package gui;

import java.util.ArrayList;
import java.util.List;

import formation.Formation;
import formation.Line;
import formation.Peg;
import gui.Layout.PanelNodeFX;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class FormationDisplayFX extends PanelNodeFX {
    Formation formation;
    PanelNodeFX contentNode;
    protected PegButtonGroupManagerFX buttonGroupManager;
    protected FormationDisplayBackgroundFX background;
    protected List<PegButtonFX> formationButtons;
    protected StackPane stack;

    protected double pegButtonScale = 0.003;
    protected java.util.Map<Peg, double[]> lastPositions = new java.util.HashMap<>(); // Cache positions as [x, y]

    protected boolean positionChanged = false;

    public FormationDisplayFX(Formation formation) {
        super();
        this.formation = formation;
        setBordered(false);

        formationButtons = new ArrayList<>();

        initialiseStack();
        initialiseButtonGroupManager();
        setContent();
        initialiseButtons();
        showFormation();
    }

    protected void initialiseButtonGroupManager() {
        buttonGroupManager = null;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
        updateButtons();
        showFormation();
    }

    protected void setContent() {
        this.setContentNode(stack);
    }

    protected void initialiseStack() {
        contentNode = new PanelNodeFX();
        contentNode.setBordered(false);
        background = new FormationDisplayBackgroundFX();
        stack = new StackPane(background, contentNode);
        background.prefWidthProperty().bind(stack.widthProperty());
        background.prefHeightProperty().bind(stack.heightProperty());

        contentNode.prefWidthProperty().bind(stack.widthProperty());
        contentNode.prefHeightProperty().bind(stack.heightProperty());

        stack.prefWidthProperty().bind(this.widthProperty());
        stack.prefHeightProperty().bind(this.heightProperty());
    }

    protected StackPane getStack() {
        return stack;
    }

    protected PegButtonFX initialiseButton(Peg peg) {
        PegButtonFX pegButton = new PegButtonFX(peg);
        return pegButton;
    }

    protected void initialiseButtons() {
        formationButtons.clear();
        lastPositions.clear(); // Clear cached positions when buttons are reinitialized
        if (background == null) {
            initialiseStack();
        }

        // remove only peg buttons so the background/content node stays behind
        this.getChildren().removeIf(node -> node instanceof PegButtonFX);

        for (Line line : formation.getLines()) {
            for (Peg peg : line.getPegs()) {
                PegButtonFX button = initialiseButton(peg);
                formationButtons.add(button);
                contentNode.getChildren().add(button);
            }
        }
    }

    protected void refreshButtons() {
        for (PegButtonFX button : formationButtons) {
            button.refresh();
        }
    }

    protected void updateButton(PegButtonFX button, Peg peg) {
        button.setPeg(peg);
    }

    protected void updateButtons() {
        for (PegButtonFX button : formationButtons) {
            updateButton(button, formation.getPegAtIndex(formationButtons.indexOf(button)));
        }
    }

    // Listener to update formation display on resize
    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        showFormation();
    }

    private void drawLine(PegButtonFX peg1, PegButtonFX peg2, FormationLineFX parentLine) {
        FormationSegmentFX segment = new FormationSegmentFX(parentLine);
        segment.setStartX(peg1.getLayoutX() + peg1.getWidth() / 2 + peg1.getTranslateX());
        segment.setStartY(peg1.getLayoutY() + peg1.getHeight() / 2 + peg1.getTranslateY());
        segment.setEndX(peg2.getLayoutX() + peg2.getWidth() / 2 + peg2.getTranslateX());
        segment.setEndY(peg2.getLayoutY() + peg2.getHeight() / 2 + peg2.getTranslateY());

        parentLine.addSegment(segment);
        segment.toBack();

    }

    private void drawLines() {
        for (int i = 0; i < formation.getLines().size(); i++) {
            Line line = formation.getLines().get(i);
            FormationLineFX formationLine = new FormationLineFX(i);

            for (int j = 1; j < line.getPegs().size(); j++) {
                Peg peg = line.getPegs().get(j);
                PegButtonFX pegButton = getPegButton(peg);

                if (pegButton != null) {
                    final int prevIndex = j - 1;
                    final formation.Peg prevPeg = line.getPegs().get(prevIndex);
                    PegButtonFX previousPegButton = getPegButton(prevPeg);
                    if (previousPegButton != null) {
                        drawLine(previousPegButton, pegButton, formationLine);
                    }
                }
            }
            formationLine.addToNode(contentNode);
        }
    }

    private void clearLines() {
        contentNode.getChildren().removeIf(node -> node instanceof FormationSegmentFX);
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

    private PegButtonFX getPegButton(Peg peg) {
        for (PegButtonFX button : formationButtons) {
            if (button.getPeg() == peg) {
                return button;
            }
        }
        return null;
    }

    private void placeButton(PegButtonFX button, double xPos, double yPos) {
        button.setLayoutX(xPos - button.getWidth() / 2);
        button.setLayoutY(yPos - button.getHeight() / 2);
        pegButtonScale = ((this.getWidth()+this.getHeight())/2) * 0.003;
        button.setRadius(15.0 * pegButtonScale);
    }

    protected double getDisplayHeight() {
        return this.getHeight();
    }

    protected double getDisplayWidth() {
        return this.getWidth();
    }

    // TODO: Convert this to a refresh function which will animate changes
    protected void showFormation() {
        double panelWidth = getDisplayWidth();
        double panelHeight = getDisplayHeight();

        for (int i = 0; i < formation.getLines().size(); i++) {
            Line line = formation.getLines().get(i);
            double yPos = panelHeight / (formation.getLines().size() + 1) * (i + 1);

            for (int j = 0; j < line.getPegs().size(); j++) {
                Peg peg = line.getPegs().get(j);
                PegButtonFX pegButton = getPegButton(peg);

                if (pegButton != null) {
                    double xPos = panelWidth / (line.getPegs().size() + 1) * (j + 1);
                    xPos = (xPos - panelWidth / 2) * (widthAtHeight(yPos) / panelWidth) + panelWidth / 2;
                    double relativeAdjustX = peg.getxAdjustment()/100.0 * widthAtHeight(yPos);
                    double relativeAdjustY = peg.getyAdjustment()/100.0 * panelHeight;
                    
                    double finalX = xPos + relativeAdjustX;
                    double finalY = yPos + relativeAdjustY;
                    
                    // Only update if position changed
                    double[] cachedPos = lastPositions.get(peg);
                    if (cachedPos == null || cachedPos[0] != finalX || cachedPos[1] != finalY) {
                        placeButton(pegButton, finalX, finalY);
                        lastPositions.put(peg, new double[]{finalX, finalY});
                        positionChanged = true;
                    }
                }
            }
        }
        
        // Only redraw lines if positions changed
        if (positionChanged) {
            clearLines();
            drawLines();
            refreshButtons();
            positionChanged = false;
        }
    }

}