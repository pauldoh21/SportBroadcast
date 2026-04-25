package gui;

import gui.Dragging.DraggableNode;
import gui.Dragging.PegHoveredEvent;
import gui.Dragging.PegLineSwapEvent;
import gui.Dragging.PegSwapEvent;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.PickResult;

public class PegButtonSelectableFX extends PegButtonFX implements DropTarget {
    boolean selected;
    boolean highlighted;
    boolean transparent;
    PegButtonGroupManagerFX buttonGroupManager;

    public PegButtonSelectableFX(formation.Peg peg) {
        super(peg);
        selected = false;
        this.setCursor(Cursor.HAND);
        initialiseDragging();
        this.getProperties().put("isDropTarget", true);
        DropTargetList.registerDropTarget(this);
        this.setOnMouseClicked(e -> buttonGroupManager.buttonFired(this, e));
    }

    private void initialiseDragging() {
        new DraggableNode(this) {
            @Override
            protected void onDragStart() {
                node.setOpacity(0.0);
                node.setCursor(Cursor.CLOSED_HAND);
            }

            @Override
            protected void onDrag() {
                super.onDrag();
                buttonGroupManager.dragging((PegButtonSelectableFX) node);
            }

            @Override
            protected void onDragEnd() {
                PegButtonSelectableFX button = (PegButtonSelectableFX) node;
                node.setOpacity(1.0);
                button.setCursor(Cursor.HAND);
            }

            @Override
            protected void onDropAccepted(Node dropTarget, PickResult pick) {
                PegButtonSelectableFX button = (PegButtonSelectableFX) node;
                if (dropTarget instanceof PegButtonSelectableFX pbsfx) {
                    button.fireEvent(new PegSwapEvent(button, pbsfx));
                } else if (dropTarget instanceof FormationSegmentFX fsfx) {
                    button.fireEvent(new PegLineSwapEvent(button, fsfx));
                }
            }

            @Override
            protected void onHover(Node target) {
                PegButtonSelectableFX button = (PegButtonSelectableFX) node;
                button.fireEvent(new PegHoveredEvent(PegHoveredEvent.PEG_HOVERED, button, target));
            }

            @Override
            protected void onUnhover(Node prevTarget) {
                PegButtonSelectableFX button = (PegButtonSelectableFX) node;
                button.fireEvent(new PegHoveredEvent(PegHoveredEvent.PEG_UNHOVERED, button, prevTarget));
            }
        };
    }

    public void setButtonGroupManager(PegButtonGroupManagerFX buttonGroupManager) {
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
            this.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-background-radius: 999; -fx-border-radius: 999; -fx-border-color: yellow; -fx-border-width: 3;");
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

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setTransparent(boolean transparent) {
        this.transparent = transparent;
        updateStyle();
    }
}
