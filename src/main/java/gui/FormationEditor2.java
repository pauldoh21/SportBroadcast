package gui;

import javax.swing.*;

import java.awt.*;

import gui.Layout.PanelNode;
import gui.Layout.SplitNode;
import formation.Formation;

public class FormationEditor2 extends PanelNode {
    SplitNode layout;
    Formation formation;
    FormationDisplay formationDisplay;
    FormationEditorDetails formationEditorDetails;

    public FormationEditor2(Formation formation) {
        super();
        setBorder(null);
        this.formation = formation;
        this.formationDisplay = new FormationDisplay(formation);
        this.formationEditorDetails = new FormationEditorDetails(formation);
        this.layout = new SplitNode(formationDisplay, formationEditorDetails, false, 0.9, false);
        this.setContent(layout);
    }
}
