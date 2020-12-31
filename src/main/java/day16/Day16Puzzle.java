package aoc.day16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day16Puzzle {
    static class Range {
        int lowestNumber;
        int highestMid;
        int lowestMid;
        int highest;

        public Range(int lowestNumber, int highestMid, int lowestMid, int highest) {
            this.lowestNumber = lowestNumber;
            this.highestMid = highestMid;
            this.lowestMid = lowestMid;
            this.highest = highest;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Range range = (Range) o;
            return lowestNumber == range.lowestNumber &&
                    highestMid == range.highestMid &&
                    lowestMid == range.lowestMid &&
                    highest == range.highest;
        }

        @Override
        public int hashCode() {
            return Objects.hash(lowestNumber, highestMid, lowestMid, highest);
        }
    }
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        Map<String, Range> parameterToRange = new HashMap<>();
        String line;
        int lowestValidNumber = -1;
        int highestValidNumber = -1;
        boolean startScanningNearby = false;
        int errorValue = 0;
        List<String> validRows = new LinkedList<>();
        String ticketRow = null;
        int totalColumns = 0;

        while ((line = reader.readLine()) != null) {
            if (line.contains(" or ")) {
                String[] parameters = line.split(": ");
                String[] numberRanges = parameters[1].split(" or ");

                String lowestNumberString = numberRanges[0].substring(0, numberRanges[0].indexOf("-"));
                String highestNumberString = numberRanges[1].substring(numberRanges[1].indexOf("-") + 1);
                int lowestNumber = Integer.parseInt(lowestNumberString);
                int highestNumber = Integer.parseInt(highestNumberString);

                String highestMidRangeNumber = numberRanges[0].substring(numberRanges[0].indexOf("-") + 1);
                String lowestMidRangeNumber = numberRanges[1].substring(0, numberRanges[1].indexOf("-"));
                int highestMidRange = Integer.parseInt(highestMidRangeNumber);
                int lowestMidRange = Integer.parseInt(lowestMidRangeNumber);
                Range range = new Range(lowestNumber, highestMidRange, lowestMidRange, highestNumber);
                parameterToRange.put(parameters[0], range);

                if (lowestValidNumber == -1 || lowestValidNumber > lowestNumber) {
                    lowestValidNumber = lowestNumber;
                }

                if (highestValidNumber == -1 || highestValidNumber < highestNumber) {
                    highestValidNumber = highestNumber;
                }
            }

            if (line.contains("your ticket")) {
                line = reader.readLine();
                ticketRow = line;
                totalColumns = ticketRow.split(",").length;
                continue;
            }

            if (line.contains("nearby tickets")) {
                startScanningNearby = true;
                continue;
            }

            if (startScanningNearby) {
                String[] numbers = line.split(",");

                boolean invalidTicket = false;

                for (String numString : numbers) {
                    int number = Integer.parseInt(numString);

                    if (number < lowestValidNumber || number > highestValidNumber) {
                        invalidTicket = true;
                        break;
                    }
                }

                if (!invalidTicket) {
                    validRows.add(line);
                }
            }
        }

        Set<Integer> allColumns = new HashSet<>();

        for (int i = 0; i < totalColumns; i++) {
            allColumns.add(i);
        }

        Map<String, Set<Integer>> validColumns = new TreeMap<>();

        for (String parameter : parameterToRange.keySet()) {
            Range range = parameterToRange.get(parameter);

            for (String row : validRows) {
                String[] numbers = row.split(",");

                for (int i = 0; i < numbers.length; i++) {
                    int number = Integer.parseInt(numbers[i]);

                    if (number > range.highestMid && number < range.lowestMid) {
                        Set<Integer> columns = validColumns.get(parameter);

                        if (columns == null) {
                            columns = new TreeSet<>(allColumns);
                            validColumns.put(parameter, columns);
                        }

                        columns.remove(i);
                    }
                }
            }
        }

        System.out.println(validColumns);
    }
}
