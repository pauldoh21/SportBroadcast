package gui;
import com.formdev.flatlaf.*;

import formation.Formation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;


public class FormationEditor {
    JFrame frame;
    Formation formation;
    FormationPanel formationPanel;
    JTextField formationInput;
    PegInfoPanel pegInfoPanel;

    private int eastPanelWidth = 300;

    public FormationEditor(Formation formation) {
        this.formation = formation;
        FlatDarkLaf.setup();
        show();
    }

    //TODO: Refactor to use relative sizing instead of pixel sizes
    void show() {
        JFrame frame = new JFrame("Formation Editor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);

        formationPanel = new FormationPanel(formation);
        formationPanel.setMinimumSize(new Dimension(400, 0));

        // Create a container for the PegInfoPanel and controlPanel
        JPanel eastPanel = new JPanel();
        eastPanel.setLayout(new BorderLayout());
        eastPanel.setPreferredSize(new Dimension(eastPanelWidth, frame.getHeight()));

        // Add PegInfoPanel to the top of the eastPanel
        pegInfoPanel = new PegInfoPanel();
        eastPanel.add(pegInfoPanel, BorderLayout.NORTH);

        formationPanel.setPegInfoPanel(pegInfoPanel);

        // Add SubstitutionsPanel to the center of the eastPanel
        SubstitutesPanel substitutionsPanel = new SubstitutesPanel(formation.getSubstitutes());
        eastPanel.add(substitutionsPanel, BorderLayout.CENTER);

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


        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formationPanel, eastPanel);
        splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(frame.getWidth() - eastPanelWidth);
        splitPane.setResizeWeight(1.0);
        frame.add(splitPane, BorderLayout.CENTER);

        frame.setVisible(true);

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
                JOptionPane.showMessageDialog(frame, "Invalid formation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                formationInput.setText(formation.getFormation());
            }
        }
    }

}
