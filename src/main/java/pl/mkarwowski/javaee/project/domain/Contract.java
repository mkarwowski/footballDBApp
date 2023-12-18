package pl.mkarwowski.javaee.project.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiryDate;

    @JsonIgnore
    @NotNull
    @OneToOne(mappedBy = "contract", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    private Footballer footballer;

    @JsonIgnore
    @NotNull
    @ManyToOne
    private Team team;

    public Contract(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Contract(LocalDate expiryDate, Footballer footballer, Team team) {
        this.expiryDate = expiryDate;
        this.footballer = footballer;
        this.team = team;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "expiryDate=" + expiryDate +
                ", footballer=" + footballer +
                ", team=" + team +
                '}';
    }

    public Contract() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Footballer getFootballer() {
        return footballer;
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
