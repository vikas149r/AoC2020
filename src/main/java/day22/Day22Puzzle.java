package day22;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Day22Puzzle {
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

        while (!player1Cards.isEmpty() && !player2Cards.isEmpty()) {
            Integer player1 = player1Cards.get(0);
            Integer player2 = player2Cards.get(0);

            if (player1 < player2) {
                player1Cards.remove(player1);
                player2Cards.remove(player2);
                player2Cards.add(player2);
                player2Cards.add(player1);
            }

            if (player2 < player1) {
                player1Cards.remove(player1);
                player2Cards.remove(player2);
                player1Cards.add(player1);
                player1Cards.add(player2);
            }
        }

        System.out.println(player1Cards);
        System.out.println(player2Cards);

        int total = 0;
        for (int i = player1Cards.size() - 1, j = 1; i >= 0; i--, j++) {
            total += (player1Cards.get(i) * j);
        }
        System.out.println(total);
    }
}
