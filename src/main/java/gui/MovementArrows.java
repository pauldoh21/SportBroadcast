package gui;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class MovementArrows extends BorderPane {

    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;

    public MovementArrows() {
        initializeComponents();
        setupLayout();
        setupActions();
    }

    private void initializeComponents() {
        upButton = createTriangleButton(Direction.UP);
        downButton = createTriangleButton(Direction.DOWN);
        leftButton = createTriangleButton(Direction.LEFT);
        rightButton = createTriangleButton(Direction.RIGHT);
    }

    public void bindToParent(Region parent) {

        prefWidthProperty().bind(parent.widthProperty().multiply(0.16));
        prefHeightProperty().bind(prefWidthProperty());

        minWidthProperty().bind(prefWidthProperty());
        minHeightProperty().bind(prefHeightProperty());
        maxWidthProperty().bind(prefWidthProperty());
        maxHeightProperty().bind(prefHeightProperty());

        DoubleBinding buttonSize = prefWidthProperty().multiply(0.28);
        double dividerValue = 1.5;

        for (Button b : new Button[]{leftButton, rightButton}) {
            b.prefWidthProperty().bind(buttonSize.divide(dividerValue));
            b.prefHeightProperty().bind(buttonSize);
            b.minWidthProperty().bind(buttonSize.divide(dividerValue));
            b.minHeightProperty().bind(buttonSize);
            b.maxWidthProperty().bind(buttonSize.divide(dividerValue));
            b.maxHeightProperty().bind(buttonSize);
        }

        for (Button b : new Button[]{upButton, downButton}) {
            b.prefWidthProperty().bind(buttonSize);
            b.prefHeightProperty().bind(buttonSize.divide(dividerValue));
            b.minWidthProperty().bind(buttonSize);
            b.minHeightProperty().bind(buttonSize.divide(dividerValue));
            b.maxWidthProperty().bind(buttonSize);
            b.maxHeightProperty().bind(buttonSize.divide(dividerValue));
        }
    }

    private void setupLayout() {
        setTop(upButton);
        setBottom(downButton);
        setLeft(leftButton);
        setRight(rightButton);

        BorderPane.setAlignment(upButton, Pos.TOP_CENTER);
        BorderPane.setAlignment(downButton, Pos.BOTTOM_CENTER);
        BorderPane.setAlignment(leftButton, Pos.CENTER_LEFT);
        BorderPane.setAlignment(rightButton, Pos.CENTER_RIGHT);

        setStyle("-fx-padding: 5px;");
        setPickOnBounds(false);
    }

    private void setupActions() {
        upButton.setOnAction(e -> moveUp());
        downButton.setOnAction(e -> moveDown());
        leftButton.setOnAction(e -> moveLeft());
        rightButton.setOnAction(e -> moveRight());
    }

    private Button createTriangleButton(Direction dir) {
        Button button = new Button();
        button.setText(null);

        Polygon triangle = new Polygon();

        switch (dir) {
            case UP -> triangle.getPoints().addAll(
                    50.0, 0.0,
                    80.0, 100.0,
                    20.0, 100.0
            );
            case DOWN -> triangle.getPoints().addAll(
                    20.0, 0.0,
                    80.0, 0.0,
                    50.0, 100.0
            );
            case LEFT -> triangle.getPoints().addAll(
                    0.0, 50.0,
                    80.0, 20.0,
                    80.0, 80.0
            );
            case RIGHT -> triangle.getPoints().addAll(
                    20.0, 0.0,
                    100.0, 50.0,
                    20.0, 100.0
            );
        }

        triangle.setFill(Color.DIMGRAY);

        // Make the button itself the triangle
        button.setShape(triangle);
        button.setStyle("""
            -fx-background-color: lightgray;
            -fx-padding: 0;
        """);

        // Scale shape with button
        triangle.scaleXProperty().bind(button.widthProperty().divide(100));
        triangle.scaleYProperty().bind(button.heightProperty().divide(100));

        return button;
    }

    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private void moveUp() { System.out.println("Move Up"); }
    private void moveDown() { System.out.println("Move Down"); }
    private void moveLeft() { System.out.println("Move Left"); }
    private void moveRight() { System.out.println("Move Right"); }

    public void hide() { setVisible(false); }
    public void show() { setVisible(true); }

    public Button getUpButton() { return upButton; }
    public Button getDownButton() { return downButton; }
    public Button getLeftButton() { return leftButton; }
    public Button getRightButton() { return rightButton; }
}
