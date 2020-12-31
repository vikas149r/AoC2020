package day15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day15Puzzle {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;

        Map<Integer, List<Integer>> reverseMapping = new HashMap<>();
        int count = 1;
        int previous = 0;

        while ((line = reader.readLine()) != null) {
            String[] numbers = line.split(",");

            for (String number : numbers) {
                int num = Integer.parseInt(number);
                previous = num;

                List<Integer> numberList = reverseMapping.get(num);

                if (numberList == null) {
                    numberList = new LinkedList<>();
                    reverseMapping.put(num, numberList);
                }

                numberList.add(count);
                count++;
            }
        }

        int numToGet = 30000000;
        for (int i = count; i <= numToGet; i++) {
            List<Integer> numberList = reverseMapping.get(previous);

            if (numberList.size() == 1) {
                previous = 0;
                reverseMapping.get(0).add(count);
            } else {
                int previousOcc = numberList.get(numberList.size() - 1);
                int secondPreviousOcc = numberList.get(numberList.size() - 2);
                int current = previousOcc - secondPreviousOcc;
                previous = current;

                List<Integer> numList = reverseMapping.get(current);

                if (numList == null) {
                    numList = new LinkedList<>();
                    reverseMapping.put(current, numList);
                }

                /*if (numList.size() >= 2) {
                    int pO = numList.get(numList.size() - 1);
                    numList.clear();
                    numList.add(pO);
                }*/

                numList.add(count);
            }

            count++;
        }

        System.out.println(previous);
    }
}
