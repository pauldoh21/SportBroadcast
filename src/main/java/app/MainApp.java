package app;

import gui.BaseWindowFX;
import gui.FormationDisplayBackgroundFX;
import gui.FormationDisplayFX;
import gui.FormationEditorFX;
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
import team.FootballTeam;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        FootballTeam team1 = new FootballTeam("Team A");
        FootballTeam team2 = new FootballTeam("Team B");
        team2.getFormation().setFormation("4-3-2-1");
        team2.getFormation().generateFormation();

        FormationEditorFX formationDisplay = new FormationEditorFX(team1.getFormation());
        FormationEditorFX formationDisplay2 = new FormationEditorFX(team2.getFormation());
        formationDisplay.setBordered(false);
        formationDisplay2.setBordered(false);

        BaseWindowFX window = new BaseWindowFX("SportBroadcast (JavaFX)");

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

        window.setContentNode(rootLayout);
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
