public class App {
    public static void main(String[] args) {
        new GUI();
        FootballTeam team1 = new FootballTeam("Team A");

        team1.getFormation().setFormation("4-3-3");
        team1.getFormation().generateFormation();
        team1.getFormation().makeSub(6, 14);
        team1.getFormation().printFormation();
    }
    
}
