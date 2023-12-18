package pl.mkarwowski.javaee.project.domain.footballerEnums;

public enum BetterFoot {
    LEFT("Left"),
    RIGHT("Right"),
    TWO_FOOTED("Two-footed");

    private final String displayValue;


    BetterFoot(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}