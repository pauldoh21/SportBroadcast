package gui.Layout;

import javafx.scene.Node;

public class PanelNodeFX extends LayoutNodeFX {
    public PanelNodeFX() {
        super();
        this.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
    }

    public void setContentNode(javafx.scene.Node node) {
        this.getChildren().clear();
        javafx.scene.layout.AnchorPane holder = new javafx.scene.layout.AnchorPane();
        holder.getChildren().add(node);
        javafx.scene.layout.AnchorPane.setTopAnchor(node, 0.0);
        javafx.scene.layout.AnchorPane.setBottomAnchor(node, 0.0);
        javafx.scene.layout.AnchorPane.setLeftAnchor(node, 0.0);
        javafx.scene.layout.AnchorPane.setRightAnchor(node, 0.0);
        holder.prefWidthProperty().bind(this.widthProperty());
        holder.prefHeightProperty().bind(this.heightProperty());
        this.getChildren().add(holder);
    }

    public Node getContentNode() {
        if (this.getChildren().isEmpty()) {
            return null;
        }
        javafx.scene.layout.AnchorPane holder = (javafx.scene.layout.AnchorPane)this.getChildren().get(0);
        if (holder.getChildren().isEmpty()) {
            return null;
        }
        return holder.getChildren().get(0);
    }

    public void setBordered(boolean bordered) {
        if (bordered) {
            this.setStyle("-fx-border-color: gray; -fx-border-width: 1;");
        } else {
            this.setStyle("-fx-border-color: transparent;");
        }
    }
}
