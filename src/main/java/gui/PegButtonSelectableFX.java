package gui;

import javafx.scene.Cursor;

public class PegButtonSelectableFX extends PegButtonFX {
    boolean selected;
    boolean highlighted;
    boolean transparent;
    PegButtonGroupManagerFX buttonGroupManager;

    public PegButtonSelectableFX(formation.Peg peg) {
        super(peg);
        selected = false;
        this.setCursor(Cursor.HAND);
        makeDraggable();
        this.setOnMouseClicked(e -> buttonGroupManager.buttonFired(this, e));
    }

    public void setButtonGroup(PegButtonGroupManagerFX buttonGroupManager) {
        this.buttonGroupManager = buttonGroupManager;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        updateStyle();
    }

    public boolean isSelected() {
        return selected;
    }

    private void updateStyle() {
        if (selected) {
            this.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 999; -fx-border-radius: 999;");
        } else if (highlighted) {
            this.setStyle("-fx-background-color: orange; -fx-text-fill: white; -fx-background-radius: 999; -fx-border-radius: 999;");
        } else if (transparent) {
            this.setStyle("-fx-background-color: rgba(0,0,255,0.3); -fx-text-fill: white; -fx-background-radius: 999; -fx-border-radius: 999;");
        } else {
            this.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-background-radius: 999; -fx-border-radius: 999;");
        }
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        updateStyle();
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
        updateStyle();
    }

    private double startX;
    private double startY;

    private void makeDraggable() {
        this.setOnMousePressed(e -> {
            startX = e.getSceneX() - this.getTranslateX();
            startY = e.getSceneY() - this.getTranslateY();
        });

        this.setOnMouseDragged(e -> {
            this.toFront();
            this.setCursor(Cursor.CLOSED_HAND);
            double dragStep = buttonGroupManager.getDragStep();
            double newX = e.getSceneX() - startX;
            double newY = e.getSceneY() - startY;
            if (dragStep > 0) {
                newX = Math.round(newX / dragStep) * dragStep;
                newY = Math.round(newY / dragStep) * dragStep;
            }
            this.setTranslateX(newX);
            this.setTranslateY(newY);
            buttonGroupManager.dragging(this);
        });

        this.setOnMouseReleased(e -> {
            double newX = e.getSceneX() - startX;
            double newY = e.getSceneY() - startY;
            this.getPeg().setxAdjustment(newX);
            this.getPeg().setyAdjustment(newY);
            buttonGroupManager.finishedDragging();
            this.setTranslateX(this.getPeg().getxAdjustment());
            this.setTranslateY(this.getPeg().getyAdjustment());
            this.setCursor(Cursor.HAND);
        });
    }
    
}
