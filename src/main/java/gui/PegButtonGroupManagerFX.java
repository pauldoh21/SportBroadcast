package gui;

import java.util.List;

import formation.Peg;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class PegButtonGroupManagerFX {
    private List<PegButtonSelectableFX> buttonGroup;
    private List<List<PegButtonFX>> lineGroups;
    protected List<PegButtonSelectableFX> selectedButtons;
    private FormationDisplayFX formationDisplay;
    private Node dragArea;
    private double dragStep;
    private int maxSelections = 11;

    public PegButtonGroupManagerFX() {
        this.buttonGroup = new java.util.ArrayList<>();
        lineGroups = new java.util.ArrayList<>();
        selectedButtons = new java.util.ArrayList<>();
    }

    public PegButtonGroupManagerFX(List<PegButtonSelectableFX> buttonGroup) {
        this.buttonGroup = buttonGroup;
        lineGroups = new java.util.ArrayList<>();
        selectedButtons = new java.util.ArrayList<>();
    }

    public void setButtonGroup(List<PegButtonSelectableFX> buttonGroup) {
        this.buttonGroup = buttonGroup;
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

    public List<PegButtonSelectableFX> getSelectedButtons() {
        return selectedButtons;
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
        if (!shiftDown) {
            selectedButtons.clear();
            selectedButtons.add(button);
        } else {
            if (!selectedButtons.contains(button)) {
                selectedButtons.add(button);
                if (selectedButtons.size() > maxSelections) {
                    selectedButtons.remove(0);
                }
            }
        }
        selectButtons();
    }

    protected void selectButtons() {
        for (PegButtonSelectableFX b : buttonGroup) {
            if (selectedButtons.contains(b)) {
                b.setSelected(true);
            } else {
                b.setSelected(false);
            }
        }
        PegButtonSelectableFX lastSelected = selectedButtons.get(selectedButtons.size() - 1);
        lastSelected.fireEvent(new PegSelectedEvent(lastSelected.getPeg(), this));
    }

    public void deselectAll() {
        selectedButtons.clear();
        for (PegButtonSelectableFX b : buttonGroup) {
            b.setSelected(false);
        }
    }

    // TODO: If anything that is not a peg button is clicked, deselect all


    public void adjustPeg(double deltaX, double deltaY) {
        for (PegButtonSelectableFX button : selectedButtons) {
            Peg peg = button.getPeg();
            peg.setxAdjustment(peg.getxAdjustment() + deltaX);
            peg.setyAdjustment(peg.getyAdjustment() + deltaY);
        }
    }

    public void finishedDragging() {
        //formationDisplay.showFormation();
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

    protected void buttonsAdjusted() {
        
    }
}
