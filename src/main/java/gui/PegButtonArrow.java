package gui;
import javax.swing.*;

import java.awt.*;

enum Direction {
    UP, DOWN, LEFT, RIGHT
}

public class PegButtonArrow extends JButton {
    PegButtonSelectable pegButton;
    Direction direction;

    public PegButtonArrow(PegButtonSelectable pegButton, Direction direction) {
        this.pegButton = pegButton;
        this.direction = direction;
        setLabel();
        this.addActionListener(e -> adjustPeg());
    }

    private void setLabel() {
        switch (direction) {
            case UP:
                this.setText("↑");
                break;
            case DOWN:
                this.setText("↓");
                break;
            case LEFT:
                this.setText("←");
                break;
            case RIGHT:
                this.setText("→");
                break;
        }
    }

    public Direction getDirection() {
        return direction;
    }

    private void adjustPeg() {
        switch (direction) {
            case UP:
                pegButton.adjustY(-0.01);
                break;
            case DOWN:
                pegButton.adjustY(0.01);
                break;
            case LEFT:
                pegButton.adjustX(-0.01);
                break;
            case RIGHT:
                pegButton.adjustX(0.01);
                break;
        }
    }
}
