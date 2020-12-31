package day11;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Day11PuzzlePart2 {
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
                    System.out.println(seatRow);
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

        String seatToTheRight;
        String seatToTheLeft;

        int countOfOccupied = 0;
        int countOfEmpty = 0;

        seatToTheRight = (j == rowLength - 1) ? null : rowSeats.substring(j + 1, j + 2);

        int increment = 0;

        if (seatToTheRight != null) {
            while (seatToTheRight != null && seatToTheRight.equals(".")) {
                increment++;
                seatToTheRight = (j + increment == rowLength - 1) ? null : rowSeats.substring(j + 1 + increment, j + 2 + increment);
            }

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
        }

        increment = 0;
        seatToTheLeft = (j == 0) ? null : rowSeats.substring(j - 1, j);

        if (seatToTheLeft != null) {
            while (seatToTheLeft != null && seatToTheLeft.equals(".")) {
                increment++;
                seatToTheLeft = (j - increment == 0) ? null : rowSeats.substring(j - 1 - increment, j - increment);
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
        }

        if (seatsInFront != null) {
            String seatFront;
            String seatFrontRight;
            String seatFrontLeft;

            seatFront = seatsInFront.substring(j, j + 1);

            increment = 0;
            while (seatFront.equals(".")) {
                String newFrontRow = (i - increment == 0) ? null : seatConfigurations.get(i - increment - 1);

                if (newFrontRow == null) {
                    seatFront = null;
                    break;
                }

                increment++;
                seatFront = newFrontRow.substring(j, j + 1);
            }

            if (seatFront != null) {
                switch (seatFront) {
                    case "L":
                        countOfEmpty++;
                        break;
                    case "#":
                        countOfOccupied++;
                        break;
                }
            }

            increment = 0;
            seatFrontRight = (j == rowLength - 1) ? null : seatsInFront.substring(j + 1, j + 2);

            if (seatFrontRight != null) {
                while (seatFrontRight != null && seatFrontRight.equals(".")) {
                    String newFrontRow = (i - increment == 0) ? null : seatConfigurations.get(i - increment - 1);

                    if (newFrontRow == null) {
                        seatFrontRight = null;
                        break;
                    }

                    seatFrontRight = (j + increment == rowLength - 1) ? null : newFrontRow.substring(j + 1 + increment, j + 2 + increment);
                    increment++;
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
            }

            increment = 0;
            seatFrontLeft = (j == 0) ? null : seatsInFront.substring(j - 1, j);

            if (seatFrontLeft != null) {
                while (seatFrontLeft != null && seatFrontLeft.equals(".")) {
                    String newFrontRow = (i - increment == 0) ? null : seatConfigurations.get(i - increment - 1);

                    if (newFrontRow == null) {
                        seatFrontLeft = null;
                        break;
                    }

                    seatFrontLeft = (j - increment == 0) ? null : newFrontRow.substring(j - 1 - increment, j - increment);
                    increment++;
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
        }

        if (seatsBehind != null) {
            String seatBehind;
            String seatBehindRight;
            String seatBehindLeft;

            seatBehind = seatsBehind.substring(j, j + 1);
            increment = 0;

            while (seatBehind.equals(".")) {
                String newRowBehind = (i + increment == seatConfigurations.size() - 1) ? null : seatConfigurations.get(i + increment + 1);

                if (newRowBehind == null) {
                    seatBehind = null;
                    break;
                }

                seatBehind = newRowBehind.substring(j, j + 1);
                increment++;
            }

            if (seatBehind != null) {
                switch (seatBehind) {
                    case "L":
                        countOfEmpty++;
                        break;
                    case "#":
                        countOfOccupied++;
                        break;
                }
            }

            increment = 0;
            seatBehindRight = (j == rowLength - 1) ? null : seatsBehind.substring(j + 1, j + 2);

            if (seatBehindRight != null) {
                while (seatBehindRight != null && seatBehindRight.equals(".")) {
                    String newRowBehind = (i + increment == seatConfigurations.size() - 1) ? null : seatConfigurations.get(i + increment + 1);

                    if (newRowBehind == null) {
                        seatBehindRight = null;
                        break;
                    }

                    seatBehindRight = (j + increment == rowLength - 1) ? null : newRowBehind.substring(j + 1 + increment, j + 2 + increment);
                    increment++;
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
            }

            increment = 0;
            seatBehindLeft = (j == 0) ? null : seatsBehind.substring(j - 1, j);

            if (seatBehindLeft != null) {
                while (seatBehindLeft != null && seatBehindLeft.equals(".")) {
                    String newRowBehind = (i + increment == seatConfigurations.size() - 1) ? null : seatConfigurations.get(i + increment + 1);

                    if (newRowBehind == null) {
                        seatBehindLeft = null;
                        break;
                    }

                    seatBehindLeft = (j - increment == 0) ? null : newRowBehind.substring(j - 1 - increment, j - increment);
                    increment++;
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
        }

        if (seat.equals("L") && countOfOccupied == 0) {
            return "#";
        }

        if (seat.equals("#") && countOfOccupied >= 5) {
            return "L";
        }

        return seat;
    }
}
