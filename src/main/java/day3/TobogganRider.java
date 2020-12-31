package day3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class TobogganRider {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        int rowNumber = 1;
        int columnNumber = 1;

        String line = reader.readLine();

        int trees = 0;
        int empty = 0;

        while ((line = reader.readLine()) != null) {
            columnNumber = columnNumber + 3;
            rowNumber = rowNumber + 1;

            if (columnNumber > line.length()) {
                columnNumber = columnNumber - line.length();
            }

            char locationCharacter = line.toCharArray()[columnNumber - 1];

            if (locationCharacter == '#') {
                trees++;
            } else {
                empty++;
            }
        }

        System.out.println("Trees : " + trees);
        System.out.println("Empty : " + empty);
    }
}
