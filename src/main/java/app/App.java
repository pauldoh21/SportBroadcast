package app;

import com.formdev.flatlaf.FlatDarkLaf;

import gui.BaseWindow;
import gui.FormationEditor;
import obs.OBSController;
import team.FootballTeam;

public class App {
    public static void main(String[] args) {
        FootballTeam team1 = new FootballTeam("Team A");

        //OBSController obsController = new OBSController("localhost", 4455, "");

        FlatDarkLaf.setup();

        BaseWindow window = new BaseWindow("Sport Broadcast");

        //window.add(new FormationEditor(team1.getFormation()));

        window.setVisible(true);
    }
    
}
