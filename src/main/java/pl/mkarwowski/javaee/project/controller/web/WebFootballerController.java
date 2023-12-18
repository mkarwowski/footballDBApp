package pl.mkarwowski.javaee.project.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.mkarwowski.javaee.project.domain.Contract;
import pl.mkarwowski.javaee.project.domain.Footballer;
import pl.mkarwowski.javaee.project.domain.footballerEnums.BetterFoot;
import pl.mkarwowski.javaee.project.domain.footballerEnums.Role;
import pl.mkarwowski.javaee.project.service.FootballerService;
import pl.mkarwowski.javaee.project.domain.models.FootballerContractTeamSponsor;

import javax.validation.Valid;

@Controller
public class WebFootballerController {
    private final FootballerService footballerService;

    public WebFootballerController(@Autowired FootballerService footballerService) {
        this.footballerService = footballerService;
    }

    @GetMapping(path = "/")
    public String index(Model model) {
        return "index";
    }

    @GetMapping("/footballer")
    public String footballers(Model model) {
        model.addAttribute("footballers", footballerService.getAllFootballers());

        return "footballer-all";
    }

    @GetMapping("/footballer/{lastName}")
    public String footballersWithSameLastName(@PathVariable("lastName") String lastName, Model model) {
        model.addAttribute("footballers", footballerService.getFootballersWithLastName(lastName));

        return "footballer-all";
    }

    @GetMapping("/footballer/orderByYob")
    public String footballersByYob(Model model) {
        model.addAttribute("footballers", footballerService.getFootballersOrderedByYob());

        return "footballer-all";
    }

    @GetMapping(path = "/footballer/add")
    public String addNewFootballer(Model model) {
        model.addAttribute("fct", new FootballerContractTeamSponsor(new Footballer("unknown", "unknown", 2000, Role.GOALKEEPER, BetterFoot.RIGHT),
                new Contract(java.time.LocalDate.now()),footballerService.getAllTeamsWithoutContracts(), footballerService.getAllSponsors()));

        return "footballer-add";
    }



    @GetMapping("/footballer/delete/{id}")
    public String deleteFootballer(@PathVariable("id") String id, Model model) {
        footballerService.deleteFootballer(Long.parseLong(id));
        model.addAttribute("footballers", footballerService.getAllFootballers());
        return "footballer-all";
    }

    @GetMapping("/footballer/edit/{id}")
    public String editFootballer(@PathVariable("id") String id, Model model) {
        model.addAttribute("fct", new FootballerContractTeamSponsor(footballerService.getFootballer(Long.parseLong(id)),
                footballerService.getFootballer(Long.parseLong(id)).getContract(), footballerService.getAllTeamsWithoutContracts(), footballerService.getAllSponsors()));

        return "footballer-edit";
    }

    @PostMapping("/footballer/add")
    public String addNewFootballer(@ModelAttribute("fct") @Valid FootballerContractTeamSponsor fct, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation error!" + bindingResult.toString());
            model.addAttribute("fct", new FootballerContractTeamSponsor(new Footballer("unknown", "unknown", 2000, Role.GOALKEEPER, BetterFoot.RIGHT),
                    new Contract(java.time.LocalDate.now()),footballerService.getAllTeamsWithoutContracts(), footballerService.getAllSponsors()));
            return "footballer-add";
        }
        if (fct.getChosenTeamID()!=0) {
            fct.setForeignFields(footballerService.getTeam(fct.getChosenTeamID()));

            Footballer f = footballerService.addFootballer(fct.getFootballer());
            footballerService.addContract(fct.getContract());
            footballerService.updateFootballer(f.getId(), fct);
        }
        else {
            footballerService.addFootballer(fct.getFootballer());
        }
        model.addAttribute("footballers", footballerService.getAllFootballers());
        return "footballer-all";
    }

    @PostMapping("/footballer/edit/{id}")
    public String editFootballer(@ModelAttribute("fct") @Valid FootballerContractTeamSponsor fct, BindingResult bindingResult, @PathVariable("id") String id, Model model) {
        long idL = Long.parseLong(id);
        if (bindingResult.hasErrors()) {
            System.out.println("Validation error!");
            model.addAttribute("fct", new FootballerContractTeamSponsor(footballerService.getFootballer(idL),
                    footballerService.getFootballer(idL).getContract(), footballerService.getAllTeamsWithoutContracts(), footballerService.getAllSponsors()));
            return "footballer-edit";
        }
        if (footballerService.checkIfFootballerExists(idL)) {
            footballerService.updateFootballerAndContract(idL, fct);
        }
        model.addAttribute("footballers", footballerService.getAllFootballers());
        return "footballer-all";
    }
}
