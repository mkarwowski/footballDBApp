package pl.mkarwowski.javaee.project.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.mkarwowski.javaee.project.domain.Contract;

import pl.mkarwowski.javaee.project.domain.Sponsor;
import pl.mkarwowski.javaee.project.service.FootballerService;

@RestController
public class ContractController {
    @Autowired
    final FootballerService footballerService;

    public ContractController(FootballerService footballerService) {
        this.footballerService = footballerService;
    }

    @GetMapping("/api/contract")
    public Iterable<Contract> allSponsors() {
        return footballerService.getAllContracts();
    }

    @GetMapping("/api/contract/{id}")
    public Contract getFootballer(@PathVariable("id") String id) {
        return footballerService.getContract(Long.parseLong(id));
    }
}
