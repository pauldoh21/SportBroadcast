package gui;

import javafx.geometry.Insets;
import javafx.scene.control.OverrunStyle;
import javafx.scene.text.Font;

public class PegButtonFX extends javafx.scene.control.Button {
    protected formation.Peg peg;
    protected double radius;

    public PegButtonFX(formation.Peg peg) {
        this.peg = peg;
        configureButton();
    }

    public formation.Peg getPeg() {
        return peg;
    }

    public void setPeg(formation.Peg peg) {
        this.peg = peg;
        updateText();
    }

    public void refresh() {
        updateText();
    }

    private void updateText() {
        if (peg.getPlayer() != null) {
            this.setText(peg.getPlayer().getShirtNumber() + "");
        } else {
            this.setText("");
        }
    }

    public void setRadius(double radius) {
        this.radius = radius;
        double diameter = radius * 2;
        this.setPrefSize(diameter, diameter);
        this.setMinSize(diameter, diameter);
        this.setMaxSize(diameter, diameter);
    }

    public double getRadius() {
        return radius;
    }

    private void configureButton() {
        updateText();

        setRadius(15);
        this.setPadding(Insets.EMPTY);
        this.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-background-radius: 999; -fx-border-radius: 999;");
        this.setTextOverrun(OverrunStyle.CLIP);
        this.setFont(Font.font(24));
        this.setShape(new javafx.scene.shape.Circle(radius));
    }
    
}
