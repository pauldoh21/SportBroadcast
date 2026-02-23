package gui;

import java.util.List;

import formation.Formation;
import gui.Layout.PanelNodeFX;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class FormationTemplateRibbonFX extends PanelNodeFX {
    protected Formation formation;
    protected ComboBox<Formation> templateSelector;
    protected TextField formationNameField;
    protected Button saveButton;
    
    public FormationTemplateRibbonFX(Formation formation) {
        super();
        this.formation = formation;
        initialiseComponents(formation);
        layoutComponents();
    }

    protected void initialiseComponents(Formation formation) {
        templateSelector = new ComboBox();

        templateSelector.getItems().addAll(initialiseDefaultTemplates());
        templateSelector.setValue(formation);
        templateSelector.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                this.fireEvent(new FormationSwapEvent(this.formation, newVal));
                this.formation = newVal;
                refreshComponents();
            }
        });

        formationNameField = new TextField();
        formationNameField.setPromptText("Formation Name");
        formationNameField.setText(formation.getName());

        saveButton = new Button("Save Template");
        // Add action handler for saveButton if needed

        this.setContentNode(new HBox(10, templateSelector, formationNameField, saveButton));
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
        refreshComponents();
    }

    protected void layoutComponents() {
        // Bind the height of the components to the height of the ribbon
        templateSelector.prefHeightProperty().bind(this.heightProperty().multiply(0.8));
        formationNameField.prefHeightProperty().bind(this.heightProperty().multiply(0.8));
        saveButton.prefHeightProperty().bind(this.heightProperty().multiply(0.8));

        templateSelector.setMinHeight(1);
        formationNameField.setMinHeight(1);
        saveButton.setMinHeight(1);

        // Center align the components vertically
        ((HBox)this.getContentNode()).setAlignment(Pos.CENTER);
    }

    protected void refreshComponents() {
        templateSelector.setValue(formation);
        formationNameField.setText(formation.getName());
    }

    protected List<Formation> initialiseDefaultTemplates() {
        // Create a list of default formation templates
        List<Formation> defaultTemplates = List.of(
            new Formation("4-4-2", true),
            new Formation("4-3-3", true),
            new Formation("3-5-2", true),
            new Formation("5-3-2", true),
            new Formation("3-4-3", true),
            new Formation("4-2-3-1", true),
            new Formation("3-2-3-1-1", true)
        );
        return defaultTemplates;
    }

}
