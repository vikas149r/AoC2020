package aoc.day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Day12Puzzle {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        int direction = 0;
        int x_distance = 0;
        int y_distance = 0;

        while ((line = reader.readLine()) != null) {
            char movement = line.charAt(0);
            String qty = line.substring(1);
            Integer quant = Integer.parseInt(qty);

            switch (movement) {
                case 'N':
                    y_distance += quant;
                    break;

                case 'E':
                    x_distance += quant;
                    break;

                case 'S':
                    y_distance -= quant;
                    break;

                case 'W':
                    x_distance -= quant;
                    break;
                case 'L':
                    direction = (direction + (quant / 90)) % 4;
                    break;

                case 'R':
                    direction = (4 + (direction - (quant / 90))) % 4;
                    break;

                case 'F':
                    switch (direction) {
                        case 0:
                            x_distance += quant;
                            break;


                        case 1:
                            y_distance += quant;
                            break;

                        case 2:
                            x_distance -= quant;
                            break;

                        case 3:
                            y_distance -= quant;
                            break;
                    }
                    break;
            }
        }

        System.out.println(Math.abs(x_distance) + Math.abs(y_distance));
    }
}
