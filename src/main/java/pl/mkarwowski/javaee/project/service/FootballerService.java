package pl.mkarwowski.javaee.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.mkarwowski.javaee.project.domain.Contract;
import pl.mkarwowski.javaee.project.domain.Footballer;
import pl.mkarwowski.javaee.project.domain.Sponsor;
import pl.mkarwowski.javaee.project.domain.Team;
import pl.mkarwowski.javaee.project.domain.footballerEnums.BetterFoot;
import pl.mkarwowski.javaee.project.domain.footballerEnums.Role;
import pl.mkarwowski.javaee.project.domain.models.FootballerContractTeamSponsor;
import pl.mkarwowski.javaee.project.repository.ContractRepository;
import pl.mkarwowski.javaee.project.repository.FootballerRepository;
import pl.mkarwowski.javaee.project.repository.SponsorRepository;
import pl.mkarwowski.javaee.project.repository.TeamRepository;
import pl.mkarwowski.javaee.project.domain.models.TeamWithoutContracts;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FootballerService {

    private final FootballerRepository footballerRepository;
    private final TeamRepository teamRepository;
    private final ContractRepository contractRepository;
    private final SponsorRepository sponsorRepository;

    @Autowired
    public FootballerService(FootballerRepository footballerRepository, TeamRepository teamRepository,
                             ContractRepository contractRepository, SponsorRepository sponsorRepository) {
        this.footballerRepository = footballerRepository;
        this.teamRepository = teamRepository;
        this.contractRepository = contractRepository;
        this.sponsorRepository = sponsorRepository;
    }

    public Footballer addFootballer(Footballer footballer) {
        return footballerRepository.save(footballer);
    }

    public Iterable<Footballer> allFootballers() {
        return footballerRepository.findAll();
    }

    public boolean checkIfFootballerExists(long id) {
        if  (footballerRepository.existsById(id)) return true;
        else return false;
    }


    public boolean deleteFootballer(long id) {

        if (checkIfFootballerExists(id)) {

            footballerRepository.deleteById(id);

            return true;
        }
        else  {

            return false;
        }
    }

    public boolean deleteTeam(long id) {

        if (ifTeamExists(id)) {
            Team team = teamRepository.findById(id).get();
            int size = team.getContracts().size();
            for (int i = 0; i < size; i++) {
                team.getContracts().get(0).getFootballer().setContract(null);
                footballerRepository.save(team.getContracts().get(0).getFootballer());

                System.out.println("###############################################################");
            }
            teamRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Team updateTeam(long id, Team team) {
        Team teamToUpdate = teamRepository.findById(id).get();
        teamToUpdate.setName(team.getName());
        teamToUpdate.setYof(team.getYof());

        return teamRepository.save(teamToUpdate);
    }
    public Footballer getFootballer(long id) {
        Optional<Footballer> footballerOpt = footballerRepository.findById(id);
        if (footballerOpt.isPresent())
            return footballerOpt.get();
        return null;
    }

    public Footballer updateFootballer(long id, FootballerContractTeamSponsor fct) {
        Footballer footballerToUpdate;
        if (fct.getChosenTeamID() == 0) {//opuszczanie drużyny
            footballerToUpdate = new Footballer(fct.getFootballer().getFirstName(), fct.getFootballer().getLastName(),
                    fct.getFootballer().getYob(), id, fct.getFootballer().getRole(), fct.getFootballer().getBetterFoot());
            footballerToUpdate.setContract(null);
            if (fct.getChosenSponsorID() != 0)
                footballerToUpdate.setSponsor(sponsorRepository.findById(fct.getChosenSponsorID()).get());
            return addFootballer(footballerToUpdate);
        }
        else {

            if (fct.getChosenTeamID() == fct.getContract().getTeam().getId()) {//zostawanie w drużynie/zmiana expiryDate w kontrakcie
                footballerToUpdate = new Footballer(fct.getFootballer().getFirstName(), fct.getFootballer().getLastName(),
                        fct.getFootballer().getYob(), id, fct.getFootballer().getRole(), fct.getFootballer().getBetterFoot());
                fct.getContract().setFootballer(footballerToUpdate);
                footballerToUpdate.setContract(fct.getContract());
            } else {//zmiana drużyny
                if (fct.getContract().getId()==0) {//nie miał wcześniej drużyny
                    footballerToUpdate = new Footballer(fct.getFootballer().getFirstName(), fct.getFootballer().getLastName(),
                            fct.getFootballer().getYob(), id, fct.getFootballer().getRole(), fct.getFootballer().getBetterFoot());
                    fct.getContract().setFootballer(footballerToUpdate);
                    fct.getContract().setTeam(teamRepository.findById(fct.getChosenTeamID()).get());

                    footballerToUpdate.setContract(fct.getContract());
                    contractRepository.save(fct.getContract());
                }
                else {//miał drużynę
                    footballerToUpdate = new Footballer(fct.getFootballer().getFirstName(), fct.getFootballer().getLastName(),
                            fct.getFootballer().getYob(), id, fct.getFootballer().getRole(), fct.getFootballer().getBetterFoot());
                    fct.getContract().setFootballer(footballerToUpdate);
                    fct.getContract().setTeam(teamRepository.findById(fct.getChosenTeamID()).get());
                    footballerToUpdate.setContract(fct.getContract());
                    contractRepository.save(fct.getContract());
                }
            }
        }
        if (fct.getChosenSponsorID() != 0)
            footballerToUpdate.setSponsor(sponsorRepository.findById(fct.getChosenSponsorID()).get());
        return addFootballer(footballerToUpdate);
    }


    public Footballer updateFootballerAndContract(long id, FootballerContractTeamSponsor fct) {
        Footballer footballer = updateFootballer(id,fct);
        if (footballer.getContract()!=null) footballer.getContract().setExpiryDate(fct.getContract().getExpiryDate());
        return footballer;
    }

    public Iterable<Footballer> getAllFootballers() {
        return footballerRepository.findAll();
    }

    public Iterable<Footballer> getAllFootballersWithContract() {
        List<Footballer> foundFootballers = new ArrayList<>();
        Iterable<Footballer> footballers = footballerRepository.findAll();

        for(Footballer footballer : footballers) {
            if (footballer.getContract() != null) {
                foundFootballers.add(footballer);
            }
        }
        return foundFootballers;
    }

    public Iterable<Footballer> getAllFootballersWithoutContract() {
        List<Footballer> foundFootballers = new ArrayList<>();
        Iterable<Footballer> footballers = footballerRepository.findAll();

        for(Footballer footballer : footballers) {
            if (footballer.getContract() == null) {
                foundFootballers.add(footballer);
            }
        }
        return foundFootballers;
    }


    public boolean ifTeamExists(long id) {
        Optional<Team> team = teamRepository.findById(id);
        if (team.isPresent()) {
            return true;
        }
        return false;
    }

    public Iterable<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public List<TeamWithoutContracts> getAllTeamsWithoutContracts() {
        List<TeamWithoutContracts> teamsWithoutContracts = new ArrayList<>();
        Iterable<Team> teams = teamRepository.findAll();

        for (Team t : teams) {
            teamsWithoutContracts.add(new TeamWithoutContracts(t));
        }

        return teamsWithoutContracts;
    }


    public void printAllFootballers() {
        Iterable<Footballer> footballers = footballerRepository.findAll();
        for (Footballer f : footballers) {
            System.out.println(f.getLastName());
        }
    }

    public List<Footballer> getFootballersFromTeam(long id) {
        List<Footballer> footballers = new ArrayList<>();
        Iterable<Contract> contracts = contractRepository.findAll();
        for (Contract c : contracts) {
            if (c.getTeam().getId() == id) {
                footballers.add(c.getFootballer());
            }
        }
        return footballers;
    }

    public Iterable<Footballer> getFootballersWithLastName(String lastName) {
        return footballerRepository.findByLastName(lastName);
    }

    public Iterable<Footballer> getFootballersOrderedByYob() {
        return footballerRepository.getFootballersOrderedByYob();
    }

    public Team getTeam(long id) {
        Optional<Team> teamOpt = teamRepository.findById(id);
        if(teamOpt.isPresent()) {
            return teamOpt.get();
        }
        return null;
    }

    public Iterable<Sponsor> getAllSponsors() {
        return sponsorRepository.findAll();
    }

    public Sponsor addSponsor(Sponsor sponsor) {
        return sponsorRepository.save(sponsor);
    }

    public Sponsor getSponsor(Long id) {
        Optional<Sponsor> sponsorOpt = sponsorRepository.findById(id);
        if (sponsorOpt.isPresent())
            return sponsorOpt.get();
        return null;
    }

    public boolean deleteSponsor(long id) {
        if(sponsorExists(id)) {
            Sponsor sponsor = sponsorRepository.findById(id).get();
            List<Footballer> footballers = footballerRepository.findBySponsor(sponsor);
            for (Footballer f : footballers) {
                f.setSponsor(null);
            }
            footballerRepository.saveAll(footballers);
            sponsorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean sponsorExists(long id) {
        if(sponsorRepository.findById(id).isPresent()) {
            return true;
        }
        return false;
    }

    public Sponsor updateSponsor(Long id, Sponsor sponsor) {
        Sponsor sponsorToUpdate = sponsorRepository.findById(id).get();
        sponsorToUpdate.setName(sponsor.getName());
        return sponsorRepository.save(sponsorToUpdate);
    }

    public Iterable<Contract> getAllContracts() {
        return contractRepository.findAll();
    }

    public Contract getContract(long id) {
        return contractRepository.findById(id).get();
    }

    public boolean deleteContract(Contract c) {
        teamRepository.findById(c.getTeam().getId()).get().removeContract(c);
        teamRepository.save(c.getTeam());

        contractRepository.delete(c);

        return true;
    }


    public Contract addContract(Contract contract) {
        return contractRepository.save(contract);
    }


    public void learningDB() {

        List<Footballer> footballers = new ArrayList<>();
        List<Footballer> footballers1 = new ArrayList<>();
        Footballer footballer1 = new Footballer("Mariusz", "Karwowski", 1998, Role.MIDFIELDER, BetterFoot.RIGHT);
        Footballer footballer2 = new Footballer("Dariusz", "Karwowski", 1991, Role.ATTACKER, BetterFoot.RIGHT);
        Footballer footballer3 = new Footballer("Bartlomiej", "Dzieniszewski", 1995, Role.GOALKEEPER, BetterFoot.LEFT);
        Footballer footballer4 = new Footballer("Daniel", "Kowalski", 1990, Role.MIDFIELDER, BetterFoot.RIGHT);
        Footballer footballer5 = new Footballer("John", "Smith", 1996, Role.DEFENDER, BetterFoot.RIGHT);
        Footballer footballer6 = new Footballer("Nils", "Petersen", 1995, Role.GOALKEEPER, BetterFoot.LEFT);
        Footballer footballer7 = new Footballer("Tomasz", "Karwowski", 1998, Role.MIDFIELDER, BetterFoot.RIGHT);
        Footballer footballer8 = new Footballer("Vince", "Dabour", 1991, Role.ATTACKER, BetterFoot.RIGHT);
        Footballer footballer9 = new Footballer("Constantin", "Dybala", 1995, Role.DEFENDER, BetterFoot.LEFT);
        footballers.add(footballer1);
        footballers.add(footballer2);
        footballers.add(footballer3);
        footballers.add(footballer4);
        footballers.add(footballer5);
        footballers.add(footballer6);
        footballers.add(footballer7);
        footballers.add(footballer8);
        footballers.add(footballer9);
        footballers1.add(footballer1);


        List<Team> teams = new ArrayList<>();
        Team team1 = new Team("Eintracht Frankfurt", 1999);
        Team team2 = new Team("SGE", 1810);
        Team team3 = new Team("FC Gortatowo", 2001);
        teams.add(team1);
        teams.add(team2);
        teams.add(team3);

        List<Contract> contracts1 = new ArrayList<>();
        List<Contract> contracts2 = new ArrayList<>();
        Contract contract1 = new Contract(java.time.LocalDate.now(), footballer1, team1);
        Contract contract2 = new Contract(java.time.LocalDate.now(), footballer2, team1);
        Contract contract3 = new Contract(java.time.LocalDate.now(), footballer3, team1);
        Contract contract4 = new Contract(java.time.LocalDate.now(), footballer4, team2);
        Contract contract5 = new Contract(java.time.LocalDate.now(), footballer5, team2);
        Contract contract6 = new Contract(java.time.LocalDate.now(), footballer6, team1);
        Contract contract7 = new Contract(java.time.LocalDate.now(), footballer8, team1);
        contracts1.add(contract1);
        contracts1.add(contract2);
        contracts1.add(contract3);
        contracts2.add(contract4);
        contracts2.add(contract5);
        contracts1.add(contract6);
        contracts1.add(contract7);

        List<Sponsor> sponsors = new ArrayList<>();
        Sponsor sponsor1 = new Sponsor("Nike");
        Sponsor sponsor2 = new Sponsor("Adidas");
        Sponsor sponsor3 = new Sponsor("Umbro");
        Sponsor sponsor4 = new Sponsor("Puma");
        Sponsor sponsor5 = new Sponsor("Lotto");
        sponsors.add(sponsor1);
        sponsors.add(sponsor2);
        sponsors.add(sponsor3);
        sponsors.add(sponsor4);
        sponsors.add(sponsor5);

        sponsorRepository.saveAll(sponsors);

        team1.setContracts(contracts1);
        team2.setContracts(contracts2);
        teamRepository.saveAll(teams);

        contract1.setFootballer(footballer1);
        contract2.setFootballer(footballer2);
        contract3.setFootballer(footballer3);
        contract4.setFootballer(footballer4);
        contract5.setFootballer(footballer5);
        contract6.setFootballer(footballer6);
        contract7.setFootballer(footballer8);
        contractRepository.saveAll(contracts1);
        contractRepository.saveAll(contracts2);

        footballer1.setContract(contract1);
        footballer2.setContract(contract2);
        footballer3.setContract(contract3);
        footballer4.setContract(contract4);
        footballer5.setContract(contract5);
        footballer6.setContract(contract6);
        footballer8.setContract(contract7);

        footballer1.setSponsor(sponsor1);
        footballer2.setSponsor(sponsor1);
        footballer3.setSponsor(sponsor3);
        footballer4.setSponsor(sponsor4);
        footballer6.setSponsor(sponsor3);
        footballer9.setSponsor(sponsor3);
        footballerRepository.saveAll(footballers);
        teamRepository.saveAll(teams);
    }
}
