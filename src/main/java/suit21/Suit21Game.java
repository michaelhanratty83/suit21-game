package suit21;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Suit21Game {
    private final Deck deck;
    private final List<Player> players;
    private final List<GameState> gameHistory;

    public Suit21Game(int numberOfPlayers, List<Player> players) {
        this.deck = new Deck();
        this.players = new ArrayList<>(players);
        this.gameHistory = new ArrayList<>();

        for (Player player : this.players) {
            player.getHand().clear();
        }

        dealInitialCards();
        gameHistory.add(new GameState(0, this.players, "Initial deal"));
    }

    public Deck getDeck() {
        return deck;
    }

    private void dealInitialCards() {
        for (int i = 0; i < 5; i++) {
            for (Player player : players) {
                player.addCard(deck.dealCard());
            }
        }
    }

    public void viewReplay() {
        System.out.println("\n=== GAME REPLAY ===");
        System.out.println("-------------------");

        for (GameState state : gameHistory) {
            System.out.println(state);
            System.out.println("-------------------");
        }
    }

    public void play() {
        boolean gameWon = false;
        List<Player> winners = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        int round = 1;

        while (deck.remainingCards() >= players.size() && !gameWon) {
            winners.clear();
            boolean twentyOneFound = false;

            for (Player player : players) {
                System.out.println("\n" + player);
                System.out.println("Maximum suit score: " + player.calculateMaxSuitScore());
                System.out.println("Best suit and score: " + player.getMaxSuitDetails());

                int choice;
                String action;

                if (player.isComputer()) {
                    choice = player.getComputerMove();
                    System.out.println("Computer chooses: " + (choice == 0 ? "skip" : choice));
                    action = "Computer " + player.getName() + " chose: " + (choice == 0 ? "skip" : "swap card " + choice);
                } else {
                    System.out.println(player.getName() + ", choose a card to swap (1-5) or 0 to skip: ");
                    choice = scanner.nextInt();
                    action = "Player " + player.getName() + " chose: " + (choice == 0 ? "skip" : "swap card " + choice);
                }

                if (choice > 0 && choice <= 5) {
                    Card removedCard = player.removeCard(choice - 1);
                    Card newCard = deck.dealCard();
                    if (newCard != null) {
                        player.addCard(newCard);
                        System.out.println("Swapped " + removedCard + " for " + newCard + ".");
                        System.out.println("New hand: " + player.getHand());
                        action += " (Swapped " + removedCard + " for " + newCard + ")";
                    }
                }

                gameHistory.add(new GameState(round, players, action));

                if (player.calculateMaxSuitScore() == 21) {
                    twentyOneFound = true;
                    winners.add(player);
                }
            }

            if (twentyOneFound) {
                double pointsToShare = 1.0 / winners.size();
                for (Player winner : winners) {
                    winner.addSharedPoint(pointsToShare);
                    System.out.println(winner.getName() + " scores 21 and earns " + pointsToShare + " points!");
                }
                gameWon = true;
            }

            round++;
        }

        if (!gameWon) {
            System.out.println("No winner. Deck is empty.");
        }

        System.out.println("\nFinal Scores:");
        List<Player> sortedPlayers = new ArrayList<>(players);
        sortedPlayers.sort((p1, p2) -> Double.compare(p2.getPoints(), p1.getPoints()));
        for (Player player : sortedPlayers) {
            System.out.println(player.getName() + ": " + String.format("%.2f", player.getPoints()) + " points");
        }

        System.out.println("\nWould you like to view the replay? (y/n)");
        scanner.nextLine();
        String viewReplay = scanner.nextLine();
        if (viewReplay.equalsIgnoreCase("y")) {
            viewReplay();
        }
    }
}
