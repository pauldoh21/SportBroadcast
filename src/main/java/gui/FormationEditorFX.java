package gui;

import formation.Formation;
import gui.Layout.GroupFX;
import gui.Layout.PanelNodeFX;
import gui.Layout.SplitNodeFX;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class FormationEditorFX extends PanelNodeFX {
    PegButtonGroupManagerFX buttonGroupManager;

    public FormationEditorFX(Formation formation) {
        super();

        buttonGroupManager = new PegButtonGroupManagerFX(new java.util.ArrayList<>());
        buttonGroupManager.setDragArea(this);
        buttonGroupManager.setDragStep(10.0);

        FormationDisplayFX formationDisplay = new FormationDisplayFX(formation, buttonGroupManager, true);
        SubstitutesPanelFX substitutesPanel = new SubstitutesPanelFX(formation, buttonGroupManager);

        buttonGroupManager.setFormationDisplay(formationDisplay);

        PanelNodeFX temp = new PanelNodeFX();
        temp.setMouseTransparent(true);
        temp.setOpacity(0);
        /* SplitNodeFX layout = new SplitNodeFX(
            javafx.collections.FXCollections.observableArrayList(
                new FormationDisplayFX(formation, buttonGroupManager, true),
                new SubstitutesPanelFX(formation, buttonGroupManager)
            ),
            true, 
            javafx.collections.FXCollections.observableArrayList(0.9), 
            false); */

        GroupFX layout = new GroupFX(javafx.collections.FXCollections.observableArrayList(formationDisplay, substitutesPanel), new double[]{0.9, 0.1}, javafx.geometry.Orientation.VERTICAL);

        StackPane controlPanel = new StackPane(layout, temp);
        controlPanel.setPickOnBounds(false);
        this.setContentNode(controlPanel);
    }
}
