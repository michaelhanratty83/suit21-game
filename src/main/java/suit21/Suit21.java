package suit21;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Suit21 {
    public static int getValidPlayerCount(Scanner scanner) {
        int players;
        do {
            System.out.print("Enter number of players (2-6): ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }

            players = scanner.nextInt();

            if (players < 2 || players > 6) {
                System.out.println("Number of players must be between 2 and 6.");
            }
        } while (players < 2 || players > 6);

        return players;
    }

    public static void main(String[] args) {
        boolean colorSupport = true;
        try {
            System.out.print("\u001B[31mTesting color support...\u001B[0m");
            System.out.println();
        } catch (Exception e) {
            colorSupport = false;
            System.out.println("ANSI colors not supported in this terminal. Using monochrome mode.");
        }

        Card.setUseColor(colorSupport);

        Scanner scanner = new Scanner(System.in);
        int numberOfPlayers = getValidPlayerCount(scanner);
        scanner.nextLine();

        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            players.add(new Player(scanner.nextLine()));
        }

        System.out.print("Enter number of games to play: ");
        int numberOfGames = scanner.nextInt();

        for (int game = 1; game <= numberOfGames; game++) {
            System.out.println("\n--- Game " + game + " ---");
            Suit21Game gameInstance = new Suit21Game(numberOfPlayers, players);
            gameInstance.play();
        }

        System.out.println("\n--- Final Scores After " + numberOfGames + " Games ---");
        players.sort((p1, p2) -> Double.compare(p2.getPoints(), p1.getPoints()));
        for (Player player : players) {
            System.out.println(player.getName() + ": " + String.format("%.2f", player.getPoints()) + " points");
        }

        scanner.close();
    }
}
