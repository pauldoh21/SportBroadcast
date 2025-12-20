public class App {
    public static void main(String[] args) {
        FootballTeam team1 = new FootballTeam("Team A");

        new FormationEditor(team1.getFormation());

        OBSController obsController = new OBSController("localhost", 4455, "");
    }
    
}
