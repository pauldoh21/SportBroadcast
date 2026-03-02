package match.event;

import player.Player;
import team.Team;

public class YellowCard extends MatchEvent {
    private Player player;

    public YellowCard(Team team, int timeSeconds, Player player) {
        super(team, timeSeconds);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}