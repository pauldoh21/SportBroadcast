import java.util.List;

public class FootballTeam extends Team {
    int sideSize;
    List<Player> startingPlayers;
    List<Player> substitutes;
    Formation formation;

    FootballTeam(String name) {
        super(name);
    }

}
