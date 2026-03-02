package formation;

import player.Player;

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

    public void copyPeg(Peg peg) {
        this.player = peg.player;
        this.position = peg.position;
        this.xAdjustment = peg.xAdjustment;
        this.yAdjustment = peg.yAdjustment;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void swapPeg(Peg otherPeg) {
        Player temp = this.player;
        this.player = otherPeg.getPlayer();
        otherPeg.setPlayer(temp);
        System.out.println("Swapped pegs: this peg now has player " + (this.player != null ? this.player.getShirtNumber() : "null") +
                           ", other peg now has player " + (otherPeg.getPlayer() != null ? otherPeg.getPlayer().getShirtNumber() : "null"));
    }

    public double getxAdjustment() {
        return xAdjustment;
    }

    public void setxAdjustment(double xAdjustment) {
        this.xAdjustment = xAdjustment;
        System.out.println("Peg for player " + (player != null ? player.getShirtNumber() : "null") + " xAdjustment set to " + xAdjustment);
    }

    public double getyAdjustment() {
        return yAdjustment;
    }

    public void setyAdjustment(double yAdjustment) {
        this.yAdjustment = yAdjustment;
        System.out.println("Peg for player " + (player != null ? player.getShirtNumber() : "null") + " yAdjustment set to " + yAdjustment);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void reset() {
        setPlayer(null);
        setPosition(null);
        setxAdjustment(0);
        setyAdjustment(0);
    }
}
