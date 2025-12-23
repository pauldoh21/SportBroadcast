package gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import formation.Formation;
import formation.Peg;
import gui.Layout.PanelNode;
import gui.Layout.SplitNode;

public class FormationEditorDetails extends PanelNode {
    Formation formation;
    JTextField formationInput;

    public FormationEditorDetails(Formation formation) {
        super();
        this.formation = formation;
        PegInfoPanel pegInfoPanel = new PegInfoPanel();

        PanelNode subsPanel = new PanelNode();
        subsPanel.setBorder(null);
        SubstitutesPanel substitutesPanel = new SubstitutesPanel(formation);
        subsPanel.setContent(substitutesPanel);

        PanelNode controlPanelParent = new PanelNode();
        controlPanelParent.setBorder(null);
        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(null);
        controlPanel.setLayout(new FlowLayout());
        formationInput = new JTextField(formation.getFormation());
        controlPanel.add(formationInput);
        JButton regenerateButton = new JButton("Regenerate Formation");
        //regenerateButton.addActionListener(e -> redrawFormation());
        controlPanel.add(regenerateButton);
        controlPanelParent.setContent(controlPanel, BorderLayout.SOUTH);

        SplitNode childLayout = new SplitNode(subsPanel, controlPanelParent, true, 0.7, true);

        SplitNode layout = new SplitNode(pegInfoPanel, childLayout, true, 0.1, true);

        this.setContent(layout);
    }
}
