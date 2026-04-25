package gui;

import formation.Peg;
import javafx.event.Event;
import javafx.event.EventType;

public class PegSelectedEvent extends Event {
    
    public static final EventType<PegSelectedEvent> PEG_SELECTED =
            new EventType<>(Event.ANY, "PEG_SELECTED");

    private final Peg selectedPeg;
    private final PegButtonGroupManagerFX sourceManager;

    public PegSelectedEvent(Peg selectedPeg, PegButtonGroupManagerFX sourceManager) {
        super(PEG_SELECTED);
        this.selectedPeg = selectedPeg;
        this.sourceManager = sourceManager;
    }

    public Peg getSelectedPeg() {
        return selectedPeg;
    }

    public PegButtonGroupManagerFX getSourceManager() {
        return sourceManager;
    }
    
}
