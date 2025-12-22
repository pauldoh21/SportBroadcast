package gui;

import javax.swing.*;
import gui.Layout.LayoutNode;
import gui.Layout.PanelNode;
import gui.Layout.SplitNode;
import java.awt.*;

public class BaseWindow extends JFrame {
    private LayoutNode layout;

    public BaseWindow(String title) {
        super(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        setLocationRelativeTo(null); // Center the frame on the screen

        JMenuBar menuBar = new JMenuBar();

        JLabel iconLabel = new JLabel();
        ImageIcon originalIcon = new ImageIcon("ico192.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        iconLabel.setIcon(new ImageIcon(scaledImage));
        menuBar.add(iconLabel);

        // Replace the JLabel spacer with Box.createHorizontalStrut for reliable spacing
        menuBar.add(Box.createHorizontalStrut(6)); // Add 8px horizontal space

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
                    0.5,
                    false
                ) {},
                new SplitNode(
                    new SplitNode(
                        new PanelNode() {},
                        new PanelNode(),
                        true,
                        0.9,
                        true
                    ), 
                    new SplitNode(
                        new PanelNode(), 
                        new PanelNode(), 
                        true, 
                        0.5,
                        false),
                    false,
                    0.8,
                    false),
                false,
                0.2,
                false
        );

        this.layout = layout;
        if (layout != null) {
            this.setContentPane(layout);
        }
    }
}
