package gui.Dragging;

import gui.DropTarget;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.AnchorPane;
import javafx.scene.SnapshotParameters;
import javafx.scene.paint.Color;

public class DraggableNode {

    protected final Node node;
    protected boolean isDragging;

    private static final double DRAG_THRESHOLD = 6;
    private static AnchorPane globalDragLayer;

    private ImageView ghost;

    private boolean dragArmed;
    private double pressSceneX;
    private double pressSceneY;

    private double mouseOffsetX;
    private double mouseOffsetY;

    private Node previousHoverTarget = null;

    public DraggableNode(Node node) {
        this.node = node;
        setupDragging();
    }

    public static void setGlobalDragLayer(AnchorPane dragLayer) {
        globalDragLayer = dragLayer;
    }

    private void setupDragging() {
        node.setOnMousePressed(this::onMousePressed);
        node.setOnMouseDragged(this::onMouseDragged);
        node.setOnMouseReleased(this::onMouseReleased);
    }

    /* ---------------- Mouse lifecycle ---------------- */

    private void onMousePressed(MouseEvent e) {
        dragArmed = true;
        isDragging = false;

        pressSceneX = e.getSceneX();
        pressSceneY = e.getSceneY();
    }

    private void onMouseDragged(MouseEvent e) {
        if (!dragArmed) return;

        double dx = e.getSceneX() - pressSceneX;
        double dy = e.getSceneY() - pressSceneY;

        if (!isDragging) {
            if (dx * dx + dy * dy < DRAG_THRESHOLD * DRAG_THRESHOLD) {
                return; // still a click
            }

            startDrag(e);
            isDragging = true;
        }

        drag(e);
        e.consume();
    }

    private void onMouseReleased(MouseEvent e) {
        if (isDragging) {
            endDrag(e);
            e.consume();
        }

        dragArmed = false;
        isDragging = false;
    }

    /* ---------------- Drag lifecycle ---------------- */

    private void startDrag(MouseEvent e) {
        if (globalDragLayer == null) return;

        ghost = createGhost(node);

        Bounds bounds = node.localToScene(node.getBoundsInLocal());

        mouseOffsetX = e.getSceneX() - bounds.getMinX();
        mouseOffsetY = e.getSceneY() - bounds.getMinY();

        Point2D start = globalDragLayer.sceneToLocal(
                bounds.getMinX(),
                bounds.getMinY()
        );

        ghost.relocate(start.getX(), start.getY());
        ghost.setManaged(false);
        ghost.setMouseTransparent(true);

        globalDragLayer.getChildren().add(ghost);
        ghost.toFront();

        onDragStart();
    }

    private void drag(MouseEvent e) {
        if (ghost == null) return;

        Point2D p = globalDragLayer.sceneToLocal(e.getSceneX(), e.getSceneY());

        double x = p.getX() - mouseOffsetX;
        double y = p.getY() - mouseOffsetY;

        double maxX = globalDragLayer.getWidth()
                - ghost.getBoundsInParent().getWidth();
        double maxY = globalDragLayer.getHeight()
                - ghost.getBoundsInParent().getHeight();

        x = clamp(x, 0, maxX);
        y = clamp(y, 0, maxY);

        ghost.relocate(x, y);
        hover(e);
        onDrag();
    }

    private void endDrag(MouseEvent e) {
        PickResult pick = e.getPickResult();
        Node picked = pick.getIntersectedNode();

        handleDropTarget(picked, pick);

        globalDragLayer.getChildren().remove(ghost);
        ghost = null;

        onDragEnd();
    }

    private void hover(MouseEvent e) {
        PickResult pick = e.getPickResult();
        Node picked = pick.getIntersectedNode();

        Node dropTarget = findDropTarget(picked);
        if (dropTarget != null && dropTarget instanceof DropTarget dt) {
            // Handle hover over drop target
            dt.setHighlighted(true);
            onHover(dropTarget);
            previousHoverTarget = dropTarget;
            return;
        }
        if (previousHoverTarget != null && previousHoverTarget instanceof DropTarget pdt) {
            pdt.setHighlighted(false);
        }
        onUnhover(previousHoverTarget);
    }

    /* ---------------- Drop handling ---------------- */

    protected void handleDropTarget(Node pickedNode, PickResult pick) {
        Node target = findDropTarget(pickedNode);

        if (target == null) {
            onDropRejected();
        } else {
            onDropAccepted(target, pick);
        }
    }

    protected Node findDropTarget(Node node) {
        Node current = node;
        while (current != null) {
            if (current.getProperties().containsKey("isDropTarget")) {
                return current;
            }
            current = current.getParent();
        }
        return null;
    }

    protected void onDropAccepted(Node target, PickResult pick) {
        System.out.println("Dropped on target: " + target);
    }

    protected void onDropRejected() {
        System.out.println("Drop rejected: no valid target found.");
    }

    /* ---------------- Utilities ---------------- */

    private ImageView createGhost(Node node) {
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);

        WritableImage image = node.snapshot(params, null);

        ImageView iv = new ImageView(image);
        iv.setOpacity(0.8);
        return iv;
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    /* ---------------- Hooks ---------------- */

    protected void onDragStart() {}
    protected void onDrag() {}
    protected void onDragEnd() {}
    protected void onHover(Node target) {}
    protected void onUnhover(Node prevTarget) {}

    public boolean isDragging() {
        return isDragging;
    }

    public Node getNode() {
        return node;
    }
}
