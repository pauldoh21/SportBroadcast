package gui.Dragging;

import gui.DropTargetList;
import javafx.event.Event;
import javafx.event.EventType;

public class PegSwapEvent extends Event {
    
    public static final EventType<PegSwapEvent> PEG_SWAP =
            new EventType<>(Event.ANY, "PEG_SWAP");

    private final gui.PegButtonSelectableFX sourceButton;
    private final gui.PegButtonSelectableFX targetButton;

    public PegSwapEvent(gui.PegButtonSelectableFX sourceButton, gui.PegButtonSelectableFX targetButton) {
        super(PEG_SWAP);
        this.sourceButton = sourceButton;
        this.targetButton = targetButton;
        DropTargetList.unhighlightAll();
        swapPegs();
    }

    public gui.PegButtonSelectableFX getSourceButton() {
        return sourceButton;
    }

    public gui.PegButtonSelectableFX getTargetButton() {
        return targetButton;
    }

    private void swapPegs() {
        formation.Peg sourcePeg = sourceButton.getPeg();
        formation.Peg targetPeg = targetButton.getPeg();

        sourcePeg.swapPeg(targetPeg);
    }

}
