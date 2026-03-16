package gui;

import gui.Layout.GroupFX;
import gui.Layout.PanelNodeFX;
import javafx.collections.FXCollections;
import match.FootballMatch;
import team.FootballTeam;

public class FootballMatchControllerFX extends PanelNodeFX {
    protected FootballMatch match;
    protected FootballTeam team1;
    protected FootballTeam team2;
    protected FormationEditorActionableFX editor1;
    protected FormationEditorActionableFX editor2;
    protected GroupFX editorGroup;
    
    public FootballMatchControllerFX(FootballTeam team1, FootballTeam team2) {
        super();
        this.match = new FootballMatch("FootballMatch", "27/06/26", team1, team2);
        this.team1 = team1;
        this.team2 = team2;
        initialiseEditors();
        setContent();
    }

    protected void initialiseEditors() {
        editor1 = new FormationEditorActionableFX(team1, match);
        editor2 = new FormationEditorActionableFX(team2, match);
        PanelNodeFX filler = new PanelNodeFX();
        filler.setBordered(false);
        editorGroup = new GroupFX(FXCollections.observableArrayList(
            editor1,
            filler,
            editor2
        ), new double[]{
            0.48,
            0.04,
            0.48
        }, javafx.geometry.Orientation.HORIZONTAL);
    }

    protected void setContent() {
        this.setContentNode(editorGroup);
    }

    public FootballMatch getMatch() {
        return match;
    }

}
