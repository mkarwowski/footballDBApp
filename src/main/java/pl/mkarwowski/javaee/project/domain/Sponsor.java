package pl.mkarwowski.javaee.project.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Sponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private String name;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Sponsor(String name) {
        this.name = name;
    }

    public Sponsor() {}

    @Override
    public String toString() {
        return "Sponsor{" +
                "name='" + name + '\'' +
                '}';
    }
}
