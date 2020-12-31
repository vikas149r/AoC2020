package aoc.day25;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Day25Puzzle {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        int cardPublicKey;
        int doorPublicKey;

        int cardLoops = 0;
        int doorLoops = 0;
        String line = reader.readLine();
        cardPublicKey = Integer.parseInt(line);
        line = reader.readLine();
        doorPublicKey = Integer.parseInt(line);

        System.out.println(cardPublicKey);
        System.out.println(doorPublicKey);

        long value = 1;
        int subjectNumber = 7;
        int divider = 20201227;
        int i = 1;

        while (cardLoops == 0 || doorLoops == 0) {
            value = value * subjectNumber;
            value = value % divider;

            if (value == cardPublicKey) {
                cardLoops = i;
            }

            if (value == doorPublicKey) {
                doorLoops = i;
            }

            i++;
        }

        value = 1;

        for (int x = 1; x <= cardLoops; x++) {
            value = value * doorPublicKey;
            value = value % divider;
        }

        System.out.println(value);

        value = 1;

        for (int x = 1; x <= doorLoops; x++) {
            value = value * cardPublicKey;
            value = value % divider;
        }

        System.out.println(value);
    }
}
