package player;
import java.util.List;

public class FootballPlayer extends Player {
    
    public FootballPlayer(String forename, List<String> middleNames, String surname, PreferredName preferredName) {
        super(forename, middleNames, surname, PreferredName.FIRST_LAST);
    }
    
    

}
