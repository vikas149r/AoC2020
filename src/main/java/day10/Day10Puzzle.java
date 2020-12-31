package day10;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day10Puzzle {
    static List<Integer> numbers = new ArrayList<>();
    static Map<Integer, Integer> numberofPaths = new HashMap<>();
    static Map<Integer, Long> pathCounts = new HashMap<>();
    static long totalCount = 0;

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;

        while ((line = reader.readLine()) != null) {
            numbers.add(Integer.parseInt(line));
        }

        numbers.add(0);

        Collections.sort(numbers);
        numbers.add(numbers.get(numbers.size() - 1) + 3);

        int countOf1Jolt = 0;
        int countof3Jolt = 0;

        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i + 1) - numbers.get(i) == 1) {
                countOf1Jolt++;
            }

            if (numbers.get(i + 1) - numbers.get(i) == 3) {
                countof3Jolt++;
            }
        }

        System.out.println(numbers);
        System.out.println(countOf1Jolt);
        System.out.println(countof3Jolt);

        for (int i = 0; i < numbers.size(); i++) {
            int paths = 0;

            for (int j = i; j < numbers.size(); j++) {
                int difference = numbers.get(j) - numbers.get(i);
                if (difference == 0) {
                    continue;
                }

                if (difference > 3) {
                    break;
                }

                paths++;
            }

            numberofPaths.put(numbers.get(i), paths);
        }

        System.out.println(numberofPaths);
        getPermutations(0);

        System.out.println(totalCount);
    }

    private static void getPermutations(int initialNumber) {
        if (initialNumber == numbers.get(numbers.size() - 1)) {
            totalCount++;
            return;
        }

        long localTotal = 0;

        if (numberofPaths.get(initialNumber) == 1) {
            Long countOutput = pathCounts.get(initialNumber);
            localTotal = totalCount;
            if (countOutput != null) {
                totalCount += countOutput;
                return;
            }
        }

        if (numbers.contains(initialNumber + 3)) {
            getPermutations(initialNumber + 3);
        }

        if (numbers.contains(initialNumber + 2)) {
            getPermutations(initialNumber + 2);
        }

        if (numbers.contains(initialNumber + 1)) {
            getPermutations(initialNumber + 1);
        }

        if (numberofPaths.get(initialNumber) == 1) {
            pathCounts.put(initialNumber, totalCount - localTotal);
        }
    }
}
