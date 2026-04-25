package gui;

public class FormationSegmentFX extends javafx.scene.shape.Line implements DropTarget {
    protected FormationLineFX parentLine;

    FormationSegmentFX(double startX, double startY, double endX, double endY, FormationLineFX parentLine) {
        super(startX, startY, endX, endY);
        this.parentLine = parentLine;
        this.getProperties().put("isDropTarget", true);
        DropTargetList.registerDropTarget(this);
        setDefaultStyle();
    }
    FormationSegmentFX(FormationLineFX parentLine) {
        super();
        this.parentLine = parentLine;
        this.getProperties().put("isDropTarget", true);
        DropTargetList.registerDropTarget(this);
        setDefaultStyle();
    }

    public void setDefaultStyle() {
        this.setStrokeWidth(4);
        this.setStroke(javafx.scene.paint.Color.web("#89e3ffff"));
    }

    public void setHighlightedStyle() {
        this.setStrokeWidth(10);
        this.setStroke(javafx.scene.paint.Color.web("rgba(255, 234, 0, 0.57)"));
    }

    @Override
    public void setHighlighted(boolean highlighted) {
        parentLine.setHighlighted(highlighted);
    }

    @Override
    public boolean isHighlighted() {
        return parentLine.isHighlighted();
    }

    public FormationLineFX getParentLine() {
        return parentLine;
    }

    public int getSegmentIndex() {
        return parentLine.segments.indexOf(this);
    }
    
}
