package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import formation.Formation;
import formation.Line;
import formation.Peg;
import gui.Layout.PanelNode;

public class FormationDisplay extends PanelNode {
    Formation formation;
    private List<PegButton> formationButtons;
    private JPanel childPanel;

    public FormationDisplay(Formation formation) {
        super();
        this.formation = formation;

        this.childPanel = new JPanel();
        childPanel.setBackground(Color.GREEN.darker());
        childPanel.setLayout(null);
        this.setContent(childPanel);
        formationButtons = new ArrayList<>();
        initialiseButtons();

        // Add a listener to ensure proper sizing before showing the formation
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                showFormation();
            }
        });
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public void initialiseButtons() {
        formationButtons.clear();
        childPanel.removeAll();   // ✅ remove buttons only, not the panel

        for (Line line : formation.getLines()) {
            for (Peg peg : line.getPegs()) {
                PegButton pegButton = new PegButton(peg);
                formationButtons.add(pegButton);
                childPanel.add(pegButton);   // ✅ add to green panel
            }
        }

        childPanel.revalidate();
        childPanel.repaint();
        showFormation();
    }
    
    void showFormation() {
        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        if (panelWidth <= 0 || panelHeight <= 0) {
            return; // Avoid calculations if dimensions are invalid
        }

        int buttonSize = 50;

        for (int i = 0; i < formation.getLines().size(); i++) {
            Line line = formation.getLines().get(i);
            int y = (int) (panelHeight * (i + 1) / (formation.getLines().size() + 1) + (line.getYOffset()));

            for (int j = 0; j < line.getPegs().size(); j++) {
                Peg peg = line.getPegs().get(j);
                int x = (int) (panelWidth * (j + 1) / (line.getPegs().size() + 1));

                for (PegButton pb : formationButtons) {
                    if (pb.getPeg() == peg) {
                        pb.setBounds(
                            (int) ((x - buttonSize / 2) + (peg.getxAdjustment() * panelWidth)),
                            (int) ((y - buttonSize / 2) + (peg.getyAdjustment() * panelHeight)),
                            buttonSize,
                            buttonSize
                        );
                        int playerNumber = peg.getPlayer() != null ? formation.getTeam().getPlayerNumber(peg.getPlayer()) : 0;
                        pb.setText(playerNumber != 0 ? String.valueOf(playerNumber) : "Empty");
                        break;
                    }
                }
            }
        }

        repaint();
    }


}
