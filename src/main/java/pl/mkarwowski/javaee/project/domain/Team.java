package pl.mkarwowski.javaee.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;
    @Min(value = 1600, message = "Year of fundation must be at least 1600")
    @Max(value = 2022, message = "Year of fundation must be less than 2022")
    private int yof;

    @OneToMany(mappedBy="team", cascade= {CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval=true,fetch = FetchType.LAZY)
    private List<Contract> contracts;

    public Team() {}

    public Team(String name, int yof) {
        this.name = name;
        this.yof = yof;
    }

    public Team(String name, int yof, List<Contract> contracts) {
        this.name = name;
        this.contracts = contracts;
        this.yof = yof;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYof() {
        return yof;
    }

    public void setYof(int yof) {
        this.yof = yof;
    }

    public void removeContract(Contract c){
        contracts.remove(c);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", yof=" + yof +
                '}';
    }
}
