package gui.action;

import javafx.event.EventType;
import player.Player;

public class GoalEvent extends PlayerEvent {

    public static final EventType<GoalEvent> GOAL =
            new EventType<>(ANY, "GOAL");

    public GoalEvent(Player player) {
        super(GOAL, player);
    }
}