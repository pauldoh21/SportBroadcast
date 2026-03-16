package match;
import java.util.List;

import match.event.MatchEvent;
import match.timer.MatchTimer;

public abstract class Match {
    private String name;
    private String date;
    private int currentTime;
    private int halfDuration;
    private int numberOfHalves;
    private List<MatchEvent> events;
    protected MatchTimer timer;

    public Match(String name, String date) {
        this.name = name;
        this.date = date;
        this.timer = new MatchTimer(null);
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

    public MatchTimer getTimer() {
        return timer;
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void pause() {
        timer.pause();
    }
}
