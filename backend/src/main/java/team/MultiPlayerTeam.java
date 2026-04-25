package team;

//TODO: Add MultiPlayerFormationTeam
public class MultiPlayerTeam extends Team {
    int sideSize;
    
    MultiPlayerTeam(String name) {
        super(name);
    }
    
    public int getSideSize() {
        return sideSize;
    }
}
