package gui.Dragging;

import gui.DropTargetList;
import gui.PegButtonSelectableFX;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.scene.Node;

public class PegHoveredEvent extends Event {

    public static final EventType<PegHoveredEvent> PEG_HOVERED =
            new EventType<>(Event.ANY, "PEG_HOVERED");
    public static final EventType<PegHoveredEvent> PEG_UNHOVERED =
            new EventType<>(Event.ANY, "PEG_UNHOVERED");

    public PegButtonSelectableFX sourceButton;
    public Node hoveredNode;
    
    public PegHoveredEvent(EventType<? extends Event> eventType, PegButtonSelectableFX sourceButton, Node hoveredNode) {
        super(eventType);
        this.sourceButton = sourceButton;
        this.hoveredNode = hoveredNode;
        if (eventType == PEG_UNHOVERED) {
            this.hoveredNode = null;
            DropTargetList.unhighlightAll();
        }
    }

    public PegButtonSelectableFX getSourceButton() {
        return this.sourceButton;
    }

    public Node getHoveredNode() {
        return this.hoveredNode;
    }
    
}
