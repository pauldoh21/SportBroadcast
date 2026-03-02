package gui.Dragging;

import gui.DropTargetList;
import javafx.event.Event;
import javafx.event.EventType;

public class PegLineSwapEvent extends Event {
    
    public static final EventType<PegLineSwapEvent> PEG_LINE_SWAP =
            new EventType<>(Event.ANY, "PEG_LINE_SWAP");

    private final gui.PegButtonSelectableFX sourceButton;
    private final gui.FormationLineFX targetLine;
    private int segmentIndex = 0;

    public PegLineSwapEvent(gui.PegButtonSelectableFX sourceButton, gui.FormationSegmentFX targetSegment) {
        super(PEG_LINE_SWAP);
        this.sourceButton = sourceButton;
        this.targetLine = targetSegment.getParentLine();
        this.segmentIndex = targetSegment.getSegmentIndex();
        DropTargetList.unhighlightAll();
    }

    public gui.PegButtonSelectableFX getSourceButton() {
        return sourceButton;
    }

    public gui.FormationLineFX getTargetLine() {
        return targetLine;
    }

    public int getSegmentIndex() {
        return segmentIndex;
    }

}
