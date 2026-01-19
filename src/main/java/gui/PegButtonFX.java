package gui;

import javafx.geometry.Insets;
import javafx.scene.control.OverrunStyle;
import javafx.scene.text.Font;

public class PegButtonFX extends javafx.scene.control.Button {
    private formation.Peg peg;

    public PegButtonFX(formation.Peg peg) {
        this.peg = peg;
        configureButton();
    }

    public formation.Peg getPeg() {
        return peg;
    }

    private void configureButton() {
        double baseSize = 30.0;
        this.setPrefSize(baseSize, baseSize);
        this.setMinSize(baseSize, baseSize);
        this.setMaxSize(baseSize, baseSize);
        this.setPadding(Insets.EMPTY);
        this.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-background-radius: 999; -fx-border-radius: 999;");
        this.setTextOverrun(OverrunStyle.CLIP);
        this.setFont(Font.font(12));
        this.setShape(new javafx.scene.shape.Circle(baseSize / 2.0));
    }
    
}
