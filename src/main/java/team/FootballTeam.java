package team;

import formation.Formation;
import formation.Line;
import formation.Peg;
import player.FootballPlayer;
import player.Player;
import player.PreferredName;

public class FootballTeam extends MultiPlayerTeam {
    Formation formation;

    public FootballTeam(String name) {
        super(name);
        // TODO: make 5s, 7s and 11s classes
        sideSize = 11;
        formation = new Formation("4-4-2", true);
        generatePlayers();
        formation.setTeam(this);
        //formation.generateFormationWithTeam();
        formation.printFormation();
    }

    public void generatePlayers() {
        for (int i = 0; i < sideSize + 5; i++) {
            Player player = new FootballPlayer("First" + (i + 1), java.util.Collections.emptyList(), "Last" + (i + 1), PreferredName.FIRST_LAST);
            player.setTeam(this);
            addPlayer(player);
        }
    }

    public void addPlayer(FootballPlayer player) {
        super.addPlayer(player);
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation newFormation) {
        int count = 0;
        if (newFormation.getTeam() != this) {
            for (Line line : newFormation.getLines()) {
                for (Peg peg : line.getPegs()) {
                    if (count < getPlayers().size()) {
                        peg.setPlayer(getPlayers().get(count));
                        count++;
                    }
                }
            }
        }
        this.formation = newFormation;
    }

}
