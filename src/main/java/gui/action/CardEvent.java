package gui.action;

import javafx.event.EventType;
import player.Player;

public class CardEvent extends PlayerEvent {

    public static final EventType<CardEvent> YELLOW_CARD =
            new EventType<>(ANY, "YELLOW_CARD");

    public static final EventType<CardEvent> RED_CARD =
            new EventType<>(ANY, "RED_CARD");

    public CardEvent(EventType<? extends CardEvent> type, Player player) {
        super(type, player);
    }
}