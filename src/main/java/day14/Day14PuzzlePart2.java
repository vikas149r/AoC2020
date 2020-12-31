package day14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day14PuzzlePart2 {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        String currentMask = null;
        Map<Long, Long> outputNumbers = new HashMap<>();

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("mask")) {
                currentMask = line.substring(line.indexOf("= ") + 2);
            }

            if (line.startsWith("mem")) {
                String memLocationString = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
                String binaryString = Integer.toBinaryString(Integer.parseInt(memLocationString));
                String decimal = line.substring(line.indexOf("= ") + 2);
                long decimalNumber = Long.parseLong(decimal);
                System.out.println(binaryString);
                String outputString = "";

                int paddedLength = currentMask.length() - binaryString.length();

                for (int i = 0; i < currentMask.length(); i++) {
                    char charAtIndex = currentMask.charAt(i);

                    if (i < paddedLength) {
                        switch (charAtIndex) {
                            case 'X':
                                outputString = outputString + "X";
                                break;
                            case '0':
                                outputString = outputString + "0";
                                break;
                            case '1':
                                outputString = outputString + "1";
                                break;
                        }
                    } else {
                        char charInBinary = binaryString.charAt(i - paddedLength);

                        switch (charAtIndex) {
                            case 'X':
                                outputString = outputString + "X";
                                break;
                            case '0':
                                outputString = outputString + charInBinary;
                                break;
                            case '1':
                                outputString = outputString + "1";
                                break;
                        }
                    }
                }

                Set<Long> memoryLocations = new HashSet<>();

                getMemoryLocations(outputString, memoryLocations);

                for (long memLocation : memoryLocations) {
                    outputNumbers.put(memLocation, decimalNumber);
                }
            }
        }

//        System.out.println(Long.parseLong("010000001100101001000111101100011101", 2));
//        System.out.println(outputNumbers);
        long total = 0;

        for (long number : outputNumbers.values()) {
            total = total + number;
        }

        System.out.println(total);
    }

    private static void getMemoryLocations(String outputString, Set<Long> memoryLocations) {
        if (!outputString.contains("X")) {
            memoryLocations.add(Long.parseLong(outputString, 2));
            return;
        }

        int xLocation = outputString.indexOf("X");
        StringBuilder sb = new StringBuilder(outputString);
        sb.setCharAt(xLocation, '0');
        getMemoryLocations(sb.toString(), memoryLocations);
        sb.setCharAt(xLocation, '1');
        getMemoryLocations(sb.toString(), memoryLocations);

    }
}
