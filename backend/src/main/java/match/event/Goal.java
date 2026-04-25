package match.event;

import player.Player;
import team.Team;

public class Goal extends MatchEvent {
    private Player scorer;

    public Goal(Team team, int timeSeconds, Player scorer) {
        super(team, timeSeconds);
        this.scorer = scorer;
    }

    public Player getScorer() {
        return scorer;
    }
    
}
