enum Position {
    GOALKEEPER,
    CENTRE_BACK,
    FULL_BACK,
    WING_BACK,
    CENTRAL_DEFENSIVE_MIDFIELDER,
    CENTRAL_MIDFIELDER,
    ATTACKING_MIDFIELDER,
    WINGER,
    SECOND_STRIKER,
    STRIKER
}

public class Peg {
    private Player player;
    private Position position;
    // Percentage peg moved on x axis
    private double xAdjustment;
    // Percentage peg moved on y axis
    private double yAdjustment;

    public Peg() {
        this.xAdjustment = 0;
        this.yAdjustment = 0;
    }

    public Peg(Player player) {
        this.player = player;
        this.xAdjustment = 0;
        this.yAdjustment = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public double getxAdjustment() {
        return xAdjustment;
    }

    public void setxAdjustment(double xAdjustment) {
        this.xAdjustment = xAdjustment;
    }

    public double getyAdjustment() {
        return yAdjustment;
    }

    public void setyAdjustment(double yAdjustment) {
        this.yAdjustment = yAdjustment;
    }
}
