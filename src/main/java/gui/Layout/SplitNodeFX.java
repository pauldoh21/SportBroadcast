package gui.Layout;

import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SplitNodeFX extends LayoutNodeFX {
    private ObservableList<LayoutNodeFX> items;
    private double[] positions;
    private boolean isVertical;
    private boolean isLocked;
    private SplitPane splitPane;

    public SplitNodeFX(ObservableList<LayoutNodeFX> items, boolean isVertical, ObservableList<Double> positions, boolean isLocked) {
        super();
        this.positions = new double[positions.size()];
        for (int i = 0; i < positions.size(); i++) {
            this.positions[i] = positions.get(i);
        }
        this.items = items;
        this.isVertical = isVertical;
        this.isLocked = isLocked;
        createLayout();
    }

    private void createLayout() {
        splitPane = new SplitPane();
        splitPane.setOrientation(isVertical ? javafx.geometry.Orientation.VERTICAL : javafx.geometry.Orientation.HORIZONTAL);
        splitPane.getItems().addAll(items);

        // Ensure divider positions are applied after the SplitPane has been laid out
        ChangeListener<Number> sizeListener = new ChangeListener<Number>() {
            private boolean applied = false;

            @Override
            public void changed(ObservableValue<? extends Number> obs, Number oldV, Number newV) {
                if (applied) return;
                if (newV == null) return;
                if (newV.doubleValue() > 0) {
                    Platform.runLater(() -> splitPane.setDividerPositions(positions));
                    applied = true;
                    // remove listeners below after applied
                    splitPane.widthProperty().removeListener(this);
                    splitPane.heightProperty().removeListener(this);
                }
            }
        };

        // For horizontal split, wait for width; for vertical wait for height — listen to both and apply when either becomes >0
        splitPane.widthProperty().addListener(sizeListener);
        splitPane.heightProperty().addListener(sizeListener);
        //splitPane.setDisable(isLocked);

        splitPane.setMinSize(0, 0);
        splitPane.prefWidthProperty().bind(this.widthProperty());
        splitPane.prefHeightProperty().bind(this.heightProperty());

        // Make child layout nodes able to grow
        for (LayoutNodeFX item : items) {
            item.setMinSize(0, 0);
            item.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        }

        this.getChildren().add(splitPane);

        System.out.println("Created SplitNodeFX: isVertical=" + isVertical + ", weight=" + positions + ", isLocked=" + isLocked);
    }

    public ObservableList<LayoutNodeFX> getItems() {
        return items;
    }

    public static void setResizableWithParent(javafx.scene.Node node, boolean resizable) {
        SplitPane.setResizableWithParent(node, resizable);
    }


}
