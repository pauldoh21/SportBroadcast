package gui;

import javax.swing.*;
import gui.Layout.LayoutNode;
import gui.Layout.PanelNode;
import gui.Layout.SplitNode;

public class BaseWindow extends JFrame {
    private LayoutNode layout;

    public BaseWindow(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        setLocationRelativeTo(null); // Center the frame on the screen

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(new JMenu("File"));
        menuBar.add(new JMenu("Edit"));
        menuBar.add(new JMenu("View"));
        this.setJMenuBar(menuBar);

        //createTestLayout();
    }

    public LayoutNode getLayoutNode() {
        return layout;
    }

    public void setLayoutNode(LayoutNode layout) {
        this.layout = layout;
        this.setContentPane(layout);
        this.revalidate();
        this.repaint();
    }

    private void createTestLayout() {
        SplitNode layout = new SplitNode(
                new SplitNode(
                    new PanelNode(),
                    new PanelNode(),
                    true,
                    0.5
                ) {},
                new SplitNode(
                    new SplitNode(
                        new PanelNode() {},
                        new PanelNode(),
                        true,
                        0.9
                    ), 
                    new SplitNode(
                        new PanelNode(), 
                        new PanelNode(), 
                        true, 
                        0.5),
                    false, 0.8),
                false,
                0.2
        );

        this.layout = layout;
        if (layout != null) {
            this.setContentPane(layout);
        }
    }
}
