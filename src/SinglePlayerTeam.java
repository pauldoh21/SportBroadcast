import java.util.List;

public class SinglePlayerTeam implements Team {
    
    private String name;
    private Player player;

    @Override
    public String GetName() {
        return name;
    }

    @Override
    public List<Player> GetPlayers() {
        return List.of(player);
    }
    
}
