package app;

import com.formdev.flatlaf.FlatDarkLaf;

import gui.BaseWindow;
import gui.FormationDisplay;
import gui.FormationEditor;
import gui.FormationEditor2;
import gui.Layout.PanelNode;
import gui.Layout.SplitNode;
import obs.OBSController;
import team.FootballTeam;

public class App {
    public static void main(String[] args) {
        FootballTeam team1 = new FootballTeam("Team A");

        //OBSController obsController = new OBSController("localhost", 4455, "");

        FlatDarkLaf.setup();

        BaseWindow window = new BaseWindow("Sport Broadcast");

        FormationEditor2 editor = new FormationEditor2(team1.getFormation());

        SplitNode layout = new SplitNode(new SplitNode(new PanelNode(), editor, false, 0.1, false), new PanelNode(), true, 0.95, false);

        window.setLayoutNode(layout);

        window.setVisible(true);
    }
    
}
