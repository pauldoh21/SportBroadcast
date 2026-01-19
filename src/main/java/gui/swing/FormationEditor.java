package gui.swing;
import com.formdev.flatlaf.*;

import formation.Formation;
import gui.Layout.swing.LayoutNode;
import gui.Layout.swing.PanelNode;
import gui.Layout.swing.SplitNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class FormationEditor extends LayoutNode {
    Formation formation;
    FormationPanel formationPanel;
    JTextField formationInput;
    PegInfoPanel pegInfoPanel;

    private int eastPanelWidth = 300;

    public FormationEditor(Formation formation) {
        this.formation = formation;
        initialize();
    }

    //TODO: Refactor to use relative sizing instead of pixel sizes
    void initialize() {
        //setTitle("Formation Editor");
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //setSize(800, 600);

        BorderLayout bLayout = new BorderLayout();
        setLayout(bLayout);

        formationPanel = new FormationPanel(formation);
        formationPanel.setMinimumSize(new Dimension(400, 0));

        // Create a container for the PegInfoPanel and controlPanel
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(eastPanelWidth, getHeight()));

        // Add PegInfoPanel to the top of the eastPanel
        pegInfoPanel = new PegInfoPanel();
        eastPanel.add(pegInfoPanel, BorderLayout.NORTH);

        formationPanel.setPegInfoPanel(pegInfoPanel);

        // Add SubstitutionsPanel to the center of the eastPanel
        /* SubstitutesPanel substitutionsPanel = new SubstitutesPanel(formation.getSubstitutes());
        eastPanel.add(substitutionsPanel, BorderLayout.CENTER); */

        // Add controlPanel to the bottom of the eastPanel
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        formationInput = new JTextField();
        formationInput.setText(formation.getFormation());
        controlPanel.add(formationInput);
        JButton regenerateButton = new JButton("Regenerate Formation");
        regenerateButton.addActionListener(e -> redrawFormation());
        controlPanel.add(regenerateButton);

        eastPanel.add(controlPanel, BorderLayout.SOUTH);

        /* JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formationPanel, eastPanel);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(getWidth() - eastPanelWidth);
        splitPane.setResizeWeight(1.0);
        add(splitPane, BorderLayout.CENTER); */

        //SplitNode layout = new SplitNode(new PanelNode(formationPanel), new PanelNode(eastPanel), false, 0.7, false);
        //add(layout, BorderLayout.CENTER);

        formationPanel.initialiseButtons();
    }

    public void redrawFormation() {
        if (formationInput.getText() != formation.getFormation()) {
            try {
                formation.setFormation(formationInput.getText());
                formation.generateFormation();
                formationPanel.initialiseButtons();
                formationPanel.redrawFormation();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Invalid formation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                formationInput.setText(formation.getFormation());
            }
        }
    }

}
