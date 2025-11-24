import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PegInfoPanel extends JPanel {
    private PegButton pegButton;
    private JLabel profilePicture;
    private JLabel nameLabel;
    private JLabel nameDisplayLabel;
    private JLabel positionLabel;
    private JComboBox<Position> positionComboBox;

    public PegInfoPanel() {
        this.setLayout(new BorderLayout());
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

        this.add(profilePicture, BorderLayout.WEST);
        this.add(detailsPanel, BorderLayout.CENTER);

        updateInfo();
    }

    public void setPegButton(PegButton pegButton) {
        if (pegButton == null) {
            positionComboBox.removeAllItems();
        }
        this.pegButton = pegButton;
        updateInfo();
    }

    public void setPositionOptions(List<Position> positions) {
        positionComboBox.removeAllItems();
        for (Position pos : positions) {
            positionComboBox.addItem(pos);
        }
    }

    private void updateInfo() {
        if (pegButton == null) {
            nameDisplayLabel.setText("");
            return;
        }
        Player player = (pegButton != null) ? pegButton.getPeg().getPlayer() : null;
        if (player != null) {
            nameDisplayLabel.setText(player.getName());
            nameDisplayLabel.setVisible(true);
            positionComboBox.setSelectedItem(pegButton.getPeg().getPosition());
        } else {
            nameDisplayLabel.setVisible(false);
        }
    }

    private void setPegPosition(Position position) {
        if (pegButton != null && position != null) {
            System.out.println("Setting peg position to: " + position);
            pegButton.getPeg().setPosition(position);
        }
    }

}
