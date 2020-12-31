package day5;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.TreeSet;

public class SeatRetriever {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        int highestValue = 0;
        Set<Integer> seatNumbers = new TreeSet<>();

        while ((line = reader.readLine()) != null) {
            String column = line.substring(7);
            String row = line.substring(0, 7);

            int columnCount = 2;
            int columnValue = 0;
            for (char columnChar : column.toCharArray()) {
                switch (columnChar) {
                    case 'R':
                        columnValue += Math.pow(2, columnCount);
                        break;
                }

                columnCount--;
            }

            int rowCount = 6;
            int rowValue = 0;

            for (char rowChar : row.toCharArray()) {
                switch (rowChar) {
                    case 'B':
                        rowValue += Math.pow(2, rowCount);
                        break;
                }

                rowCount--;
            }

            System.out.println(columnValue);
            System.out.println(rowValue);
            int maxValue = ((rowValue * 8) + columnValue);
            seatNumbers.add(maxValue);
//            System.out.println(maxValue);

            if (highestValue < maxValue) {
                highestValue = maxValue;
                System.out.println("Highest: " + highestValue);
            }
        }

        System.out.println(highestValue);
        System.out.println(seatNumbers.size());
        System.out.println(seatNumbers);
        int seatNumber = 0;

        for (Integer number : seatNumbers) {
            if (seatNumber == 0) {
                seatNumber = number;
                continue;
            }

            if (seatNumber + 1 != number) {
                System.out.println(seatNumber);
                break;
            }

            seatNumber = number;
        }
    }
}
