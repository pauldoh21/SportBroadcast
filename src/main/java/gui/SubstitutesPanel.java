package gui;
import javax.swing.*;

import player.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SubstitutesPanel extends JPanel {
    private ArrayList<Player> substitutes;
    private JList<Player> substitutesList;
    private JScrollPane scrollPane;

    public SubstitutesPanel(List<Player> substitutes) {
        this.substitutes = new ArrayList<>(substitutes);
        this.setLayout(new BorderLayout());
        //this.setPreferredSize(new Dimension(200, 0));

        JLabel label = new JLabel("Substitutes:");
        this.add(label, BorderLayout.NORTH);

        substitutesList = new JList<Player>(substitutes.toArray(new Player[substitutes.size()]));
        substitutesList.setCellRenderer(new PlayerCellRenderer());
        //updateSubstitutesList();

        scrollPane = new JScrollPane(substitutesList);
        scrollPane.setViewportView(substitutesList);
        substitutesList.setLayoutOrientation(JList.VERTICAL);
        this.add(scrollPane, BorderLayout.CENTER);
    }
    
}
