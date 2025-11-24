public enum Position {
    GOALKEEPER("GK"),
    CENTRE_BACK("CB"),
    FULL_BACK("FB"),
    WING_BACK("WB"),
    CENTRAL_DEFENSIVE_MIDFIELDER("CDM"),
    CENTRAL_MIDFIELDER("CM"),
    ATTACKING_MIDFIELDER("CAM"),
    WINGER("W"),
    SECOND_STRIKER("SS"),
    STRIKER("ST");

    private final String label;
    private Position(String string) {
        this.label = string;
    }

    public String shortName() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}