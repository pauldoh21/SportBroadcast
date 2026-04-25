package player;
import java.util.List;

import team.Team;

public abstract class Player {
    String forename;
    List<String> middleNames;
    String surname;
    PreferredName preferredName;
    private Team team;
    // TODO: Add profile picture for player

    Player(String forename, List<String> middleNames, String surname, PreferredName preferredName) {
        this.forename = forename;
        this.middleNames = middleNames;
        this.surname = surname;

        if (middleNames.size() == 0 && (preferredName == PreferredName.FIRST_MIDDLE_LAST || preferredName == PreferredName.F_M_LAST)) {
            this.preferredName = PreferredName.FIRST_LAST;
        } else if (preferredName == null) {
            this.preferredName = PreferredName.FIRST_LAST;
        } else {
            this.preferredName = preferredName;
        }
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getName() {
        return forename + " " + String.join(" ", middleNames) + " " + surname;
    }

    public String getPreferredName() {
        return switch (preferredName) {
            case FIRST -> forename;
            case LAST -> surname;
            case MIDDLE -> String.join(" ", middleNames);
            case FIRST_LAST -> forename + " " + surname;
            case FIRST_MIDDLE_LAST -> forename + " " + String.join(" ", middleNames) + " " + surname;
            case F_LAST -> forename.charAt(0) + ". " + surname;
            case F_M_LAST -> forename.charAt(0) + ". " + (middleNames.size() > 0 ? middleNames.get(0).charAt(0) + ". " : "") + surname;
        };
    }

    public int getShirtNumber() {
        if (team != null) {
            return team.getPlayerNumber(this);
        }
        return 0;
    }

    @Override
    public String toString() {
        return getPreferredName();
    }
}
