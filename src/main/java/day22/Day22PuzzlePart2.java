package aoc.day22;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day22PuzzlePart2 {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        List<Integer> player1Cards = new LinkedList<>();
        List<Integer> player2Cards = new LinkedList<>();

        String line;
        List<Integer> referenceList = player1Cards;

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }

            if (line.contains("Player 1")) {
                referenceList = player1Cards;
                continue;
            }

            if (line.contains("Player 2")) {
                referenceList = player2Cards;
                continue;
            }

            referenceList.add(Integer.parseInt(line));
        }

        System.out.println(player1Cards);
        System.out.println(player2Cards);

        int winner = recursiveCombat(player1Cards, player2Cards);

        int total = 0;
        if (winner == 0) {
            player1Cards.addAll(player2Cards);
            player2Cards.clear();

            for (int i = player1Cards.size() - 1, j = 1; i >= 0; i--, j++) {
                total += (player1Cards.get(i) * j);
            }
        }

        if (winner == 1) {
            for (int i = player2Cards.size() - 1, j = 1; i >= 0; i--, j++) {
                total += (player2Cards.get(i) * j);
            }
        }

        System.out.println(total);

        System.out.println(player1Cards);
        System.out.println(player2Cards);
    }

    public static int recursiveCombat(List<Integer> player1Cards, List<Integer> player2Cards) {
        List<String> gameHistory = new ArrayList<>();

        while (!player1Cards.isEmpty() && !player2Cards.isEmpty()) {
            StringBuilder value = new StringBuilder().append(player1Cards.toString()).append(",").append(player2Cards.toString());

            if (gameHistory.contains(value.toString())) {
                return 0;
            }

            gameHistory.add(value.toString());

            Integer player1 = player1Cards.get(0);
            Integer player2 = player2Cards.get(0);

            int winner = 0;

            if (player1Cards.size() - 1 >= player1 && player2Cards.size() - 1 >= player2) {
                List<Integer> newPlayer1Cards = new LinkedList(player1Cards.subList(1, player1 + 1));
                List<Integer> newPlayer2Cards = new LinkedList(player2Cards.subList(1, player2 + 1));
                winner = recursiveCombat(newPlayer1Cards, newPlayer2Cards);
            } else {
                winner = player1.intValue() > player2.intValue() ? 0 : 1;
            }

            if (winner == 0) {
                player1Cards.remove(player1);
                player2Cards.remove(player2);
                player1Cards.add(player1);
                player1Cards.add(player2);
            }

            if (winner == 1) {
                player1Cards.remove(player1);
                player2Cards.remove(player2);
                player2Cards.add(player2);
                player2Cards.add(player1);
            }
        }

        return (player1Cards.size() == 0) ? 1 : 0;
    }
}
