package aoc.day2;

import aoc.day1.RetrieveMultiplication;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CheckPatterns {

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;

        /*line = reader.readLine();
        final String[] parts = line.split(" ");

        System.out.println(parts.length == 3 ? parts[0] + ", " + parts[1] + ", " + parts[2] : "Not matching");
        char alphabet = parts[1].charAt(0);
        System.out.println("Character is : " + alphabet);
        Map<Character, Integer> charToCount = new HashMap<>();

        for (Character currentAlphabet : parts[2].toCharArray()) {
            Integer charCount = charToCount.get(currentAlphabet);
            charToCount.put(currentAlphabet, charCount == null ? 1 : ++charCount);
        }

        System.out.println(charToCount);

        String[] boundParts = parts[0].split("-");

        if (boundParts.length == 2) {
            int lowerBound = Integer.parseInt(boundParts[0]);
            int upperBound = Integer.parseInt(boundParts[1]);

            int count = charToCount.get(alphabet);

            System.out.println("Lower bound : " + lowerBound + ", Upper bound : " + upperBound + ", Count : " + count);

            if (count < lowerBound || count > upperBound) {
                System.out.println("Does not match");
            } else {
                System.out.println("Matches");
            }
        }*/

        int matchingCount = 0;
        int nonMatchingCount = 0;

        while ((line = reader.readLine()) != null) {
            final String[] parts = line.split(" ");

            if (parts.length == 3) {
                char alphabet = parts[1].charAt(0);

                String[] boundParts = parts[0].split("-");

                if (boundParts.length == 2) {
                    int lowerBound = Integer.parseInt(boundParts[0]);
                    int upperBound = Integer.parseInt(boundParts[1]);

                    char firstAlphabet = parts[2].charAt(lowerBound - 1);
                    char secondAlphabet = parts[2].charAt(upperBound - 1);

                    System.out.println("Alphabet : " + alphabet + ", FirstAlphabet : " + firstAlphabet + ", SecondAlphabet : " + secondAlphabet);
                    if (firstAlphabet == secondAlphabet) {
                        nonMatchingCount++;
                    } else if (alphabet != firstAlphabet && alphabet != secondAlphabet) {
                        nonMatchingCount++;
                    } else {
                        matchingCount++;
                    }
                }
            }
        }

        System.out.println("Matching count : " + matchingCount);
        System.out.println("Non-Matching count : " + nonMatchingCount);
    }
}
