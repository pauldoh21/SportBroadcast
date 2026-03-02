package gui.swing;
import javax.swing.*;

import formation.Peg;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PegButtonSelectable extends JButton {
    private Peg peg;
    private boolean pegSelected;
    private ArrayList<PegButtonArrow> adjustmentArrows;
    private boolean dragging;
    
    public PegButtonSelectable(Peg peg) {
        this.peg = peg;
        adjustmentArrows = new ArrayList<>();
        this.setBackground(Color.BLUE);
        this.setForeground(Color.WHITE);
        this.addActionListener(e -> {
            ((FormationPanel) this.getParent()).selectPeg(this);
        });
        setKeyBindings();
    }

    public Peg getPeg() {
        return peg;
    }

    public void adjustX(double delta) {
        peg.setxAdjustment(peg.getxAdjustment() + delta);
        ((FormationPanel) getParent()).redrawFormation();
    }

    public void adjustY(double delta) {
        peg.setyAdjustment(peg.getyAdjustment() + delta);
        ((FormationPanel) getParent()).redrawFormation();
    }

    private void addAdjustmentArrows() {
        int buttonSize = 20;
        int offset = 30;

        JPanel parentPanel = (JPanel) this.getParent();

        PegButtonArrow upArrow = new PegButtonArrow(this, Direction.UP);
        upArrow.setBounds(this.getX() + this.getWidth() / 2 - buttonSize / 2, this.getY() - offset, buttonSize, buttonSize);
        upArrow.setVisible(false);
        parentPanel.add(upArrow);

        PegButtonArrow downArrow = new PegButtonArrow(this, Direction.DOWN);
        downArrow.setBounds(this.getX() + this.getWidth() / 2 - buttonSize / 2, this.getY() + this.getHeight() + offset - buttonSize, buttonSize, buttonSize);
        downArrow.setVisible(false);
        parentPanel.add(downArrow);

        PegButtonArrow leftArrow = new PegButtonArrow(this, Direction.LEFT);
        leftArrow.setBounds(this.getX() - offset, this.getY() + this.getHeight() / 2 - buttonSize / 2, buttonSize, buttonSize);
        upArrow.setVisible(false);
        parentPanel.add(leftArrow);

        PegButtonArrow rightArrow = new PegButtonArrow(this, Direction.RIGHT);
        rightArrow.setBounds(this.getX() + this.getWidth() + offset - buttonSize, this.getY() + this.getHeight() / 2 - buttonSize / 2, buttonSize, buttonSize);
        upArrow.setVisible(false);
        parentPanel.add(rightArrow);

        adjustmentArrows.add(upArrow);
        adjustmentArrows.add(downArrow);
        adjustmentArrows.add(leftArrow);
        adjustmentArrows.add(rightArrow);
    }

    public void moveArrows() {
        int buttonSize = 20;
        int offset = 30;

        FormationPanel parentPanel = (FormationPanel) this.getParent();

        int x = parentPanel.getSelectedCoordinates()[0];
        int y = parentPanel.getSelectedCoordinates()[1];

        for (PegButtonArrow arrow : adjustmentArrows) {
            switch (arrow.getDirection()) {
                case UP -> arrow.setLocation(x + this.getWidth() / 2 - buttonSize / 2, y - offset);
                case DOWN -> arrow.setLocation(x + this.getWidth() / 2 - buttonSize / 2, y + this.getHeight() + offset - buttonSize);
                case LEFT -> arrow.setLocation(x - offset, y + this.getHeight() / 2 - buttonSize / 2);
                case RIGHT -> arrow.setLocation(x + this.getWidth() + offset - buttonSize, y + this.getHeight() / 2 - buttonSize / 2);
            }
        }
    }

    public void showAdjustmentArrows() {
        if (adjustmentArrows.isEmpty()) {
            addAdjustmentArrows();
        }
        for (PegButtonArrow arrow : adjustmentArrows) {
            arrow.setVisible(true);
        }
        JPanel parentPanel = (JPanel) this.getParent();
        parentPanel.revalidate();
        parentPanel.repaint();
    }

    public void hideAdjustmentArrows() {
        if (adjustmentArrows.isEmpty()) {
            addAdjustmentArrows();
        }
        for (PegButtonArrow arrow : adjustmentArrows) {
            arrow.setVisible(false);
        }
        JPanel parentPanel = (JPanel) this.getParent();
        parentPanel.revalidate();
        parentPanel.repaint();
    }

    public void select() {
        if (pegSelected) return;
        pegSelected = true;
        this.setBorder(BorderFactory.createLineBorder(Color.YELLOW, 3));
        showAdjustmentArrows();
    }

    public void deselect() {
        if (!pegSelected) return;
        pegSelected = false;
        this.setBorder(UIManager.getBorder("Button.border"));
        hideAdjustmentArrows();
    }

    public boolean isPegSelected() {
        return pegSelected;
    }

    @Override
    public void repaint() {
        super.repaint();
    }

    public boolean isDragging() {
        return dragging;
    }

    public void swapPegs(PegButtonSelectable otherPegButton) {
        Peg thisPeg = this.getPeg();
        Peg otherPeg = otherPegButton.getPeg();

        thisPeg.swapPeg(otherPeg);

        FormationPanel parentPanel = (FormationPanel) this.getParent();
        parentPanel.selectPeg(otherPegButton);
        parentPanel.redrawFormation();
    }

    @Override
    protected void processMouseMotionEvent(java.awt.event.MouseEvent e) {
        FormationPanel parentPanel = (FormationPanel) this.getParent();
        if (e.getID() == java.awt.event.MouseEvent.MOUSE_DRAGGED && isPegSelected()) {
            dragging = true;
            setLocation(e.getXOnScreen() - this.getWidth() / 2 - this.getParent().getLocationOnScreen().x,
                        e.getYOnScreen() - this.getHeight() / 2 - this.getParent().getLocationOnScreen().y);
            parentPanel.redrawFormation();
        }
        super.processMouseMotionEvent(e);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        FormationPanel parentPanel = (FormationPanel) this.getParent();

        if (e.getID() == MouseEvent.MOUSE_RELEASED && isDragging() && isPegSelected()) {
            dragging = false;

            for (PegButtonSelectable otherPegButton : parentPanel.getFormationButtons()) {
                if (otherPegButton != this) {
                    double distance = Math.hypot(this.getX() - otherPegButton.getX(), this.getY() - otherPegButton.getY());
                    System.out.println(distance);
                    if (distance < 30) {
                        this.swapPegs(otherPegButton);
                        break;
                    }
                }
            }

            parentPanel.redrawFormation();
        }
    }

    public void setKeyBindings() {
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "left pressed");
        this.getActionMap().put("left pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pegSelected) {
                    adjustX(-0.01);
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "right pressed");
        this.getActionMap().put("right pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pegSelected) {
                    adjustX(0.01);
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "up pressed");
        this.getActionMap().put("up pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pegSelected) {
                    adjustY(-0.01);
                }
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "down pressed");
        this.getActionMap().put("down pressed", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pegSelected) {
                    adjustY(0.01);
                }
            }
        });
    }
        
    
}
