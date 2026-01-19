package app;

import com.formdev.flatlaf.FlatDarkLaf;

import gui.Layout.swing.PanelNode;
import gui.Layout.swing.SplitNode;
import gui.swing.BaseWindow;
import gui.swing.FormationDisplay;
import gui.swing.FormationEditor;
import gui.swing.FormationEditor2;
import obs.OBSController;
import team.FootballTeam;

public class App {
    public static void main(String[] args) {
        FootballTeam team1 = new FootballTeam("Team A");

        //OBSController obsController = new OBSController("localhost", 4455, "");

        FlatDarkLaf.setup();

        BaseWindow window = new BaseWindow("Sport Broadcast");

        FormationEditor2 editor = new FormationEditor2(team1.getFormation());

        SplitNode layout = new SplitNode(new SplitNode(new PanelNode(), editor, false, 0.1, false), new PanelNode(), true, 0.9, true);

        window.setLayoutNode(layout);

        window.setVisible(true);
    }
    
}
