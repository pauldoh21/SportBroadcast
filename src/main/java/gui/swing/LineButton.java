package gui.swing;
import javax.swing.*;

import formation.Line;

public class LineButton extends JButton {
    Line line;
    private double initialY;
    private boolean initialised = false;

    public LineButton(Line line) {
        this.line = line;
        this.setText("↕");
    }

    public Line getLine() {
        return line;
    }

    public boolean setInitialY(double y) {
        if (initialised) return false;
        this.initialY = y;
        this.initialised = true;
        return initialised;
    }

    // Drag button to adjust line Y position
    @Override
    protected void processMouseMotionEvent(java.awt.event.MouseEvent e) {
        if (e.getID() == java.awt.event.MouseEvent.MOUSE_DRAGGED) {
            double panelHeight = this.getParent().getHeight();
            double newY = (e.getYOnScreen() - this.getParent().getLocationOnScreen().y - (initialY * panelHeight));
            System.out.println("New Y: " + newY + "\nLocationOnScreen " + this.getParent().getLocationOnScreen().y + "\nInitial Y: " + initialY + "\nPanel Height: " + panelHeight);
            line.setYOffset(newY);
            FormationPanel parentPanel = (FormationPanel) this.getParent();
            parentPanel.redrawFormation();
        }
        super.processMouseMotionEvent(e);
    }

}
