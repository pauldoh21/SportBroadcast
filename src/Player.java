import java.util.List;

public abstract class Player {
    String forename;
    List<String> middleNames;
    String surname;

    String getName() {
        return forename + " " + String.join(" ", middleNames) + " " + surname;
    }
}
