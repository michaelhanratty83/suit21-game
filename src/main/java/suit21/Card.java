package suit21;

public class Card {
    private final String suit;
    private final String rank;

    private static final String RED = "\u001B[31m";
    private static final String BLACK = "\u001B[30m";
    private static final String RESET = "\u001B[0m";

    private static boolean useColor = true;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public static void setUseColor(boolean use) {
        useColor = use;
    }

    public int getValue() {
        switch (rank) {
            case "A":
                return 11;
            case "K":
            case "Q":
            case "J":
                return 10;
            default:
                return Integer.parseInt(rank);
        }
    }

    @Override
    public String toString() {
        if (useColor) {
            if ("♥".equals(suit) || "♦".equals(suit)) {
                return rank + RED + suit + RESET;
            }
            return rank + BLACK + suit + RESET;
        }
        return rank + suit;
    }
}
