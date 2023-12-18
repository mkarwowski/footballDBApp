package pl.mkarwowski.javaee.project.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mkarwowski.javaee.project.domain.Footballer;
import pl.mkarwowski.javaee.project.domain.Sponsor;
import pl.mkarwowski.javaee.project.domain.models.FootballerContractTeamSponsor;
import pl.mkarwowski.javaee.project.service.FootballerService;

@RestController
public class SponsorController {
    @Autowired
    final FootballerService footballerService;

    public SponsorController(FootballerService footballerService) {
        this.footballerService = footballerService;
    }

    @PostMapping("/api/sponsor")
    public Sponsor addSponsor(@RequestBody Sponsor sponsor) {
        return footballerService.addSponsor(sponsor);
    }

    @GetMapping("/api/sponsor")
    public Iterable<Sponsor> allSponsors() {
        return footballerService.getAllSponsors();
    }

    @DeleteMapping("/api/sponsor/{id}")
    public boolean deleteSponsor(@PathVariable String id) {
        return footballerService.deleteSponsor(Long.parseLong(id));
    }

    @GetMapping("/api/sponsor/{id}")
    public Sponsor getFootballer(@PathVariable("id") String id) {
        return footballerService.getSponsor(Long.parseLong(id));
    }

    @PutMapping("/api/sponsor/{id}")
    public Sponsor updateSponsor(@PathVariable String id, @RequestBody Sponsor sponsor) {
        return footballerService.updateSponsor(Long.parseLong(id), sponsor);
    }
}
