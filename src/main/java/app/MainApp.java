package app;

import formation.Formation;
import gui.BaseWindowFX;
import gui.FormationDisplayBackgroundFX;
import gui.FormationDisplayFX;
import gui.FormationDisplayFXDeprecated;
import gui.FormationEditorActionableFX;
import gui.FormationEditorFX;
import gui.FormationEditorFXDeprecated;
import gui.FormationEditorTeamFX;
import gui.FootballMatchControllerFX;
import gui.Layout.GroupFX;
import gui.Layout.LayoutNodeFX;
import gui.Layout.SplitNodeFX;
import gui.Layout.PanelNodeFX;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import match.FootballMatch;
import obs.OBSController;
import team.FootballTeam;
import team.Team;
import teamsheet.TeamsheetPDF;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        BaseWindowFX window = new BaseWindowFX("SportBroadcast");
        /* FootballTeam team1 = new FootballTeam("Team A");
        FootballTeam team2 = new FootballTeam("Team B");
        team2.getFormation().setFormation("4-3-2-1");
        team2.getFormation().generateFormationWithTeam();

        FormationEditorFXDeprecated formationDisplay = new FormationEditorFXDeprecated(team1.getFormation());
        FormationEditorFXDeprecated formationDisplay2 = new FormationEditorFXDeprecated(team2.getFormation());
        formationDisplay.setBordered(false);
        formationDisplay2.setBordered(false);

        StackPane placeholder = new StackPane(new Label("JavaFX UI placeholder"));

        GroupFX editors = new GroupFX(
                javafx.collections.FXCollections.observableArrayList(
                        formationDisplay,
                        new PanelNodeFX(),
                        formationDisplay2
                ),
                new double[]{0.45, 0.1, 0.45},
                javafx.geometry.Orientation.HORIZONTAL
        );

        SplitNodeFX mainContent = new SplitNodeFX(
                javafx.collections.FXCollections.observableArrayList(
                        new PanelNodeFX(),
                        editors,
                        new PanelNodeFX()
                ),
                false,
                javafx.collections.FXCollections.observableArrayList(0.2, 0.8),
                false);

        //SplitNodeFX.setResizableWithParent(mainContent.getItems().get(0), true);

        SplitNodeFX rootLayout = new SplitNodeFX(javafx.collections.FXCollections.observableArrayList(new PanelNodeFX(), mainContent,
                new PanelNodeFX()
        ), true, javafx.collections.FXCollections.observableArrayList(0.1, 0.9), true
        );

        //OBSController obsController = new OBSController("localhost", 4455, "");
        //obsController.setSourceText("Team1_Score", "2");

        FootballMatch match = new FootballMatch("Match 1", "2024-06-01", team1, team2);
        match.addGoal(team1, team1.getPlayers().get(0));
        System.out.println(match.getScoreString()); */

        //window.setContentNode(rootLayout);
        /* Formation tempFormation = new Formation("4-4-2");
        tempFormation.generateFormation();
        FormationEditorFX formationDisplayFX = new FormationEditorFX(tempFormation); */
        
        FootballTeam team1 = new FootballTeam("Team A");
        FootballTeam team2 = new FootballTeam("Team B");
        //FootballMatch match = new FootballMatch("Match 1", "2026-06-26", team1, team2);
        //FormationEditorActionableFX formationDisplayFX = new FormationEditorActionableFX(team1, match);
        FootballMatchControllerFX matchController = new FootballMatchControllerFX(team1, team2);
        matchController.getMatch().initialiseBroadcast("localhost", 4455, "");
        matchController.getMatch().start();

        TeamsheetPDF teamsheetPDF = new TeamsheetPDF(team1);
        teamsheetPDF.generate();

        window.setContentNode(matchController);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
