package gui.swing;
import javax.swing.*;
import javax.swing.border.Border;

import player.Player;

import java.awt.*;

public class PlayerCellRenderer extends JLabel implements ListCellRenderer {

    public PlayerCellRenderer() {
        setOpaque(true);
        setHorizontalAlignment(LEFT);
        setVerticalAlignment(CENTER);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setFont(getFont().deriveFont(Font.BOLD, 14f));
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value instanceof Player player) {
            int num = player.getShirtNumber();
            setText(num + ": " + player.getPreferredName());
        } else {
            setText("Unknown Player");
        }

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
