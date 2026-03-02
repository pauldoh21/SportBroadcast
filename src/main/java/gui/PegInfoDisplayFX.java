package gui;

import javafx.scene.control.Label;

import formation.Peg;
import gui.Layout.PanelNodeFX;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PegInfoDisplayFX extends PanelNodeFX {
    protected Peg currentPeg;
    protected Label playerName;
    protected Label shirtNumber;
    protected Label position;

    public PegInfoDisplayFX() {
        super();
        setBordered(true);
        initialiseInfo();
    }

    protected void initialiseInfo() {
        VBox contentPane = new VBox();
        playerName = new Label("Name: ");
        shirtNumber = new Label("Shirt Number: ");
        position = new Label("Position: ");
        contentPane.getChildren().add(playerName);
        contentPane.getChildren().add(shirtNumber);
        contentPane.getChildren().add(position);
        contentPane.setSpacing(6);
        contentPane.setPadding(new javafx.geometry.Insets(6));
        this.setContentNode(contentPane);
    }

    public void displayPegInfo(Peg peg) {
        this.currentPeg = peg;
        updateDisplay();
    }

    protected void updateDisplay() {
        if (currentPeg != null && currentPeg.getPlayer() != null) {
            playerName.setText("Name: " + (currentPeg.getPlayer().getName() != null ? currentPeg.getPlayer().getName() : "Unknown"));
            shirtNumber.setText("Shirt Number: " + (currentPeg.getPlayer().getShirtNumber() != 0 ? currentPeg.getPlayer().getShirtNumber() : "Unknown"));
            position.setText("Position: " + (currentPeg.getPosition() != null ? currentPeg.getPosition().toString() : "Unknown"));
        } else {
            playerName.setText("Name: ");
            shirtNumber.setText("Shirt Number: ");
            position.setText("Position: ");
        }
    }
    
}
