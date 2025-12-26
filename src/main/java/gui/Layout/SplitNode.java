package gui.Layout;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.HierarchyEvent;

public class SplitNode extends LayoutNode {
    LayoutNode first;
    LayoutNode second;
    boolean isVertical;
    boolean isLocked = false;
    JSplitPane splitPane;

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
        splitPane = new JSplitPane(
                isVertical ? JSplitPane.VERTICAL_SPLIT : JSplitPane.HORIZONTAL_SPLIT,
                first,
                second
        );
        splitPane.setEnabled(!isLocked);
        splitPane.setDividerSize(isLocked ? 0 : 5);
        this.setLayout(new java.awt.BorderLayout());
        this.add(splitPane, java.awt.BorderLayout.CENTER);
        splitPane.setResizeWeight(weight);

        SwingUtilities.invokeLater(() ->
            SwingUtilities.invokeLater(() -> {
                int size = isVertical ? splitPane.getHeight() : splitPane.getWidth();
                if (size > 0) {
                    splitPane.setDividerLocation((int) (size * weight));
                }
            })
        );



        System.out.println("Created SplitNode: isVertical=" + isVertical + ", weight=" + weight + ", isLocked=" + isLocked);

    }
}
