package team;
import java.util.HashMap;

import player.Player;

public abstract class Team {
    String name;
    HashMap<Integer, Player> players;
    static int maxNumberOfPlayers;

    public Team(String name) {
        this.name = name;
        this.players = new java.util.HashMap<>();
    }

    public String getName() {
        return name;
    }

    public HashMap<Integer, Player> getPlayers() {
        return players;
    }

    public void addPlayer(Player player) {

        if ((players.size() < maxNumberOfPlayers) || (maxNumberOfPlayers == 0)) {
            int number = getFirstNumber();
            players.put(number, player);
            System.out.println("Added player " + player.getPreferredName() + " with number " + number + " to team " + name);
        } else {
            throw new IllegalStateException("Team is already full");
        }
    }

    private int getFirstNumber() {
        int max = players.keySet().stream().max(Integer::compareTo).orElse(0);
        for (int i = 1; i <= max + 1; i++) {
            if (!players.containsKey(i)) {
                return i;
            }
        }
        return max + 1;
    }

    public void setPlayerNumber(int currentNumber, int newNumber) {
        if (!players.containsKey(currentNumber)) {
            throw new IllegalArgumentException("No player with number " + currentNumber + " in team " + name);
        }
        if (players.containsKey(newNumber)) {
            throw new IllegalArgumentException("Number " + newNumber + " is already taken in team " + name);
        }
        Player player = players.remove(currentNumber);
        players.put(newNumber, player);
        System.out.println("Changed player " + player.getPreferredName() + " number from " + currentNumber + " to " + newNumber);
    }

    public int getPlayerNumber(Player player) {
        for (var entry : players.entrySet()) {
            if (entry.getValue().equals(player)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Player " + player.getPreferredName() + " is not in team " + name);
    }

}