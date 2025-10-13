import java.util.List;

public class Line {
    private List<Peg> pegs;
    private double yPosition; // Y position of the line on the field (0.0 to 1.0)

    public Line(double yPosition, int numberOfPegs) {
        this.yPosition = yPosition;
        this.pegs = new java.util.ArrayList<>();
        for (int i = 0; i < numberOfPegs; i++) {
            pegs.add(new Peg());
        }
    }

    public List<Peg> getPegs() {
        return pegs;
    }

    public double getyPosition() {
        return yPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    public void addPlayer(Player player, int pegIndex) {
        if (pegIndex >= 0 && pegIndex < pegs.size()) {
            pegs.get(pegIndex).setPlayer(player);
        }
    }
}
