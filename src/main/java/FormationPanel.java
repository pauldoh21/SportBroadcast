import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FormationPanel extends JPanel {
    private Formation formation;
    private List<PegButton> formationButtons;
    private int selectedX, selectedY;
    private List<LineButton> lineButtons;
    private PegInfoPanel pegInfoPanel;

    public FormationPanel(Formation formation) {
        this.formation = formation;
        formationButtons = new ArrayList<>();
        lineButtons = new ArrayList<>();
        //pegInfoPanel = new PegInfoPanel();
        //this.add(pegInfoPanel);
        this.setBackground(Color.GREEN.darker());
        this.setLayout(null);
        
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                redrawFormation();
            }
        });
        
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                selectPeg(null);
            }
        });
    }

    public void setPegInfoPanel(PegInfoPanel pegInfoPanel) {
        this.pegInfoPanel = pegInfoPanel;
    }

    public List<PegButton> getFormationButtons() {
        return formationButtons;
    }

    public void selectPeg(PegButton pegButton) {
        if (pegButton == null) {
            pegInfoPanel.setPegButton(null);
        }
        for (PegButton button : formationButtons) {
            if (button instanceof PegButton pb) {
                if (pegButton == null) {
                    pb.deselect();
                } else if (pb == pegButton) {
                    pb.select();
                    selectedPegLineOptions(pb);
                    this.setComponentZOrder(pegButton, 0);
                    if (pegInfoPanel != null) {
                        pegInfoPanel.setPegButton(pegButton);
                    }
                } else {
                    pb.deselect();
                }
            }
        }
    }

    private void selectedPegLineOptions(PegButton pegButton) {
        if (pegButton != null) {
            for (int i = 0; i < formation.getLines().size(); i++) {
                Line line = formation.getLines().get(i);
                if (line.getPegs().contains(pegButton.getPeg())) {
                    pegInfoPanel.setPositionOptions(line.getAvailablePositions());
                    break;
                }
            }
        }
    }

    public void initialiseButtons() {
        formationButtons.clear();
        lineButtons.clear();
        this.removeAll();
        //this.add(pegInfoPanel);
        for (int i = 0; i < formation.getLines().size(); i++) {
            Line line = formation.getLines().get(i);
            LineButton lineButton = new LineButton(line);
            lineButtons.add(lineButton);
            add(lineButton);
            for (int j = 0; j < line.getPegs().size(); j++) {
                Peg peg = line.getPegs().get(j);
                PegButton pegButton = new PegButton(peg);
                formationButtons.add(pegButton);
                add(pegButton);
            }
        }
        showFormation();
    }

    void showFormation() {

        int panelWidth = this.getWidth();
        int panelHeight = this.getHeight();
        int buttonSize = 50;

        for (int i = 0; i < formation.getLines().size(); i++) {
            Line line = formation.getLines().get(i);
            int y = (int) (panelHeight * (i + 1) / (formation.getLines().size() + 1) + (line.getYOffset()));

            for (LineButton lb : lineButtons) {
                if (lb.getLine() == line) {
                    lb.setInitialY((double)y/panelHeight);
                    lb.setBounds(10, y - 15, 25, 25);
                    break;
                }
            }

            for (int j = 0; j < line.getPegs().size(); j++) {
                Peg peg = line.getPegs().get(j);
                int x = (int) (panelWidth * (j + 1) / (line.getPegs().size() + 1));

                for (PegButton pb : formationButtons) {
                    if (pb.getPeg() == peg && !pb.isDragging()) {
                        pb.setBounds((int) ((x - buttonSize / 2) + (peg.getxAdjustment() * panelWidth)), (int) ((y - buttonSize / 2) + (peg.getyAdjustment() * panelWidth)), buttonSize, buttonSize);
                        int playerNumber = peg.getPlayer() != null ? formation.getTeam().getPlayerNumber(peg.getPlayer()) : 0;
                        pb.setText(playerNumber != 0 ? String.valueOf(playerNumber) : "Empty");
                        if (pb.isPegSelected()) {
                            selectedX = pb.getX();
                            selectedY = pb.getY();
                            pb.moveArrows();
                        }

                        break;
                    }
                }
                
            }
        }

        repaint();
    }

    public int[] getSelectedCoordinates() {
        int[] coords = new int[2];
        coords[0] = selectedX;
        coords[1] = selectedY;
        return coords;
    }

    public PegButton getSelectedPegButton() {
        for (PegButton pb : formationButtons) {
            if (pb.isPegSelected()) {
                return pb;
            }
        }
        return null;
    }

    public void redrawFormation() {
        showFormation();
    }
    
}
