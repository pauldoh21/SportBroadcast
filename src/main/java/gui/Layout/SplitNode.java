package gui.Layout;

import javax.swing.*;

public class SplitNode extends LayoutNode {
    LayoutNode first;
    LayoutNode second;
    boolean isVertical;

    public SplitNode(LayoutNode first, LayoutNode second, boolean isVertical, double weight) {
        super();
        this.first = first;
        this.second = second;
        this.isVertical = isVertical;
        this.weight = weight;
        createLayout();
    }

    public void createLayout() {
        JSplitPane splitPane = new JSplitPane(
                isVertical ? JSplitPane.VERTICAL_SPLIT : JSplitPane.HORIZONTAL_SPLIT,
                first,
                second
        );
        splitPane.setResizeWeight(weight);
        this.setLayout(new java.awt.BorderLayout());
        this.add(splitPane, java.awt.BorderLayout.CENTER);
    }
}
