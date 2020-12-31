package aoc.day1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RetrieveMultiplication {
    private static final int year2020 = 2020;

    public static void main(String[] args) throws Exception {
        final List<Integer> numbers = new ArrayList<Integer>();

        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        while ((line = reader.readLine()) != null) {
            numbers.add(Integer.parseInt(line));
        }
        Collections.sort(numbers);

        retrievePart1(numbers);
        retrievePart2(numbers);
    }

    private static void retrievePart1(List<Integer> numbers) {
        Iterator<Integer> primaryIterator = numbers.iterator();

        while (primaryIterator.hasNext()) {
            Integer number = primaryIterator.next();
            int difference = year2020 - number;

            if (numbers.contains(difference)) {
                System.out.println("Numbers are : " + number + ", " + difference);
                System.out.println("Multiplication is : " + number * difference);
                break;
            }
        }
    }

    private static void retrievePart2(List<Integer> numbers) {
        Iterator<Integer> primaryIterator = numbers.iterator();

        while (primaryIterator.hasNext()) {
            Integer number = primaryIterator.next();
            int difference = year2020 - number;

            Iterator<Integer> secondaryIterator = numbers.iterator();

            while (secondaryIterator.hasNext()) {
                Integer secondNumber =  secondaryIterator.next();

                if (secondNumber == number) {
                    continue;
                }

                int secondDifference = difference - secondNumber;

                if (numbers.contains(secondDifference)) {
                    System.out.println("Numbers are : " + number + ", " + secondNumber + ", " + secondDifference);
                    System.out.println("Multiplication is : " + number * secondNumber * secondDifference);
                    break;
                }
            }
        }
    }
}
