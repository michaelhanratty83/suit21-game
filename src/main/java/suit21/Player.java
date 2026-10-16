package suit21;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private final String name;
    private final List<Card> hand;
    private double points;
    private final boolean isComputer;

    private static final String RED = "\u001B[31m";
    private static final String BLACK = "\u001B[30m";
    private static final String RESET = "\u001B[0m";

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        this.points = 0.0;
        this.isComputer = name.equalsIgnoreCase("computer");
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public Card removeCard(int index) {
        return hand.remove(index);
    }

    public List<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public double getPoints() {
        return points;
    }

    public void addPoint() {
        points++;
    }

    public void addSharedPoint(double fraction) {
        points += fraction;
    }

    public boolean isComputer() {
        return isComputer;
    }

    public int calculateMaxSuitScore() {
        Map<String, Integer> suitScores = new HashMap<>();
        Map<String, Integer> aceCountBySuit = new HashMap<>();

        for (Card card : hand) {
            String suit = card.getSuit();
            int value = card.getValue();
            if (value == 11) {
                aceCountBySuit.put(suit, aceCountBySuit.getOrDefault(suit, 0) + 1);
            }
            suitScores.put(suit, suitScores.getOrDefault(suit, 0) + value);
        }

        int best = 0;
        for (Map.Entry<String, Integer> entry : suitScores.entrySet()) {
            String suit = entry.getKey();
            int score = entry.getValue();
            int aces = aceCountBySuit.getOrDefault(suit, 0);

            while (score > 21 && aces > 0) {
                score -= 10;
                aces--;
            }

            if (score <= 21) {
                best = Math.max(best, score);
            }
        }

        return best;
    }

    public String getMaxSuitDetails() {
        Map<String, Integer> suitScores = new HashMap<>();
        Map<String, Integer> aceCounts = new HashMap<>();

        for (Card card : hand) {
            String suit = card.getSuit();
            int value = card.getValue();
            if (value == 11) {
                aceCounts.put(suit, aceCounts.getOrDefault(suit, 0) + 1);
            }
            suitScores.put(suit, suitScores.getOrDefault(suit, 0) + value);
        }

        String bestSuit = "";
        int bestScore = 0;

        for (Map.Entry<String, Integer> entry : suitScores.entrySet()) {
            String suit = entry.getKey();
            int score = entry.getValue();
            int aces = aceCounts.getOrDefault(suit, 0);

            while (score > 21 && aces > 0) {
                score -= 10;
                aces--;
            }

            if (score <= 21 && score > bestScore) {
                bestScore = score;
                bestSuit = suit;
            }
        }

        if (bestSuit.isEmpty()) {
            return "No valid suit";
        }

        String colouredSuit = (bestSuit.equals("♥") || bestSuit.equals("♦"))
                ? RED + bestSuit + RESET
                : BLACK + bestSuit + RESET;

        return colouredSuit + " with score " + bestScore;
    }

    public int getComputerMove() {
        if (calculateMaxSuitScore() == 21) {
            return 0;
        }

        int worstIndex = 0;
        int worstValue = Integer.MAX_VALUE;

        for (int i = 0; i < hand.size(); i++) {
            Card card = hand.get(i);
            if (card.getValue() < worstValue) {
                worstValue = card.getValue();
                worstIndex = i;
            }
        }

        return worstIndex + 1;
    }

    @Override
    public String toString() {
        return name + "'s hand: " + hand + " (Points: " + points + ")";
    }
}
