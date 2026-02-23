package obs.broadcaster;

import obs.OBSController;

// TODO: This isn't very good at all right now, very specific

public class FootballBroadcaster {
    protected OBSController obs;
    
    public FootballBroadcaster(String host, int port, String password) {
        this.obs = new OBSController(host, port, password);
        setScore(0, 0);
    }

    public void setScore(int score1, int score2) {
        obs.setSourceText("Team1_Score", String.valueOf(score1));
        obs.setSourceText("Team2_Score", String.valueOf(score2));
    }

}
