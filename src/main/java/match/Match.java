package match;
import java.util.List;

import match.event.MatchEvent;

public abstract class Match {
    private String name;
    private String date;
    private int currentTime;
    private int halfDuration;
    private int numberOfHalves;
    private List<MatchEvent> events;

    public Match(String name, String date) {
        this.name = name;
        this.date = date;
        this.events = new java.util.ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public int getHalfDuration() {
        return halfDuration;
    }

    public int getNumberOfHalves() {
        return numberOfHalves;
    }

    public List<MatchEvent> getEvents() {
        return events;
    }

    public void addEvent(MatchEvent event) {
        events.add(event);
    }
}
