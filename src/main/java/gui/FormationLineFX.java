package gui;

import java.util.List;

import formation.Formation;
import gui.Dragging.PegHoveredEvent;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class FormationLineFX extends Group {

    protected List<FormationSegmentFX> segments;
    protected int lineIndex;
    protected boolean highlighted = false;

    public FormationLineFX(List<double[]> points, int lineIndex) {
        segments = new java.util.ArrayList<>();
        this.lineIndex = lineIndex;
        for (int i = 0; i < points.size() - 1; i++) {
            double[] start = points.get(i);
            double[] end = points.get(i + 1);
            FormationSegmentFX segment = new FormationSegmentFX(start[0], start[1], end[0], end[1], this);
            segments.add(segment);
        }
        initialiseLine();
    }

    public FormationLineFX(int lineIndex) {
        segments = new java.util.ArrayList<>();
        this.lineIndex = lineIndex;
        initialiseLine();
    }

    public void addToNode(Pane parent) {
        parent.getChildren().addAll(segments);
        for (FormationSegmentFX segment : segments) {
            segment.toBack();
        }
    }

    public void addSegment(FormationSegmentFX segment) {
        segments.add(segment);
        this.getChildren().add(segment);
    }

    public void editLine(List<double[]> newPoints) {
        // Remove existing segments
        this.getChildren().clear();
        segments.clear();
        // Create new segments
        for (int i = 0; i < newPoints.size() - 1; i++) {
            double[] start = newPoints.get(i);
            double[] end = newPoints.get(i + 1);
            FormationSegmentFX segment = new FormationSegmentFX(start[0], start[1], end[0], end[1], this);
            segments.add(segment);
        }
        initialiseLine();
        // Add new segments to the group
        this.getChildren().addAll(segments);
    }

    private void initialiseLine() {
        this.addEventHandler(PegHoveredEvent.PEG_HOVERED, e -> {
            if (segments.contains(e.getHoveredNode())) {
                for (FormationSegmentFX segment : segments) {
                    segment.setHighlightedStyle();
                }
            }
        });

    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        for (FormationSegmentFX segment : segments) {
            if (highlighted) {
                segment.setHighlightedStyle();
            } else {
                segment.setDefaultStyle();
            }
        }
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public int getLineIndex() {
        return lineIndex;
    }
    
}
