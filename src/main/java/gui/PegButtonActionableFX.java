package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import formation.Peg;
import gui.action.Action;

public class PegButtonActionableFX extends PegButtonSelectableFX {

    protected List<Action> availableActions;

    public PegButtonActionableFX(Peg peg) {
        super(peg);
        availableActions = new ArrayList<>(Arrays.asList(
            Action.GOAL, 
            Action.YELLOW_CARD, 
            Action.RED_CARD
        ));
    }

    public void setAvailableActions(List<Action> actions) {
        availableActions.clear();
        availableActions.addAll(actions);
    }

    public List<Action> getAvailableActions() {
        return availableActions;
    }
    
}
