package formation;
import java.util.List;

import player.Player;

public class Line {
    private List<Peg> pegs;
    private double yOffset; // Y position of the line on the field (0.0 to 1.0)
    private List<Position> availablePositions;

    public Line(double numberOfPegs) {
        this.yOffset = 0;
        this.pegs = new java.util.ArrayList<>();
        this.availablePositions = new java.util.ArrayList<>();
        setAvailablePositions(null);
        for (int i = 0; i < numberOfPegs; i++) {
            pegs.add(new Peg());
        }
    }

    public void setAvailablePositions(List<Position> positions) {
        if (positions == null) {
            for (Position pos : Position.values()) {
                availablePositions.add(pos);
            }
        } else {
            this.availablePositions = positions;
        }
    }

    public List<Position> getAvailablePositions() {
        return availablePositions;
    }

    public List<Peg> getPegs() {
        return pegs;
    }

    public double getYOffset() {
        return yOffset;
    }

    public void setYOffset(double yPosition) {
        this.yOffset = yPosition;
    }

    public void addPlayer(Player player, int pegIndex) {
        if (pegIndex >= 0 && pegIndex < pegs.size()) {
            pegs.get(pegIndex).setPlayer(player);
        }
    }

    public void removePeg(Peg peg) {
        pegs.remove(peg);
    }

    public void addPegAtPosition(Peg peg, int position) {
        pegs.add(position, peg);
    }
}
