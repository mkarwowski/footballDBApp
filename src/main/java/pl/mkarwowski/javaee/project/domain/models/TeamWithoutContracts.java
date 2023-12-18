package pl.mkarwowski.javaee.project.domain.models;

import pl.mkarwowski.javaee.project.domain.Team;

public class TeamWithoutContracts {
    private String name;
    private long id;

    public TeamWithoutContracts(Team t) {
        name = t.getName();
        id = t.getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TeamWithoutContracts() {}
}
