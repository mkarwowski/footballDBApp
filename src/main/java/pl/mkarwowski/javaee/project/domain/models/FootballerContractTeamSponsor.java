package pl.mkarwowski.javaee.project.domain.models;

import pl.mkarwowski.javaee.project.domain.Contract;
import pl.mkarwowski.javaee.project.domain.Footballer;
import pl.mkarwowski.javaee.project.domain.Sponsor;
import pl.mkarwowski.javaee.project.domain.Team;

import javax.validation.Valid;
import java.util.List;

public class FootballerContractTeamSponsor {
    @Valid
    private Footballer footballer;
    private Contract contract;
    private List<TeamWithoutContracts> teams;
    private long chosenTeamID;
    private long chosenSponsorID;
    private Iterable<Sponsor> sponsors;


    public long getChosenSponsorID() {
        return chosenSponsorID;
    }

    public void setChosenSponsorID(long chosenSponsorID) {
        this.chosenSponsorID = chosenSponsorID;
    }

    public Iterable<Sponsor> getSponsors() {
        return sponsors;
    }

    public void setSponsors(Iterable<Sponsor> sponsors) {
        this.sponsors = sponsors;
    }

    public long getChosenTeamID() {
        return chosenTeamID;
    }

    public void setChosenTeamID(long chosenTeamID) {
        this.chosenTeamID = chosenTeamID;
    }

    public Footballer getFootballer() {
        return footballer;
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public List<TeamWithoutContracts> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamWithoutContracts> teams) {
        this.teams = teams;
    }

    public FootballerContractTeamSponsor(Footballer f, Contract c, List<TeamWithoutContracts> t, Iterable<Sponsor> s) {
        this.footballer = f;
        this.contract = c;
        this.teams = t;
        this.sponsors = s;
    }

    public void setForeignFields(Team t) {
        contract.setFootballer(footballer);
        contract.setTeam(t);

    }
}
