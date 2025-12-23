package gui;
import java.awt.*;
import java.util.List;
import javax.swing.*;

import formation.Peg;
import formation.Position;
import gui.Layout.PanelNode;
import player.Player;

public class PegInfoPanel extends PanelNode {
    private PegButtonSelectable pegButton;
    private Peg peg;
    private JLabel profilePicture;
    private JLabel nameLabel;
    private JLabel nameDisplayLabel;
    private JLabel positionLabel;
    private JComboBox<Position> positionComboBox;

    public PegInfoPanel() {
        super();
        setBorder(null);

        PanelNode contentPanel = new PanelNode();
        contentPanel.setBorder(null);

        profilePicture = new JLabel(new ImageIcon("noName.png"));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        nameLabel = new JLabel("Name: ");
        positionLabel = new JLabel("Position: ");
        nameDisplayLabel = new JLabel();
        positionComboBox = new JComboBox<>();
        positionComboBox.setPrototypeDisplayValue(Position.CENTRAL_DEFENSIVE_MIDFIELDER);
        positionComboBox.addActionListener(e -> {
            Position selectedPosition = (Position) positionComboBox.getSelectedItem();
            setPegPosition(selectedPosition);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        detailsPanel.add(nameLabel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        detailsPanel.add(positionLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        detailsPanel.add(nameDisplayLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        detailsPanel.add(positionComboBox, gbc);

        contentPanel.add(profilePicture, BorderLayout.WEST);
        contentPanel.add(detailsPanel, BorderLayout.CENTER);

        this.setContent(contentPanel, BorderLayout.NORTH);

        updateInfo();
    }

    public void setPegButton(PegButtonSelectable pegButton) {
        if (pegButton == null) {
            positionComboBox.removeAllItems();
        }
        this.pegButton = pegButton;
        updateInfo();
    }

    public void setPeg(Peg peg) {
        if (peg == null) {
            positionComboBox.removeAllItems();
        }
        this.peg = peg;
        updateInfo();
    }

    public void setPositionOptions(List<Position> positions) {
        positionComboBox.removeAllItems();
        for (Position pos : positions) {
            positionComboBox.addItem(pos);
        }
    }

    private void updateInfo() {
        if (peg == null) {
            nameDisplayLabel.setText("");
            return;
        }
        Player player = (peg != null) ? peg.getPlayer() : null;
        if (player != null) {
            nameDisplayLabel.setText(player.getName());
            nameDisplayLabel.setVisible(true);
            positionComboBox.setSelectedItem(peg.getPosition());
        } else {
            nameDisplayLabel.setVisible(false);
        }
    }

    private void setPegPosition(Position position) {
        if (peg != null && position != null) {
            System.out.println("Setting peg position to: " + position);
            peg.setPosition(position);
        }
    }

}
