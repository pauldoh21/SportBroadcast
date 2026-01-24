package match.event;

import player.Player;
import team.Team;

public class RedCard extends Event {
    private Player player;

    public RedCard(Team team, int timeSeconds, Player player) {
        super(team, timeSeconds);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}