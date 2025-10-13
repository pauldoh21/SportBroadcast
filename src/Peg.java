public class Peg {
    private Player player;
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
