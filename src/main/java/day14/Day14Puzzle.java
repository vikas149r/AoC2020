package day14;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Day14Puzzle {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        String currentMask = null;
        Map<String, Long> outputNumbers = new HashMap<>();

        while ((line = reader.readLine()) != null) {
            if (line.startsWith("mask")) {
                currentMask = line.substring(line.indexOf("= ") + 2);
            }

            if (line.startsWith("mem")) {
                String memLocationString = line.substring(line.indexOf('[') + 1, line.indexOf(']'));
                String decimal = line.substring(line.indexOf("= ") + 2);
                String binaryString = Integer.toBinaryString(Integer.parseInt(decimal));
//                System.out.println(binaryString);
                String outputString = "";

                int paddedLength = currentMask.length() - binaryString.length();

                for (int i = 0; i < currentMask.length(); i++) {
                    char charAtIndex = currentMask.charAt(i);

                    if (i < paddedLength) {
                        if (charAtIndex == 'X') {
                            outputString = outputString + "0";
                        } else {
                            outputString = outputString + charAtIndex;
                        }
                    } else {
                        char charInBinary = binaryString.charAt(i - paddedLength);

                        if (charAtIndex == 'X') {
                            outputString = outputString + charInBinary;
                        } else {
                            /*int newChar = charInBinary & charAtIndex;
                            outputString = newChar + outputString;*/
                            outputString = outputString + charAtIndex;
                        }
                    }
                }

                outputNumbers.put(memLocationString, Long.parseLong(outputString, 2));
            }
        }

//        System.out.println(Long.parseLong("010000001100101001000111101100011101", 2));
        System.out.println(outputNumbers);
        long total = 0;

        for (long number : outputNumbers.values()) {
            total = total + number;
        }

        System.out.println(total);
    }
}
