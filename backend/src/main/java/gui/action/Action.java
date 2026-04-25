package gui.action;

import gui.symbols.GoalSymbol;
import gui.symbols.RedCardSymbol;
import gui.symbols.YellowCardSymbol;
import javafx.event.EventType;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.shape.SVGPath;
import player.Player;

public enum Action {
    GOAL("Goal", GoalEvent.GOAL, new GoalSymbol()) {
        @Override
        public PlayerEvent createEvent(Player player) {
            return new GoalEvent(player);
        }
    },
    YELLOW_CARD("Yellow card", CardEvent.YELLOW_CARD, new YellowCardSymbol()) {
        @Override
        public PlayerEvent createEvent(Player player) {
            return new CardEvent(CardEvent.YELLOW_CARD, player);
        }
    },
    RED_CARD("Red card", CardEvent.RED_CARD, new RedCardSymbol()) {
        @Override
        public PlayerEvent createEvent(Player player) {
            return new CardEvent(CardEvent.RED_CARD, player);
        }
    };

    private final String label;
    private final EventType<? extends PlayerEvent> eventType;
    private final SVGPath symbol;

    Action(String label, EventType<? extends PlayerEvent> eventType, SVGPath symbol) {
        this.label = label;
        this.eventType = eventType;
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return label;
    }

    /** ✅ Safe factory — ALWAYS returns PlayerEvent */
    public abstract PlayerEvent createEvent(Player player);

    public EventType<? extends PlayerEvent> getEventType() {
        return eventType;
    }

    public SVGPath toSymbol() {
        return symbol;
    }

    public StackPane toSymbolNode() {
        if (symbol == null) return new StackPane();

        StackPane wrapper = new StackPane();
        Group group = new Group(symbol);
        wrapper.getChildren().add(group);

        DoubleBinding scale = Bindings.createDoubleBinding(() -> {
            Bounds b = symbol.getBoundsInLocal();
            double w = b.getWidth() <= 0 ? 1 : b.getWidth();
            double h = b.getHeight() <= 0 ? 1 : b.getHeight();
            double sx = wrapper.getWidth() / w;
            double sy = wrapper.getHeight() / h;
            double s = Math.min(sx, sy);
            if (Double.isInfinite(s) || Double.isNaN(s) || s <= 0) return 1.0;
            return s;
        }, wrapper.widthProperty(), wrapper.heightProperty(), symbol.boundsInLocalProperty());

        group.scaleXProperty().bind(scale);
        group.scaleYProperty().bind(scale);

        return wrapper;
    }
}