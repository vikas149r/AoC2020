package day13;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day13Puzzle {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line = reader.readLine();
        int timeStamp = Integer.parseInt(line);
        Set<Integer> busNumbers = new HashSet<>();
        Set<String> busNumbersSet = null;

        line = reader.readLine();
        String[] busNumberStrings = line.split(",");
        List<String> busNumbersList = Arrays.asList(busNumberStrings);
        busNumbersSet = new HashSet<>(busNumbersList);
        busNumbersSet.remove("x");

        System.out.println(busNumbersSet);

        int minMinutesToWait = 0;
        int busToTake = 0;

        for (String busNumberString : busNumbersSet) {
            int busNumber = Integer.parseInt(busNumberString);
            int minutesToWait = busNumber - (timeStamp % busNumber);

            if (minMinutesToWait == 0 || minMinutesToWait > minutesToWait) {
                minMinutesToWait = minutesToWait;
                busToTake = busNumber;
            }
        }

        System.out.println(busToTake * minMinutesToWait);
    }
}
