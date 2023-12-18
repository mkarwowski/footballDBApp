package pl.mkarwowski.javaee.project.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.mkarwowski.javaee.project.domain.Sponsor;
import pl.mkarwowski.javaee.project.service.FootballerService;

import javax.validation.Valid;

@Controller
public class WebSponsorController {
    private final FootballerService footballerService;

    public WebSponsorController(FootballerService footballerService) {
        this.footballerService = footballerService;
    }

    @GetMapping("/sponsor")
    public String sponsors(Model model) {
        model.addAttribute("allSponsorsFromDB", footballerService.getAllSponsors());

        return "sponsor-all";
    }

    @GetMapping(path = "/sponsor/add")
    public String addSponsor(Model model) {
        model.addAttribute("sponsor", new Sponsor("unknown"));

        return "sponsor-add";
    }

    @PostMapping("/sponsor/add")
    public String addSponsor(@ModelAttribute("sponsor") @Valid Sponsor sponsor, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation error!");
            return "sponsor-add";
        }
        footballerService.addSponsor(sponsor);
        model.addAttribute("allSponsorsFromDB", footballerService.getAllSponsors());
        return "sponsor-all";
    }

    @GetMapping("/sponsor/delete/{id}")
    public String deleteSponsor(@PathVariable("id") String id, Model model) {
        footballerService.deleteSponsor(Long.parseLong(id));

        model.addAttribute("allSponsorsFromDB", footballerService.getAllSponsors());

        return "sponsor-all";
    }

    @GetMapping("/sponsor/edit/{id}")
    public String editSponsor(@PathVariable("id") String id, Model model) {
        model.addAttribute("sponsor", footballerService.getSponsor(Long.parseLong(id)));
        return "sponsor-edit";
    }

    @PostMapping("/sponsor/edit/{id}")
    public String editSponsor(@ModelAttribute("sponsor") @Valid Sponsor sponsor, BindingResult bindingResult, @PathVariable("id") String id, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation error!");
            return "sponsor-edit";
        }
        footballerService.updateSponsor(Long.parseLong(id), sponsor);
        model.addAttribute("allSponsorsFromDB", footballerService.getAllSponsors());
        return "sponsor-all";
    }

}
