package suit21;

import java.util.ArrayList;
import java.util.List;

public class PlayerState {
    private final String name;
    private final List<Card> hand;
    private final int maxScore;
    private final String maxSuitDetails;
    private final double points;

    private static final String RED = "\u001B[31m";
    private static final String BLACK = "\u001B[30m";
    private static final String RESET = "\u001B[0m";

    public PlayerState(Player player) {
        this.name = player.getName();
        this.hand = new ArrayList<>(player.getHand());
        this.maxScore = player.calculateMaxSuitScore();

        String details = player.getMaxSuitDetails();
        if (details.equals("No valid suit")) {
            this.maxSuitDetails = "No valid suit";
        } else {
            String[] parts = details.split(" with score ");
            String suit = parts[0];
            String score = parts[1];
            if ("♥".equals(suit) || "♦".equals(suit)) {
                this.maxSuitDetails = RED + suit + RESET + " with score " + score;
            } else {
                this.maxSuitDetails = BLACK + suit + RESET + " with score " + score;
            }
        }

        this.points = player.getPoints();
    }

    @Override
    public String toString() {
        return name + "'s hand: " + hand +
                "\nMaximum suit score: " + maxScore +
                "\nBest suit and score: " + maxSuitDetails +
                "\nPoints: " + String.format("%.2f", points);
    }
}
