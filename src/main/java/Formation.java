import java.util.ArrayList;

public class Formation {
    private Team team;
    private ArrayList<Line> lines;
    private String formationNotation;
    boolean includesGoalkeeper = true;
    int numberOfPlayers;
    private ArrayList<Player> substitutes;

    Formation(MultiPlayerTeam team) {
        this.numberOfPlayers = team.getSideSize();
        this.team = team;
    }

    public void generateFormation() {
        if (formationNotation == null || formationNotation.isEmpty()) {
            throw new IllegalStateException("Formation notation is not set");
        }
        if (team.getPlayers().size() < numberOfPlayers) {
            throw new IllegalStateException("Not enough players in the team to generate formation");
        }
        lines = new ArrayList<>();
        String[] segments = formationNotation.split("-");
        int totalLines = segments.length + (includesGoalkeeper ? 1 : 0);
        double lineSpacing = 1.0 / (totalLines + 1);
        int playerIndex = 0;

        if (includesGoalkeeper) {
            Line goalkeeperLine = new Line(1);
            goalkeeperLine.addPlayer(team.getPlayers().get(playerIndex + 1), 0);
            lines.add(goalkeeperLine);
            playerIndex++;
        }

        for (int i = 0; i < segments.length; i++) {
            int playersInLine = Integer.parseInt(segments[i]);
            Line line = new Line(playersInLine);
            for (int j = 0; j < playersInLine; j++) {
                if (playerIndex < team.getPlayers().size()) {
                    line.addPlayer(team.getPlayers().get(playerIndex + 1), j);
                    playerIndex++;
                }
            }
            lines.add(line);
        }

        substitutes = new ArrayList<>();
        for (int i = numberOfPlayers + 1; i <= team.getPlayers().size(); i++) {
            substitutes.add(team.getPlayers().get(i));
        }
    }

    public void setFormation(String formationNotation) {
        if (!isValidFormation(formationNotation)) {
            throw new IllegalArgumentException("Invalid formation notation for " + numberOfPlayers + " players: " + formationNotation);
        } else {
            this.formationNotation = formationNotation;
        }
    }

    public String getFormation() {
        return formationNotation;
    }

    public ArrayList<Line> getLines() {
        return lines;
    }

    public Team getTeam() {
        return team;
    }

    public ArrayList<Player> getSubstitutes() {
        return substitutes;
    }

    public void printFormation() {
        System.out.println("Formation: " + formationNotation);
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            if (includesGoalkeeper && i == 0) {
                System.out.print("Goalkeeper: ");
                for (Peg peg : line.getPegs()) {
                    if (peg.getPlayer() != null) {
                        int playerNumber = team.getPlayerNumber(peg.getPlayer());
                        System.out.print("[" + playerNumber + "] ");
                    }
                }
                System.out.println();
            } else {
                System.out.print("Line " + i + ": ");
                for (Peg peg : line.getPegs()) {
                    if (peg.getPlayer() != null) {
                        int playerNumber = team.getPlayerNumber(peg.getPlayer());
                        System.out.print("[" + playerNumber + "] ");
                    }
                }
                System.out.println();
            }
        }
        System.out.print("Substitutes: ");
        for (Player player : substitutes) {
            System.out.print(player.getName() + ", ");
        }
        System.out.println();
    }

    public void includesGoalkeeper(boolean includesGoalkeeper) {
        this.includesGoalkeeper = includesGoalkeeper;
    }

    public void makeSub(int outNumber, int inNumber) {
        Player playerOut = team.getPlayers().get(outNumber);
        Player playerIn = team.getPlayers().get(inNumber);
        makeSub(playerOut, playerIn);
    }

    public void makeSub(Player playerOut, Player playerIn) {
        if (!substitutes.contains(playerIn)) {
            throw new IllegalArgumentException("Player " + playerIn.getPreferredName() + " is not a substitute");
        }
        if (substitutes.contains(playerOut)) {
            throw new IllegalArgumentException("Player " + playerOut.getPreferredName() + " is not on the field");
        }

        for (Line line : lines) {
            for (Peg peg : line.getPegs()) {
                if (peg.getPlayer() != null && peg.getPlayer().equals(playerOut)) {
                    peg.setPlayer(playerIn);
                    substitutes.remove(playerIn);
                    substitutes.add(playerOut);
                    System.out.println("Substitution made: " + playerOut.getPreferredName() + " out, " + playerIn.getPreferredName() + " in");
                    return;
                }
            }
        }
        throw new IllegalArgumentException("Player " + playerOut.getPreferredName() + " is not on the field");
    }

    private boolean isValidFormation(String formationNotation) {
        String[] segments = formationNotation.split("-");
        int totalPlayers = 0;
        for (String segment : segments) {
            try {
                int playersInSegment = Integer.parseInt(segment);
                totalPlayers += playersInSegment;
            } catch (NumberFormatException e) {
                return false; // Invalid segment
            }
        }
        return totalPlayers == numberOfPlayers - (includesGoalkeeper ? 1 : 0);
    }
    
}
