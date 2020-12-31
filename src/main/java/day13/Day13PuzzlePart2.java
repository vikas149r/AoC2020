package aoc.day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Pattern;

public class Day13PuzzlePart2 {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line = reader.readLine();
        line = reader.readLine();
        String[] busNumberStrings = line.split(",");

        int waitPeriod = 0;
        List<Integer> busNumbersWaitList = new ArrayList<>();
        List<Integer> busNumbers = new ArrayList<>();

        for (String busNumberString : busNumberStrings) {
            if (busNumberString.equals("x")) {
                waitPeriod++;
                continue;
            }

            int busNumber = Integer.parseInt(busNumberString);
            busNumbers.add(busNumber);
            busNumbersWaitList.add(waitPeriod++);
        }

        List<Integer> sortedBus = new ArrayList<>(busNumbers);
        Collections.sort(sortedBus, Collections.reverseOrder());

        List<Integer> sortedBusWaits = new ArrayList<>();
        int firstBus = busNumbers.get(0);
        int largestBusNumber = sortedBus.get(0);
        int largestBusWait = busNumbersWaitList.get(busNumbers.indexOf(largestBusNumber));

        for (Integer bus : sortedBus) {
            sortedBusWaits.add(busNumbersWaitList.get(busNumbers.indexOf(bus)) - largestBusWait);
        }

        long numberToMultiply = largestBusNumber;

        for (int bus : busNumbers) {
            if (Math.abs(sortedBusWaits.get(sortedBus.indexOf(bus))) == bus) {
                numberToMultiply = numberToMultiply * bus;
            }
        }

        sortedBus.remove(0);
        sortedBusWaits.remove(0);

        System.out.println(numberToMultiply);

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            long timeStamp = (numberToMultiply * i);
            boolean matched = true;

            for (int j = 0; j < sortedBus.size(); j++) {
                int busNumber = sortedBus.get(j);
                int busWait = sortedBusWaits.get(j);

                if ((timeStamp + busWait) % busNumber != 0) {
                    matched = false;
                    break;
                }
            }

            if (matched) {
                System.out.println(timeStamp - firstBus);
                break;
            }
        }
    }
}
