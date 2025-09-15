import java.util.List;

public abstract class Team {
    String name;
    List<Player> players;
    static int maxNumberOfPlayers;

    String getName() {
        return name;
    }

    List<Player> getPlayers() {
        return players;
    }

    void addPlayer(Player player) {
        if ((players.size() < maxNumberOfPlayers) || (maxNumberOfPlayers == 0)) {
            players.add(player);
        } else {
            throw new IllegalStateException("Team is already full");
        }
    }

}