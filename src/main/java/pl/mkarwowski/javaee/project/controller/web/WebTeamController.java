package pl.mkarwowski.javaee.project.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.mkarwowski.javaee.project.domain.Team;
import pl.mkarwowski.javaee.project.service.FootballerService;

import javax.validation.Valid;

@Controller
public class WebTeamController {
    private final FootballerService footballerService;

    public WebTeamController(@Autowired FootballerService footballerService) {
        this.footballerService = footballerService;
    }

    @GetMapping("/team")
    public String teams(Model model) {
        model.addAttribute("allTeamsFromDB", footballerService.getAllTeams());

        return "team-all";
    }

    @GetMapping("/team/add")
    public String addTeam(Model model) {
        model.addAttribute("team", new Team("unknown", 2022));

        return "team-add";
    }

    @PostMapping("/team/add")
    public String addNewTeam(@ModelAttribute("team") @Valid Team team, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation error!");
            return "team-add";
        }
        footballerService.addTeam(team);
        model.addAttribute("allTeamsFromDB", footballerService.getAllTeams());
        return "team-all";
    }

    @GetMapping("/team/delete/{id}")
    public String deleteTeam(@PathVariable("id") String id, Model model) {
        footballerService.deleteTeam(Long.parseLong(id));
        model.addAttribute("allTeamsFromDB", footballerService.getAllTeams());
        return "team-all";
    }

    @GetMapping("/team/edit/{id}")
    public String editTeam(@PathVariable("id") String id, Model model) {
        model.addAttribute("team", footballerService.getTeam(Long.parseLong(id)));

        return "team-edit";
    }

    @GetMapping("/team/{id}")
    public String footballersFromClub(@PathVariable("id") String id, Model model) {
        model.addAttribute("footballers", footballerService.getFootballersFromTeam(Long.parseLong(id)));

        return "footballer-all";
    }

    @PostMapping("/team/edit/{id}")
    public String editTeam(@ModelAttribute("team") @Valid Team team, BindingResult bindingResult, @PathVariable("id") String id, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println("Validation error!");
            return "team-edit";
        }
        footballerService.updateTeam(Long.parseLong(id), team);

        model.addAttribute("allTeamsFromDB", footballerService.getAllTeams());

        return "team-all";
    }


}
