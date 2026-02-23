package gui;

import java.util.ArrayList;
import java.util.List;

import gui.action.Action;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PegActionMenuFX extends Pane {

    protected PegButtonSelectableFX currentPegButton;
    protected List<Button> actionButtons = new ArrayList<>();
    protected PegButtonSelectableFX target;

    protected double radius = 60;

    public PegActionMenuFX() {
        setManaged(false);

        setMouseTransparent(false);
        setPickOnBounds(false);
        setVisible(false);

        double diameter = radius * 2;
        setPrefSize(diameter, diameter);
        setMinSize(diameter, diameter);
        setMaxSize(diameter, diameter);
    }

    protected Button newActionButton(Action a) {
        double size = 36;
        Button ab = new Button();
        if (a.toSymbolNode() == null) {
            ab.setText(a.toString());
        } else {
            StackPane graphic = a.toSymbolNode();
            double graphicSize = size * 0.6;
            graphic.setPrefSize(graphicSize, graphicSize);
            graphic.setMinSize(graphicSize, graphicSize);
            graphic.setMaxSize(graphicSize, graphicSize);
            ab.setGraphic(graphic);
        }
        ab.setPrefSize(size, size);
        ab.setMinSize(size, size);
        ab.setMaxSize(size, size);
        ab.setShape(new Circle(size / 2));
        ab.setStyle("-fx-background-radius: 999; -fx-border-radius: 999; -fx-padding: 0;");
        ab.setOnMouseClicked(e -> actionButtonFired(ab, e, a));
        return ab;
    }

    protected void actionButtonFired(Button ab, MouseEvent e, Action a) {
        if (target.getPeg().getPlayer() == null) {
            return;
        }
        ab.fireEvent(a.createEvent(target.getPeg().getPlayer()));
    }

    public void showFor(PegButtonSelectableFX target, List<Action> availableActions) {
        this.target = target;
        getChildren().clear();
        actionButtons.clear();

        double centerX = radius;
        double centerY = radius;

        int count = availableActions.size();

        double sweepAngle = Math.toRadians(120);
        double startAngle = -Math.PI / 2 - sweepAngle / 2;

        for (int i = 0; i < count; i++) {
            Button b = newActionButton(availableActions.get(i));

            b.applyCss();
            b.autosize();

            double t = (count == 1) ? 0.5 : (double) i / (count - 1);
            double angle = startAngle + sweepAngle * t;

            double x = centerX + Math.cos(angle) * radius - b.getWidth() / 2;
            double y = centerY + Math.sin(angle) * radius - b.getHeight() / 2;

            b.setLayoutX(x);
            b.setLayoutY(y);

            actionButtons.add(b);
        }

        getChildren().addAll(actionButtons);

        // Debug markers
        /* Circle centerMarker = new Circle(centerX, centerY, 4, Color.RED);
        Circle borderMarker = new Circle(centerX, centerY, RADIUS);
        borderMarker.setStroke(Color.RED);
        borderMarker.setFill(Color.TRANSPARENT);

        getChildren().addAll(borderMarker, centerMarker); */

        // Position the menu centered on the target peg
        Point2D scenePos = target.localToScene(
                target.getWidth() / 2,
                target.getHeight() / 2
        );
        Point2D parentPos = getParent().sceneToLocal(scenePos);

        setLayoutX(parentPos.getX() - radius);
        setLayoutY(parentPos.getY() - radius);

        /* setBorder(new Border(new BorderStroke(
                Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT
        ))); */

        setVisible(true);
    }

    protected void relocate() {
        if (target == null) {
            return;
        }

        // Position the menu centered on the target peg
        Point2D scenePos = target.localToScene(
                target.getWidth() / 2,
                target.getHeight() / 2
        );
        Point2D parentPos = getParent().sceneToLocal(scenePos);

        setLayoutX(parentPos.getX() - radius);
        setLayoutY(parentPos.getY() - radius);
        setRadius(target.getRadius() * 1.8);
    }

    public void setRadius(double radius) {
        this.radius = radius;

        double centerX = radius;
        double centerY = radius;

        int count = actionButtons.size();

        double sweepAngle = Math.toRadians(120);
        double startAngle = -Math.PI / 2 - sweepAngle / 2;

        for (int i = 0; i < count; i++) {
            Button b = actionButtons.get(i);

            b.applyCss();
            b.autosize();

            double t = (count == 1) ? 0.5 : (double) i / (count - 1);
            double angle = startAngle + sweepAngle * t;

            double x = centerX + Math.cos(angle) * radius - b.getWidth() / 2;
            double y = centerY + Math.sin(angle) * radius - b.getHeight() / 2;

            b.setLayoutX(x);
            b.setLayoutY(y);

        }
    }

    public void hide() {
        setVisible(false);
        target = null;
    }
}
