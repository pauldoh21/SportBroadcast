package match.event;

import team.Team;

public abstract class Event {
    private Team team;
    private int timeSeconds;

    public Event(Team team, int timeSeconds) {
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
