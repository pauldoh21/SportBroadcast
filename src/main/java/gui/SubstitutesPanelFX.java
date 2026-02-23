package gui;

import java.util.ArrayList;
import java.util.List;

import formation.Formation;
import formation.Peg;
import gui.Layout.PanelNodeFX;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;

public class SubstitutesPanelFX extends PanelNodeFX {
    protected Formation formation;
    protected List<PegButtonFX> subsButtons;
    protected PegButtonGroupManagerFX buttonGroupManager;

    double pegButtonScale = 0.003;

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
        hbox.setAlignment(Pos.CENTER_LEFT);

        scrollPane = new ScrollPane(hbox);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);

        scrollPane.setOnScroll(event -> {
            if(event.getDeltaX() == 0 && event.getDeltaY() != 0) {
                scrollPane.setHvalue(scrollPane.getHvalue() - event.getDeltaY()*2 / hbox.getWidth());
            }
        });

        setContentNode(scrollPane);

        initialiseButtons();
    }

    public List<PegButtonFX> getSubsButtons() {
        return subsButtons;
    }

    public void setPegButtonScale(double scale) {
        this.pegButtonScale = scale;
    }

    protected PegButtonSelectableFX initialiseButton(Peg peg) {
        return new PegButtonSelectableFX(peg);
    }

    public void initialiseButtons() {
        subsButtons.clear();
        hbox.getChildren().clear();

        if (formation == null || formation.getSubsLine() == null) return;

        for (Peg peg : formation.getSubsLine().getPegs()) {
            PegButtonSelectableFX pegButton = initialiseButton(peg);
            subsButtons.add(pegButton);
            hbox.getChildren().add(pegButton);
            pegButton.setButtonGroupManager(buttonGroupManager);
            buttonGroupManager.getButtonGroup().add(pegButton);
        }
        buttonGroupManager.addLineGroup(subsButtons);
    }

    public void layoutButtons() {
        for (PegButtonFX button : subsButtons) {
            button.setMinSize(30.0 * pegButtonScale, 30.0 * pegButtonScale);
            button.setPrefSize(30.0 * pegButtonScale, 30.0 * pegButtonScale);
            button.setMaxSize(30.0 * pegButtonScale, 30.0 * pegButtonScale);
        }
    }

    public void updateButtons() {
        
    }
    
}
