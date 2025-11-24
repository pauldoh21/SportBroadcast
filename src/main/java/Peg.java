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
        this.position = null;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void swapPlayer(Peg otherPlayer) {
        Player temp = this.player;
        this.player = otherPlayer.getPlayer();
        otherPlayer.setPlayer(temp);
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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
