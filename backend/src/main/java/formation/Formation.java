package formation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import player.Player;
import team.FootballTeam;
import team.MultiPlayerTeam;
import team.Team;

public class Formation {
    private String name;
    private Team team;
    private ArrayList<Line> lines;
    private String formationNotation;
    boolean includesGoalkeeper = true;
    int numberOfPlayers;
    private ArrayList<Player> substitutes;
    private Line subsLine;
    private int maxSubstitutes = 7;

    public Formation(MultiPlayerTeam team) {
        this.numberOfPlayers = team.getSideSize();
        this.team = team;
    }

    public Formation(String formationNotation) {
        this.formationNotation = formationNotation;
        List<Integer> playersPerLine = new ArrayList<>();
        String[] segments = formationNotation.split("-");
        this.numberOfPlayers = 0;
        for (String segment : segments) {
            int players = Integer.parseInt(segment);
            playersPerLine.add(players);
            this.numberOfPlayers += players;
        }
        generateFormation();
    }

    public Formation(String formationNotation, boolean includesGoalkeeper) {
        this.formationNotation = formationNotation;
        this.includesGoalkeeper = includesGoalkeeper;
        List<Integer> playersPerLine = new ArrayList<>();
        String[] segments = formationNotation.split("-");
        this.numberOfPlayers = 0;
        for (String segment : segments) {
            int players = Integer.parseInt(segment);
            playersPerLine.add(players);
            this.numberOfPlayers += players;
        }
        generateFormation();
    }

    public static Formation copyFormation(Formation f) {
        Formation newFormation = new Formation(f.formationNotation, f.includesGoalkeeper);
        newFormation.setName(f.getName());
        if (f.getTeam() != null) {
            newFormation.setTeam(f.getTeam());
        }
        int count = 0;
        for (Line line : f.getLines()) {
            for (Peg peg : line.getPegs()) {
                newFormation.getPegAtIndex(count).copyPeg(peg);
                count++;
            }
        }
        for (Peg peg : f.getSubsLine().getPegs()) {
            newFormation.getPegAtIndex(count).copyPeg(peg);
            count++;
        }
        return newFormation;
    }

    public static void changeTeamFormation(Formation f1, Formation f2) {
        if (f1.getTeam() == null) {
            return;
        }
        // TODO: Update this to be FormationTeam once it is added
        if (f1.getTeam() instanceof FootballTeam t) {
            t.setFormation(f2);
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        updateFormationNotation();
        return formationNotation + (name != null ? ": " + name : "");
    }

    private void updateFormationNotation() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            Line line = lines.get(i);
            if (includesGoalkeeper && i == 0) {
                continue; // Skip goalkeeper line
            }
            sb.append(line.getPegs().size());
            if (i < lines.size() - 1) {
                sb.append("-");
            }
        }
        formationNotation = sb.toString();
        //System.out.println("Updated formation notation: " + formationNotation);
    }

    public void generateFormation() {
        if (formationNotation == null || formationNotation.isEmpty()) {
            throw new IllegalStateException("Formation notation is not set");
        }
        lines = new ArrayList<>();
        String[] segments = formationNotation.split("-");
        int totalLines = segments.length + (includesGoalkeeper ? 1 : 0);
        double lineSpacing = 1.0 / (totalLines + 1);

        if (includesGoalkeeper) {
            Line goalkeeperLine = new Line(1);
            lines.add(goalkeeperLine);
        }

        for (int i = 0; i < segments.length; i++) {
            int playersInLine = Integer.parseInt(segments[i]);
            Line line = new Line(playersInLine);
            lines.add(line);
        }

        for (int i = 0; i < maxSubstitutes; i++) {
            if (substitutes == null) {
                substitutes = new ArrayList<>();
            }
            subsLine = new Line(maxSubstitutes);
        }
    }

    public void generateFormationWithTeam() {
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
        subsLine = new Line(team.getPlayers().size() - numberOfPlayers);
        for (int i = numberOfPlayers + 1; i <= team.getPlayers().size(); i++) {
            substitutes.add(team.getPlayers().get(i));
            subsLine.addPlayer(team.getPlayers().get(i), i - numberOfPlayers - 1);
        }
    }

    public void setFormation(String formationNotation) {
        if (!isValidFormation(formationNotation)) {
            throw new IllegalArgumentException("Invalid formation notation for " + numberOfPlayers + " players: " + formationNotation);
        } else {
            this.formationNotation = formationNotation;
        }
    }

    public void setTeam(Team team) {
        this.team = team;
        List<Player> players = new ArrayList<>(team.getPlayers().values());
        int count = 0;
        
        for (Line line : lines) {
            for (Peg peg : line.getPegs()) {
                if (count < players.size()) {
                    System.out.println("Adding player " + players.get(count) + " to " + peg.toString());
                    peg.setPlayer(players.get(count));
                    count++;
                }
            }
        }
        for (Peg peg : subsLine.getPegs()) {
            if (count < players.size()) {
                peg.setPlayer(players.get(count));
                count++;
            }
        }
    }

    public void reset() {
        this.team = null;
        for (Line line : lines) {
            for (Peg peg : line.getPegs()) {
                peg.reset();
            }
        }
        for (Peg peg : subsLine.getPegs()) {
            peg.reset();
        }
    }

    public void addPlayer(Player player) {
        Peg peg = getFirstFreePeg();
        if (peg != null) {
            peg.setPlayer(player);
        } else {
            throw new IllegalStateException("No free pegs available to add player " + player.getPreferredName());
        }
    }

    private Peg getFirstFreePeg() {
        for (Line line : lines) {
            for (Peg peg : line.getPegs()) {
                if (peg.getPlayer() == null) {
                    return peg;
                }
            }
        }
        if (subsLine != null) {
            for (Peg peg : subsLine.getPegs()) {
                if (peg.getPlayer() == null) {
                    return peg;
                }
            }
        }
        return null;
    }

    public String getName() {
        return name;
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

    @Deprecated
    public ArrayList<Player> getSubstitutes() {
        return substitutes;
    }

    public Line getSubsLine() {
        return subsLine;
    }

    public ArrayList<Player> getOnFieldPlayers() {
        ArrayList<Player> onFieldPlayers = new ArrayList<>();
        for (Line line : lines) {
            if (!line.equals(subsLine)) {
                for (Peg peg : line.getPegs()) {
                    if (peg.getPlayer() != null) {
                        onFieldPlayers.add(peg.getPlayer());
                    }
                }
            }
        }
        return onFieldPlayers;
    }

    public ArrayList<Player> getSubstitutePlayers() {
        ArrayList<Player> substitutePlayers = new ArrayList<>();
        for (Peg peg : subsLine.getPegs()) {
            if (peg.getPlayer() != null) {
                substitutePlayers.add(peg.getPlayer());
            }
        }
        return substitutePlayers;
    }

    public Peg getPegAtIndex(int i) {
        int count = 0;
        for (Line line : lines) {
            for (Peg peg : line.getPegs()) {
                if (count == i) {
                    return peg;
                }
                count++;
            }
        }
        for (Peg peg : subsLine.getPegs()) {
            if (count == i) {
                return peg;
            }
            count++;
        }
        return null; // Index out of bounds
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
        generateFormation();
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

    public void changeLine(Peg peg, int newLineIndex, int newPositionIndex) {
        if (includesGoalkeeper && lines.get(0).getPegs().contains(peg)) {
            System.out.println("Cannot remove goalkeeper line");
            return;
        }
        Line currentLine = null;
        for (Line line : lines) {
            if (line.getPegs().contains(peg)) {
                currentLine = line;
                break;
            }
        }
        if (currentLine == null) {
            throw new IllegalArgumentException("Peg not found in any line");
        }
        currentLine.removePeg(peg);
        if (currentLine.getPegs().isEmpty()) {
            lines.remove(currentLine);
        }
        Line newLine = lines.get(newLineIndex);
        newLine.addPegAtPosition(peg, newPositionIndex);

        updateFormationNotation();
        setName("New Formation");
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
