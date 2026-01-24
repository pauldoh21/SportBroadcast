package match;

import match.event.Goal;
import player.Player;
import team.Team;

public class FootballMatch extends Match {
    private Team team1;
    private Team team2;
    private int[] score = new int[2];

    public FootballMatch(String name, String date, Team team1, Team team2) {
        super(name, date);
        this.team1 = team1;
        this.team2 = team2;
    }

    public void addGoal(Team team, Player scorer) {
        addEvent(new Goal(team, getCurrentTime(), scorer));
        if (team.equals(team1)) {
            score[0]++;
        } else if (team.equals(team2)) {
            score[1]++;
        }
    }

    private void updateScore() {
        score[0] = 0;
        score[1] = 0;
        for (var event : getEvents()) {
            if (event instanceof Goal goalEvent) {
                if (goalEvent.getTeam().equals(team1)) {
                    score[0]++;
                } else if (goalEvent.getTeam().equals(team2)) {
                    score[1]++;
                }
            }
        }
    }

    public int[] getScore() {
        return score;
    }

    public String getScoreString() {
        return score[0] + " - " + score[1];
    }

}
