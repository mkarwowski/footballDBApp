package pl.mkarwowski.javaee.project.domain.footballerEnums;

public enum Role {
    ATTACKER("Attacker"),
    MIDFIELDER("Midfielder"),
    DEFENDER("Defender"),
    GOALKEEPER("Goalkeeper");

    private final String displayValue;

    Role(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}

