package gui.swing;

import javax.swing.*;

import java.awt.*;

import formation.Formation;
import gui.Layout.swing.PanelNode;
import gui.Layout.swing.SplitNode;

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
