package pl.mkarwowski.javaee.project.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mkarwowski.javaee.project.domain.Sponsor;
import pl.mkarwowski.javaee.project.domain.Team;
import pl.mkarwowski.javaee.project.service.FootballerService;

@RestController
public class TeamController {
    @Autowired
    final FootballerService footballerService;

    public TeamController(FootballerService footballerService) {
        this.footballerService = footballerService;
    }

    @PostMapping("/api/team")
    public Team addTeam(@RequestBody Team team) {
        return footballerService.addTeam(team);
    }

    @GetMapping("/api/team")
    public Iterable<Team> allTeam() {
        return footballerService.getAllTeams();
    }

    @DeleteMapping("/api/team/{id}")
    public boolean deleteTeam(@PathVariable String id) {
        return footballerService.deleteTeam(Long.parseLong(id));
    }

    @GetMapping("/api/team/{id}")
    public Team getTeam(@PathVariable("id") String id) {
        return footballerService.getTeam(Long.parseLong(id));
    }

    @PutMapping("/api/team/{id}")
    public Team updateSponsor(@PathVariable String id, @RequestBody Team Team) {
        return footballerService.updateTeam(Long.parseLong(id), Team);
    }
}
