package day9;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day9Puzzle {

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;

        List<Long> numbers = new ArrayList<>();
        int count = 25;
        while ((line = reader.readLine()) != null) {
            Long number = Long.parseLong(line);
            numbers.add(number);
        }

        int index = 0;
        long oddNumber = 0;

        for (int i = 0; i < numbers.size() - count; i++) {
            index = i + count;
            long number = numbers.get(i + count);

            Set<Long> subsetNumbers = new HashSet<>();

            for (int j = i; j < i + 25; j++) {
                subsetNumbers.add(numbers.get(j));
            }

            boolean containsDifference = false;

            for (int j = i; j < i + 25; j++) {
                long difference = number - numbers.get(j);

                if (subsetNumbers.contains(difference)) {
                    containsDifference = true;
                    break;
                }
            }

            if (!containsDifference) {
                oddNumber = number;
                System.out.println("Number is : " + number);
                System.out.println("Index : " + (index));
                break;
            }
        }


        for (int i = 0; i < index; i++) {
            int sum = 0;
            long lowestNumber = 0;
            long highestNumber = 0;

            for (int j = i; j < numbers.size(); j++) {
                sum += numbers.get(j);

                if (lowestNumber == 0 || lowestNumber > numbers.get(j)) {
                    lowestNumber = numbers.get(j);
                }

                if (numbers.get(j) > highestNumber) {
                    highestNumber = numbers.get(j);
                }

                if (sum == oddNumber) {
                    System.out.println(lowestNumber + highestNumber);
                    System.exit(0);
                }

                if (sum > oddNumber) {
                    break;
                }
            }
        }
    }
}
