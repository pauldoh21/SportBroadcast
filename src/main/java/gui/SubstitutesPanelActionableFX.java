package gui;

import formation.Formation;
import formation.Peg;

public class SubstitutesPanelActionableFX extends SubstitutesPanelFX {

    public SubstitutesPanelActionableFX(Formation formation, PegButtonGroupManagerFX buttonGroupManager) {
        super(formation, buttonGroupManager);
    }

    @Override
    protected PegButtonSelectableFX initialiseButton(Peg peg) {
        return new PegButtonActionableFX(peg);
    }
    
}
