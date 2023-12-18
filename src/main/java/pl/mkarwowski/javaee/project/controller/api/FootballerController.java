package pl.mkarwowski.javaee.project.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mkarwowski.javaee.project.domain.Footballer;
import pl.mkarwowski.javaee.project.domain.models.FootballerContractTeamSponsor;
import pl.mkarwowski.javaee.project.service.FootballerService;


@RestController
public class FootballerController {

    @Autowired
    final FootballerService footballerService;

    public FootballerController(FootballerService footballerService) {
        this.footballerService = footballerService;
    }

    @PostMapping("/api/footballer")
    public Footballer addFootballer(@RequestBody Footballer footballer) {
        return footballerService.addFootballer(footballer);
    }

    @GetMapping("/api/footballer")
    public Iterable<Footballer> allFootballers() {
        return footballerService.allFootballers();
    }

    @DeleteMapping("/api/footballer/{id}")
    public boolean deleteFootballer(@PathVariable String id) {
        return footballerService.deleteFootballer(Long.parseLong(id));
    }

    @GetMapping("/api/footballer/{id}")
    public Footballer getFootballer(@PathVariable("id") String id) {
        return footballerService.getFootballer(Long.parseLong(id));
    }

    @PutMapping("/api/footballer/{id}")
    public Footballer updateFootballer(@PathVariable String id, @RequestBody FootballerContractTeamSponsor footballer) {
        return footballerService.updateFootballerAndContract(Long.parseLong(id), footballer);
    }
}