package pl.mkarwowski.javaee.project.domain;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.mkarwowski.javaee.project.domain.footballerEnums.BetterFoot;
import pl.mkarwowski.javaee.project.domain.footballerEnums.Role;

import java.util.List;

@Entity
public class Footballer {
    @NotNull
    @Size(min=1, max=32, message = "First name must contain 1-32 letters")
    @Pattern(regexp="[a-zA-Z]*+", message = "First name must contain only letters")
    private String firstName;

    @NotNull
    @Size(min=1, max=45, message = "Last name must contain 1-45 letters")
    @Pattern(regexp="[a-zA-Z]*+", message = "Last name must contain only letters")
    private String lastName;

    @Min(value = 1600, message = "Year of birth must be at least 1600")
    @Max(value = 2022, message = "Year of birth must be less than 2022")
    private int yob;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private Role role;
    @NotNull
    private BetterFoot betterFoot;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Contract contract;


    @ManyToOne
    private Sponsor sponsor;


    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Sponsor getSponsor() {
        return sponsor;
    }

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    public Footballer(String firstName, String lastName, int yob, Role role, BetterFoot betterFoot, Contract contract, Sponsor sponsor) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yob = yob;
        this.role = role;
        this.betterFoot = betterFoot;
        this.contract = contract;
        this.sponsor = sponsor;
    }

    public long getId() {
        return id;
    }

    public Footballer(String firstName, String lastName, int yob, long id, Role role, BetterFoot betterFoot) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yob = yob;
        this.id = id;
        this.role = role;
        this.betterFoot = betterFoot;
    }

    public Footballer(String firstName, String lastName, int yob, Role role, BetterFoot betterFoot) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.yob = yob;
        this.role = role;
        this.betterFoot = betterFoot;
    }

    public Footballer() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYob() {
        return yob;
    }

    public void setYob(int yob) {
        this.yob = yob;
    }



    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public BetterFoot getBetterFoot() {
        return betterFoot;
    }

    public void setBetterFoot(BetterFoot betterFoot) {
        this.betterFoot = betterFoot;
    }

    @Override
    public String toString() {
        return "Footballer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", yob=" + yob +
                ", role=" + role +
                ", betterFoot=" + betterFoot +
                '}';
    }
}