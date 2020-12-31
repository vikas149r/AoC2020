package aoc.day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Day11Puzzle {
    static Map<Integer, String> seatConfigurations = new HashMap<>();
    static int rowLength = 0;

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;

        int row = 0;

        while ((line = reader.readLine()) != null) {
            rowLength = line.length();
            seatConfigurations.put(row++, line);
        }

        Map<Integer, String> newConfigurations = getNewConfigurations(seatConfigurations);

        int totalOccupied = 0;
        Map<Integer, String> iteratedConfigurations = null;

        for (int count = 0; count < Integer.MAX_VALUE; count++) {
            Map<Integer, String> currentConfigurations;

            if (count == 0) {
                currentConfigurations = new HashMap<>(newConfigurations);
            } else {
                currentConfigurations = new HashMap<>(iteratedConfigurations);
            }

            iteratedConfigurations = getNewConfigurations(currentConfigurations);

            if (iteratedConfigurations.equals(currentConfigurations)) {
                for (int i = 0; i < iteratedConfigurations.size(); i++) {
                    String seatRow = iteratedConfigurations.get(i);
                    char[] seats = seatRow.toCharArray();

                    for (int j = 0; j < rowLength; j++) {
                        if (seats[j] == '#') {
                            totalOccupied++;
                        }
                    }
                }

                System.out.println("Occupied : " + totalOccupied);
                break;
            }
        }


    }

    private static Map<Integer, String> getNewConfigurations(Map<Integer, String> seatConfigurations) {
        Map<Integer, String> newConfigurations = new HashMap<>();

        for (int i = 0; i < seatConfigurations.size(); i++) {
            String seatRow = "";
            for (int j = 0; j < rowLength; j++) {
                String currentSeat = getCurrentSeat(seatConfigurations, i, j);
                seatRow = seatRow + currentSeat;
            }

            newConfigurations.put(i, seatRow);
        }

        return newConfigurations;
    }

    private static String getCurrentSeat(Map<Integer, String> seatConfigurations, int i, int j) {
        String seatsBehind = (i == seatConfigurations.size() - 1) ? null : seatConfigurations.get(i + 1);
        String seatsInFront = (i == 0) ? null : seatConfigurations.get(i - 1);
        String rowSeats = seatConfigurations.get(i);

        String seat = rowSeats.substring(j, j + 1);

        if (seat.equals(".")) {
            return ".";
        }

        String seatToTheRight = (j == rowLength - 1) ? null : rowSeats.substring(j + 1, j + 2);
        String seatToTheLeft = (j == 0) ? null : rowSeats.substring(j - 1, j);

        int countOfOccupied = 0;
        int countOfEmpty = 0;

        if (seatToTheRight != null) {
            switch (seatToTheRight) {
                case "L":
                    countOfEmpty++;
                    break;
                case "#":
                    countOfOccupied++;
                    break;
            }
        }

        if (seatToTheLeft != null) {
            switch (seatToTheLeft) {
                case "L":
                    countOfEmpty++;
                    break;
                case "#":
                    countOfOccupied++;
                    break;
            }
        }

        if (seatsInFront != null) {
            String seatFront = seatsInFront.substring(j, j + 1);
            String seatFrontRight = (j == rowLength - 1) ? null : seatsInFront.substring(j + 1, j + 2);
            String seatFrontLeft = (j == 0) ? null : seatsInFront.substring(j - 1, j);

            switch (seatFront) {
                case "L":
                    countOfEmpty++;
                    break;
                case "#":
                    countOfOccupied++;
                    break;
            }

            if (seatFrontRight != null) {
                switch (seatFrontRight) {
                    case "L":
                        countOfEmpty++;
                        break;
                    case "#":
                        countOfOccupied++;
                        break;
                }
            }

            if (seatFrontLeft != null) {
                switch (seatFrontLeft) {
                    case "L":
                        countOfEmpty++;
                        break;
                    case "#":
                        countOfOccupied++;
                        break;
                }
            }
        }

        if (seatsBehind != null) {
            String seatBehind = seatsBehind.substring(j, j + 1);
            String seatBehindRight = (j == rowLength - 1) ? null : seatsBehind.substring(j + 1, j + 2);
            String seatBehindLeft = (j == 0) ? null : seatsBehind.substring(j - 1, j);

            switch (seatBehind) {
                case "L":
                    countOfEmpty++;
                    break;
                case "#":
                    countOfOccupied++;
                    break;
            }

            if (seatBehindRight != null) {
                switch (seatBehindRight) {
                    case "L":
                        countOfEmpty++;
                        break;
                    case "#":
                        countOfOccupied++;
                        break;
                }
            }

            if (seatBehindLeft != null) {
                switch (seatBehindLeft) {
                    case "L":
                        countOfEmpty++;
                        break;
                    case "#":
                        countOfOccupied++;
                        break;
                }
            }
        }

        if (seat.equals("L") && countOfOccupied == 0) {
            return "#";
        }

        if (seat.equals("#") && countOfOccupied >= 4) {
            return "L";
        }

        return seat;
    }
}
