package gui;

import formation.Formation;
import gui.Layout.LayoutNode;

public class FormationDisplay extends LayoutNode {
    Formation formation;

    public FormationDisplay(Formation formation) {
        super();
        this.formation = formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    private void redrawFormation() {
        
    }
    
    
}
