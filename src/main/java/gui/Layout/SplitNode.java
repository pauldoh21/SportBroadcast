package gui.Layout;

import javax.swing.*;

public class SplitNode extends LayoutNode {
    LayoutNode first;
    LayoutNode second;
    boolean isVertical;
    boolean isLocked = false;

    public SplitNode(LayoutNode first, LayoutNode second, boolean isVertical, double weight, boolean isLocked) {
        super();
        this.first = first;
        this.second = second;
        this.isVertical = isVertical;
        this.weight = weight;
        this.isLocked = isLocked;
        createLayout();
    }

    public void createLayout() {
        JSplitPane splitPane = new JSplitPane(
                isVertical ? JSplitPane.VERTICAL_SPLIT : JSplitPane.HORIZONTAL_SPLIT,
                first,
                second
        );
        splitPane.setResizeWeight(weight);
        splitPane.setEnabled(!isLocked);
        this.setLayout(new java.awt.BorderLayout());
        this.add(splitPane, java.awt.BorderLayout.CENTER);
    }
}
