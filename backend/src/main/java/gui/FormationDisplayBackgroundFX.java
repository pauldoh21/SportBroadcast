package gui;

import gui.Layout.PanelNodeFX;
import javafx.scene.paint.*;
import javafx.scene.shape.*;

public class FormationDisplayBackgroundFX extends PanelNodeFX {

    public FormationDisplayBackgroundFX() {
        setBordered(false);
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();
        drawPitch();
    }

    private void drawPitch() {
        getChildren().clear();

        double w = getWidth();
        double h = getHeight();

        if (w <= 0 || h <= 0) return;

        // Perspective factor (how narrow the far edge is)
        double topInset = w * 0.05;

        /* ===============================
           Pitch surface (skewed polygon)
           =============================== */
        Polygon pitch = new Polygon(
                topInset, 0,
                w - topInset, 0,
                w, h,
                0, h
        );

        LinearGradient grassGradient = new LinearGradient(
                0, 0, 0, 1,
                true, CycleMethod.NO_CYCLE,
                new Stop(0.0, Color.web("#4CAF50")),
                new Stop(0.5, Color.web("#2E7D32")),
                new Stop(1.0, Color.web("#1B5E20"))
        );

        pitch.setFill(grassGradient);
        pitch.setStroke(Color.web("#ffffffff"));
        pitch.setStrokeWidth(2);

        getChildren().add(pitch);

        /* ===============================
           Pitch markings
           =============================== */
        drawHalfwayLine(w, h, topInset);
        drawCenterArc(w, h, topInset);
        drawPenaltyArea(w, h, topInset);
        drawGoalArea(w, h, topInset);
    }

    /* ===============================
       Helpers
       =============================== */

    private double lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    private double leftEdge(double y, double w, double h, double inset) {
        return lerp(inset, 0, y / h);
    }

    private double rightEdge(double y, double w, double h, double inset) {
        return lerp(w - inset, w, y / h);
    }

    private double pitchWidthAt(double y, double w, double h, double inset) {
        return rightEdge(y, w, h, inset) - leftEdge(y, w, h, inset);
    }

    /* ===============================
       Markings
       =============================== */

    private void drawHalfwayLine(double w, double h, double inset) {
        double y = h;

        Line line = new Line(
                leftEdge(y, w, h, inset),
                y,
                rightEdge(y, w, h, inset),
                y
        );

        line.setStroke(Color.WHITE);
        line.setStrokeWidth(2);

        getChildren().add(line);
    }

    private void drawCenterArc(double w, double h, double inset) {
        double centerY = h;
        double radius = ((w+h)/2) * 0.2;

        Arc arc = new Arc(
                w / 2,
                centerY,
                radius,
                radius * 0.6,
                0,
                180
        );

        arc.setFill(null);
        arc.setStroke(Color.WHITE);
        arc.setStrokeWidth(2);

        getChildren().add(arc);
    }

    private void drawPenaltyArea(double w, double h, double inset) {
        double y1 = 0;
        double y2 = h * 0.25;

        double pw1 = pitchWidthAt(y1, w, h, inset);
        double pw2 = pitchWidthAt(y2, w, h, inset);

        double boxWidthRatio = 0.6;

        double left1  = leftEdge(y1, w, h, inset) + pw1 * (0.5 - boxWidthRatio / 2);
        double right1 = left1 + pw1 * boxWidthRatio;

        double left2  = leftEdge(y2, w, h, inset) + pw2 * (0.5 - boxWidthRatio / 2);
        double right2 = left2 + pw2 * boxWidthRatio;

        Polygon box = new Polygon(
                left1,  y1,
                right1, y1,
                right2, y2,
                left2,  y2
        );

        box.setFill(null);
        box.setStroke(Color.WHITE);
        box.setStrokeWidth(2);

        getChildren().add(box);
    }


    private void drawGoalArea(double w, double h, double inset) {
        double y1 = 0;
        double y2 = h * 0.12;

        double pw1 = pitchWidthAt(y1, w, h, inset);
        double pw2 = pitchWidthAt(y2, w, h, inset);

        double boxWidthRatio = 0.3; // 30% of pitch width

        double left1  = leftEdge(y1, w, h, inset) + pw1 * (0.5 - boxWidthRatio / 2);
        double right1 = left1 + pw1 * boxWidthRatio;

        double left2  = leftEdge(y2, w, h, inset) + pw2 * (0.5 - boxWidthRatio / 2);
        double right2 = left2 + pw2 * boxWidthRatio;

        Polygon box = new Polygon(
                left1,  y1,
                right1, y1,
                right2, y2,
                left2,  y2
        );

        box.setFill(null);
        box.setStroke(Color.WHITE);
        box.setStrokeWidth(2);

        getChildren().add(box);
    }

}
