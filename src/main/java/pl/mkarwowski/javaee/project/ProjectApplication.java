package pl.mkarwowski.javaee.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import pl.mkarwowski.javaee.project.domain.Contract;
import pl.mkarwowski.javaee.project.service.FootballerService;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class ProjectApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(ProjectApplication.class, args);

	}
	@Bean
	public CommandLineRunner setUpApp(@Autowired FootballerService footballerService) {
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {//timer - co 24h sprawdza czy któryś z kontraktów nie wygasnął
				System.out.println("Checking if expiryDate of contract is greater than todays date");

				Iterable<Contract> contracts = footballerService.getAllContracts();
				for (Contract c : contracts) {
					if(c.getExpiryDate().compareTo(LocalDate.now()) < 0) {
						System.out.println("DELETING CONTRACT ID="+c.getId());
						footballerService.deleteContract(c);
					}
				}
			}
		},0, 86400000L);//24h

		return (args) -> {

			footballerService.learningDB();



		};
	}


}
