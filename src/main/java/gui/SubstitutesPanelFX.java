package gui;

import java.util.ArrayList;
import java.util.List;

import formation.Formation;
import formation.Peg;
import gui.Layout.PanelNodeFX;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class SubstitutesPanelFX extends PanelNodeFX {
    Formation formation;
    List<PegButtonFX> subsButtons;
    PegButtonGroupManagerFX buttonGroupManager;

    private HBox hbox;
    private ScrollPane scrollPane;

    public SubstitutesPanelFX(Formation formation, PegButtonGroupManagerFX buttonGroupManager) {
        super();
        setBordered(true);
        this.formation = formation;
        this.buttonGroupManager = buttonGroupManager;

        subsButtons = new ArrayList<>();

        hbox = new HBox(6);
        hbox.setPadding(new Insets(6));

        scrollPane = new ScrollPane(hbox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);

        setContentNode(scrollPane);

        initialiseButtons();
    }

    public void initialiseButtons() {
        subsButtons.clear();
        hbox.getChildren().clear();

        if (formation == null || formation.getSubsLine() == null) return;

        for (Peg peg : formation.getSubsLine().getPegs()) {
            PegButtonSelectableFX pegButton = new PegButtonSelectableFX(peg);
            subsButtons.add(pegButton);
            hbox.getChildren().add(pegButton);
            pegButton.setButtonGroup(buttonGroupManager);
            buttonGroupManager.getButtonGroup().add(pegButton);
        }
        buttonGroupManager.addLineGroup(subsButtons);
    }

    public void updateButtons() {
        
    }
    
}
