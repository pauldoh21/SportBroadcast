package gui.action;

import javafx.event.Event;
import javafx.event.EventType;
import player.Player;

public abstract class PlayerEvent extends Event {

    private final Player player;

    protected PlayerEvent(EventType<? extends PlayerEvent> eventType, Player player) {
        super(eventType);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}