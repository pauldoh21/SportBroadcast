package gui;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class PegButtonGroupManagerFX {
    private List<PegButtonSelectableFX> buttonGroup;
    private List<List<PegButtonFX>> lineGroups;
    private List<PegButtonSelectableFX> selectedButtons;
    private FormationDisplayFX formationDisplay;
    private Node dragArea;
    private double dragStep;
    private int maxSelections = 2;
    
    public PegButtonGroupManagerFX(List<PegButtonSelectableFX> buttonGroup) {
        this.buttonGroup = buttonGroup;
        lineGroups = new java.util.ArrayList<>();
        selectedButtons = new java.util.ArrayList<>();
    }

    public void setFormationDisplay(FormationDisplayFX formationDisplay) {
        this.formationDisplay = formationDisplay;
    }

    public void setDragArea(Node dragArea) {
        this.dragArea = dragArea;
    }

    public void addButton(PegButtonSelectableFX button) {
        buttonGroup.add(button);
    }

    public List<PegButtonSelectableFX> getButtonGroup() {
        return buttonGroup;
    }

    public void addLineGroup(List<PegButtonFX> lineGroup) {
        lineGroups.add(lineGroup);
    }

    public List<PegButtonFX> getLineGroup(int index) {
        return lineGroups.get(index);
    }

    public void clearLineGroups() {
        lineGroups.clear();
    }

    private void highlightLineGroup(List<PegButtonFX> lineGroup) {
        for (List<PegButtonFX> lg : lineGroups) {
            for (PegButtonFX b : lg) {
                if (lineGroup.contains(b)) {
                    if (b instanceof PegButtonSelectableFX psb) {
                        psb.setHighlighted(true);
                    }
                } else {
                    if (b instanceof PegButtonSelectableFX psb) {
                        psb.setTransparent(true);
                    }
                }
            }
        }
    }

    private void clearHighlighting() {
        for (List<PegButtonFX> lg : lineGroups) {
            for (PegButtonFX b : lg) {
                if (b instanceof PegButtonSelectableFX psb) {
                    psb.setHighlighted(false);
                    psb.setTransparent(false);
                }
            }
        }
    }

    public void buttonFired(PegButtonSelectableFX button, MouseEvent e) {
        selectButton(button, e.isShiftDown());
    }

    public void selectButton(PegButtonSelectableFX button, boolean shiftDown) {
        for (PegButtonSelectableFX b : buttonGroup) {
            if (b != button && !shiftDown) {
                if (selectedButtons.contains(b)) {
                    selectedButtons.remove(b);
                }
            }
        }
        if (!button.isSelected()) {
            selectedButtons.add(button);
        } else {
            selectedButtons.remove(button);
        }
        if (selectedButtons.size() > maxSelections) {
            selectedButtons.remove(0);
        }
        selectButtons();
    }

    private void selectButtons() {
        for (PegButtonSelectableFX b : buttonGroup) {
            if (selectedButtons.contains(b)) {
                b.setSelected(true);
            } else {
                b.setSelected(false);
            }
        }
    }

    public void finishedDragging() {
        formationDisplay.showFormation();
        revertToSelectedButtons();
    }

    private void revertToSelectedButtons() {
        for (PegButtonSelectableFX b : buttonGroup) {
            if (selectedButtons.contains(b)) {
                b.setSelected(true);
            } else {
                b.setSelected(false);
            }
        }
    }

    public void dragging(PegButtonSelectableFX button) {
        for (PegButtonSelectableFX b : buttonGroup) {
            if (b != button) {
                b.setSelected(false);
            } else {
                button.setSelected(true);
            }
        }
    }

    public double getDragStep() {
        return dragStep;
    }

    public void setDragStep(double dragStep) {
        this.dragStep = dragStep;
    }
}
