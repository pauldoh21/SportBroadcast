
public class FootballTeam extends MultiPlayerTeam {
    Formation formation;

    FootballTeam(String name) {
        super(name);
        // TODO: make 5s, 7s and 11s classes
        sideSize = 11;
        formation = new Formation(this);
        formation.includesGoalkeeper(true);
        formation.setFormation("4-4-2");
        generatePlayers();
        formation.generateFormation();
        formation.printFormation();
    }

    public void generatePlayers() {
        for (int i = 0; i < sideSize + 5; i++) {
            addPlayer(new FootballPlayer("First" + (i + 1), java.util.Collections.emptyList(), "Last" + (i + 1), PreferredName.FIRST_LAST));
        }
    }

    public void addPlayer(FootballPlayer player) {
        super.addPlayer(player);
    }

    public Formation getFormation() {
        return formation;
    }

}
