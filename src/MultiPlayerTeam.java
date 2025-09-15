import java.util.List;

public class MultiPlayerTeam implements Team {
    
    private String name;
    private List<Player> players;

    @Override
    public String GetName() {
        return name;
    }

    @Override
    public List<Player> GetPlayers() {
        return players;
    }
    
}
