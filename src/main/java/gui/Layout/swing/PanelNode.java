package gui.Layout.swing;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PanelNode extends LayoutNode {


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

    public void setContent(JPanel panel, String position) {
        this.removeAll();
        this.add(panel, position);
        this.revalidate();
        this.repaint();
    }
}
