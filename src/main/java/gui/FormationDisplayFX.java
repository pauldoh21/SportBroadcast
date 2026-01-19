package gui;

import java.util.ArrayList;
import java.util.List;

import formation.Formation;
import formation.Line;
import formation.Peg;
import gui.Layout.PanelNodeFX;
import javafx.scene.layout.StackPane;

public class FormationDisplayFX extends PanelNodeFX {
    Formation formation;
    boolean interactable;
    PegButtonGroupManagerFX buttonGroupManager;
    PanelNodeFX contentNode;
    FormationDisplayBackgroundFX background;
    private List<PegButtonFX> formationButtons;

    public FormationDisplayFX(Formation formation, PegButtonGroupManagerFX buttonGroupManager, boolean interactable) {
        super();
        this.formation = formation;
        this.interactable = interactable;
        setBordered(false);

        formationButtons = new ArrayList<>();
        this.buttonGroupManager = buttonGroupManager;
        // create the stack/content first so initialiseButtons adds into the visible contentNode
        initialiseStack();
        initialiseButtons();
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    private void initialiseStack() {
        contentNode = new PanelNodeFX();
        contentNode.setBordered(false);
        background = new FormationDisplayBackgroundFX();
        StackPane stack = new StackPane(background, contentNode);
        background.prefWidthProperty().bind(stack.widthProperty());
        background.prefHeightProperty().bind(stack.heightProperty());

        contentNode.prefWidthProperty().bind(stack.widthProperty());
        contentNode.prefHeightProperty().bind(stack.heightProperty());

        stack.prefWidthProperty().bind(this.widthProperty());
        stack.prefHeightProperty().bind(this.heightProperty());

        this.setContentNode(stack);
    }

    public void initialiseButtons() {
        formationButtons.clear();
        if (background == null) {
            initialiseStack();
        }

        // remove only peg buttons so the background/content node stays behind
        this.getChildren().removeIf(node -> node instanceof PegButtonFX);

        for (Line line : formation.getLines()) {
            for (Peg peg : line.getPegs()) {
                PegButtonFX pegButton;
                if (interactable) {
                    pegButton = new PegButtonSelectableFX(peg);
                } else {
                    pegButton = new PegButtonFX(peg);
                }
                formationButtons.add(pegButton);
                contentNode.getChildren().add(pegButton);
            }
        }
        if (interactable) {
            for (PegButtonFX button : formationButtons) {
                if (button instanceof PegButtonSelectableFX selectableButton) {
                    selectableButton.setButtonGroup(buttonGroupManager);
                    buttonGroupManager.addButton(selectableButton);
                }
            }
        }
        showFormation();
    }

    // Listener to update formation display on resize
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        showFormation();
    }

    private void drawLine(PegButtonFX peg1, PegButtonFX peg2) {
        javafx.scene.shape.Line line = new javafx.scene.shape.Line();
        line.setStartX(peg1.getLayoutX() + peg1.getWidth() / 2 + peg1.getTranslateX());
        line.setStartY(peg1.getLayoutY() + peg1.getHeight() / 2 + peg1.getTranslateY());
        line.setEndX(peg2.getLayoutX() + peg2.getWidth() / 2 + peg2.getTranslateX());
        line.setEndY(peg2.getLayoutY() + peg2.getHeight() / 2 + peg2.getTranslateY());
        line.setStrokeWidth(4);
        line.setStroke(javafx.scene.paint.Color.web("#89e3ffff"));

        contentNode.getChildren().add(line);
        line.toBack();

    }

    private void clearLines() {
        contentNode.getChildren().removeIf(node -> node instanceof javafx.scene.shape.Line);
    }

    private double widthAtHeight(double height) {
        // Return multiplier for x coord at given height based on pitch perspective
        double topInset = this.getWidth() * 0.05;
        double w = this.getWidth();
        double h = this.getHeight();
        if (h <= 0) return 0;
        double bottomWidth = w;
        double topWidth = w - 2 * topInset;
        return topWidth + (bottomWidth - topWidth) * (height / h);
    }

    public void showFormation() {
        clearLines();
        buttonGroupManager.clearLineGroups();
        double panelWidth = this.getWidth();
        double panelHeight = this.getHeight();

        for (int i = 0; i < formation.getLines().size(); i++) {
            Line line = formation.getLines().get(i);
            double yPos = panelHeight / (formation.getLines().size() + 1) * (i + 1);

            for (int j = 0; j < line.getPegs().size(); j++) {
                Peg peg = line.getPegs().get(j);
                PegButtonFX pegButton = formationButtons.stream()
                        .filter(btn -> btn.getPeg() == peg)
                        .findFirst()
                        .orElse(null);

                List<PegButtonFX> lineGroup = new ArrayList<>();
                if (pegButton != null) {
                    double xPos = panelWidth / (line.getPegs().size() + 1) * (j + 1);
                    xPos = (xPos - panelWidth / 2) * (widthAtHeight(yPos) / panelWidth) + panelWidth / 2;
                    //System.out.println("Positioning PegButtonFX for Peg " + peg.getPlayer() + " at (" + xPos + ", " + yPos + ")");
                    pegButton.setLayoutX(xPos - pegButton.getWidth() / 2);
                    pegButton.setLayoutY(yPos - pegButton.getHeight() / 2);
                    pegButton.setScaleX(((panelWidth+panelHeight)/2) * 0.003);
                    pegButton.setScaleY(((panelWidth+panelHeight)/2) * 0.003);
                    pegButton.setText(String.valueOf(peg.getPlayer().getShirtNumber()));
                    if (j > 0) {
                        final int prevIndex = j - 1;
                        final formation.Peg prevPeg = line.getPegs().get(prevIndex);
                        PegButtonFX previousPegButton = formationButtons.stream()
                                .filter(btn -> btn.getPeg() == prevPeg)
                                .findFirst()
                                .orElse(null);
                        if (previousPegButton != null) {
                            drawLine(previousPegButton, pegButton);
                        }
                    }
                    lineGroup.add(pegButton);
                }
                if (interactable) {
                    buttonGroupManager.addLineGroup(lineGroup);
                }
            }
        }

    }

}