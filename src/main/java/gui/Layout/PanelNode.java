package gui.Layout;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelNode extends LayoutNode {
    public PanelNode(JPanel panel) {
        super();
        this.setLayout(new java.awt.BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY.darker()));
        this.add(panel, BorderLayout.CENTER);
    }

    public PanelNode() {
        super();
        this.setLayout(new java.awt.BorderLayout());
        this.setBorder(BorderFactory.createLineBorder(java.awt.Color.GRAY.darker()));
    }

    public void setContent(JPanel panel) {
        this.removeAll();
        this.add(panel, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }
}
