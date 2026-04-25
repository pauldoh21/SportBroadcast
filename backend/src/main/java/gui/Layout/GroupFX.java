package gui.Layout;

import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class GroupFX extends PanelNodeFX {
    private ObservableList<LayoutNodeFX> items;
    private double[] weights;
    private Pane box;
    private javafx.geometry.Orientation orientation;

    public GroupFX(ObservableList<LayoutNodeFX> items, double[] weights, javafx.geometry.Orientation orientation) {
        super();
        this.items = items;
        this.weights = weights;
        this.orientation = orientation;
        this.setBordered(false);
        createLayout();
    }

    private void createLayout() {
        if (orientation == javafx.geometry.Orientation.HORIZONTAL) {
            javafx.scene.layout.HBox hbox = new javafx.scene.layout.HBox();
            for (int i = 0; i < items.size(); i++) {
                LayoutNodeFX item = items.get(i);
                hbox.getChildren().add(item);
                if (i < weights.length) {
                    javafx.scene.layout.HBox.setHgrow(item, javafx.scene.layout.Priority.ALWAYS);
                    item.prefWidthProperty().bind(hbox.widthProperty().multiply(weights[i]));
                }
            }
            box = hbox;
        } else {
            javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox();
            for (int i = 0; i < items.size(); i++) {
                LayoutNodeFX item = items.get(i);
                vbox.getChildren().add(item);
                if (i < weights.length) {
                    javafx.scene.layout.VBox.setVgrow(item, javafx.scene.layout.Priority.ALWAYS);
                    item.prefHeightProperty().bind(vbox.heightProperty().multiply(weights[i]));
                }
            }
            box = vbox;
        }
        setContentNode(box);
    }
    
}
