package gui;

import java.util.List;

import formation.Formation;
import formation.Peg;
import gui.Dragging.PegLineSwapEvent;
import gui.Dragging.PegSwapEvent;
import gui.Layout.GroupFX;
import gui.Layout.PanelNodeFX;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;

public class FormationEditorFX extends FormationDisplayFX {
    protected MovementArrows movementArrows;
    protected GroupFX group;
    protected PanelNodeFX stackPanel;
    protected FormationTemplateRibbonFX formationTemplateRibbon;

    public FormationEditorFX(formation.Formation formation) {
        super(formation);
        setButtonGroupManagers();
        buttonGroupManager.setFormationDisplay(this);
        this.addEventHandler(PegSwapEvent.PEG_SWAP, e -> {
            this.positionChanged = true;
            this.showFormation();
        });
        this.addEventHandler(PegSelectedEvent.PEG_SELECTED, e -> {
            pegSelectedEventHandler(e);
        });
        // TODO: How to handle adding new line
        this.addEventHandler(PegLineSwapEvent.PEG_LINE_SWAP, e -> {
            lineSwapEventHandler(e);
        });
        this.addEventHandler(FormationSwapEvent.FORMATION_SWAP, e -> {
            formationSwapEventHandler(e);
        });
        movementArrows.hide();
    }

    @Override
    protected void initialiseButtonGroupManager() {
        buttonGroupManager = new PegButtonGroupManagerFX();
    }

    protected void pegSelectedEventHandler(PegSelectedEvent e) {
        // Only show the arrows if the peg is in the formation (not a sub)
        if (e.getSourceManager() == this.buttonGroupManager) {
            if (formation.getLines().stream().anyMatch(line -> line.getPegs().contains(e.getSelectedPeg()))) {
                movementArrows.show();
            } else {
                movementArrows.hide();
            }
        }
    }

    protected void lineSwapEventHandler(PegLineSwapEvent e) {
        formation.changeLine(e.getSourceButton().getPeg(), e.getTargetLine().getLineIndex(), e.getSegmentIndex()+1);
        formationTemplateRibbon.setFormation(formation);
    }

    protected void formationSwapEventHandler(FormationSwapEvent e) {
        if (e.getSourceFormation() != this.formation) {
                return;
            }
            this.positionChanged = true;
            this.setFormation(e.getNewFormation());
    }

    @Override
    protected void initialiseStack() {
        super.initialiseStack();
        stack.setOnMouseClicked(e -> {buttonGroupManager.deselectAll(); movementArrows.hide();});
        addMovementArrows();
    }

    @Override
    protected PegButtonFX initialiseButton(Peg peg) {
        PegButtonSelectableFX pegButton = new PegButtonSelectableFX(peg);
        return pegButton;
    }

    @Override
    protected void setContent() {
        stackPanel = new PanelNodeFX();
        stackPanel.setContentNode(this.stack);

        formationTemplateRibbon = new FormationTemplateRibbonFX(formation);

        this.group = new GroupFX(javafx.collections.FXCollections.observableArrayList(formationTemplateRibbon, stackPanel), new double[]{0.1, 0.9}, javafx.geometry.Orientation.VERTICAL);
        this.setContentNode(this.group);
    }

    @Override
    protected void refreshButtons() {
        for (PegButtonFX button : buttonGroupManager.getButtonGroup()) {
            button.refresh();
        }
    }

    @Override
    public void setFormation(Formation formation) {
        super.setFormation(formation);
    }

    private void setButtonGroupManagers() {
        @SuppressWarnings("unchecked")
        List<PegButtonSelectableFX> selectableButtons = (List<PegButtonSelectableFX>) (List<?>) formationButtons;
        buttonGroupManager.setButtonGroup(selectableButtons);
        for (PegButtonFX button : formationButtons) {
            if (button instanceof PegButtonSelectableFX psb) {
                psb.setButtonGroupManager(buttonGroupManager);
            }
        }
    }
    
    private void addMovementArrows() {
        movementArrows = new MovementArrows();
        this.stack.getChildren().add(movementArrows);
        movementArrows.bindToParent(stack);
        StackPane.setAlignment(movementArrows, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(movementArrows, new Insets(10));
        movementArrows.setTranslateX(-30); // Small offset from edge
        movementArrows.setTranslateY(-30);
        setMovementArrowsFunctions();
    }

    private void setMovementArrowsFunctions() {
        int step = 1; // Define how much to move per click
        movementArrows.getUpButton().setOnAction(e -> {
            buttonGroupManager.adjustPeg(0, -step);
            this.showFormation();
        });
        movementArrows.getDownButton().setOnAction(e -> {
            buttonGroupManager.adjustPeg(0, step);
            this.showFormation();
        });
        movementArrows.getLeftButton().setOnAction(e -> {
            buttonGroupManager.adjustPeg(-step, 0);
            this.showFormation();
        });
        movementArrows.getRightButton().setOnAction(e -> {
            buttonGroupManager.adjustPeg(step, 0);
            this.showFormation();
        });
    }
    
    // You can add methods to control the movement arrows if needed
    public MovementArrows getMovementArrows() {
        return movementArrows;
    }

    public PegButtonGroupManagerFX getButtonGroupManager() {
        return buttonGroupManager;
    }

    @Override
    protected void showFormation() {
        super.showFormation();
        
        //movementArrows.setTranslateX(this.getWidth() * -0.01);
        //movementArrows.setTranslateY(this.getHeight() * -0.01);
    }
}
