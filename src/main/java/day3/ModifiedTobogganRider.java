package day3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ModifiedTobogganRider {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        int columnNumber = 1;

        String line = reader.readLine();

        int trees = 0;
        int empty = 0;

        int columnIncrement = 1;
        int rowIncrement = 2;

        while (true) {
            for (int i = 1; i <= rowIncrement; i++) {
                line = reader.readLine();
            }

            if (line == null) {
                break;
            }

            columnNumber = columnNumber + columnIncrement;

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
