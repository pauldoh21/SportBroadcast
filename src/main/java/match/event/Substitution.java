package match.event;

import player.Player;
import team.Team;

public class Substitution extends Event {
    private Player playerOut;
    private Player playerIn;

    public Substitution(Team team, int timeSeconds, Player playerOut, Player playerIn) {
        super(team, timeSeconds);
        this.playerOut = playerOut;
        this.playerIn = playerIn;
    }

    public Player getPlayerOut() {
        return playerOut;
    }

    public Player getPlayerIn() {
        return playerIn;
    }
    
}
