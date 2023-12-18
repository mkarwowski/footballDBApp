package pl.mkarwowski.javaee.project.controller.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mkarwowski.javaee.project.service.FootballerService;

@Controller
public class webContractController {
    private final FootballerService footballerService;

    public webContractController(@Autowired FootballerService footballerService) {
        this.footballerService = footballerService;
    }

    @GetMapping("/contract")
    public String contracts(Model model) {
        model.addAttribute("allContractsFromDB", footballerService.getAllContracts());

        return "contract-all";
    }
}
