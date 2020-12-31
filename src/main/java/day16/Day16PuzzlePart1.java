package day16;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Day16PuzzlePart1 {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        int lowestValidNumber = -1;
        int highestValidNumber = -1;
        boolean startScanningNearby = false;
        int errorValue = 0;

        while ((line = reader.readLine()) != null) {
            if (line.contains(" or ")) {
                String[] parameters = line.split(": ");
                String[] numberRanges = parameters[1].split(" or ");

                String lowestNumberString = numberRanges[0].substring(0, numberRanges[0].indexOf("-"));
                /*String highestMidRangeNumber = numberRanges[0].substring(numberRanges[0].indexOf("-") + 1);
                String lowestMidRangeNumber = numberRanges[1].substring(0, numberRanges[1].indexOf("-"));*/
                String highestNumberString = numberRanges[1].substring(numberRanges[1].indexOf("-") + 1);

                int lowestNumber = Integer.parseInt(lowestNumberString);
                int highestNumber = Integer.parseInt(highestNumberString);

                if (lowestValidNumber == -1 || lowestValidNumber > lowestNumber) {
                    lowestValidNumber = lowestNumber;
                }

                if (highestValidNumber == -1 || highestValidNumber < highestNumber) {
                    highestValidNumber = highestNumber;
                }
            }

            if (line.contains("nearby tickets")) {
                startScanningNearby = true;
                continue;
            }

            if (startScanningNearby) {
                String[] numbers = line.split(",");

                for (String numString : numbers) {
                    int number = Integer.parseInt(numString);

                    if (number < lowestValidNumber || number > highestValidNumber) {
                        errorValue += number;
                    }
                }
            }
        }

        System.out.println(errorValue);
    }
}
