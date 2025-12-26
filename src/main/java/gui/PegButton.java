package gui;

import java.awt.Color;

import javax.swing.JButton;

import formation.Peg;

public class PegButton extends JButton {
    private Peg peg;

    public PegButton(Peg peg) {
        this.peg = peg;
        configureButton();
    }

    public Peg getPeg() {
        return peg;
    }

    private void configureButton() {
        this.setBackground(Color.BLUE);
        this.setForeground(Color.WHITE);
    }
}
