package gui;

import javafx.event.Event;
import javafx.event.EventType;

public class FormationSwapEvent extends Event {
    public static final EventType<FormationSwapEvent> FORMATION_SWAP =
            new EventType<>(Event.ANY, "FORMATION_SWAP");

    private final formation.Formation sourceFormation;
    private final formation.Formation targetFormation;

    public FormationSwapEvent(formation.Formation sourceFormation, formation.Formation targetFormation) {
        super(FORMATION_SWAP);
        this.sourceFormation = sourceFormation;
        this.targetFormation = targetFormation;
        resetSourceFormation();
    }

    public formation.Formation getSourceFormation() {
        return sourceFormation;
    }

    public formation.Formation getNewFormation() {
        return targetFormation;
    }

    private void resetSourceFormation() {
        sourceFormation.reset();
    }
}
