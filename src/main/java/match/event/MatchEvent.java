package match.event;

import team.Team;

public abstract class MatchEvent {
    private Team team;
    private int timeSeconds;

    public MatchEvent(Team team, int timeSeconds) {
        this.team = team;
        this.timeSeconds = timeSeconds;
    }

    public Team getTeam() {
        return team;
    }

    public int getTimeSeconds() {
        return timeSeconds;
    }
}
