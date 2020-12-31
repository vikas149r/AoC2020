package day12;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Day12PuzzlePart2 {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        int direction;
        int x_distance = 0;
        int y_distance = 0;

        int waypoint_x_distance = 10;
        int waypoint_y_distance = 1;

        while ((line = reader.readLine()) != null) {
            char movement = line.charAt(0);
            String qty = line.substring(1);
            Integer quant = Integer.parseInt(qty);

            switch (movement) {
                case 'N':
                    waypoint_y_distance += quant;
                    break;

                case 'E':
                    waypoint_x_distance += quant;
                    break;

                case 'S':
                    waypoint_y_distance -= quant;
                    break;

                case 'W':
                    waypoint_x_distance -= quant;
                    break;

                case 'L':
                    direction = (quant / 90) % 4;

                    for (int i = 0; i < direction; i++) {
                        int temp = waypoint_y_distance;
                        waypoint_y_distance = waypoint_x_distance;
                        waypoint_x_distance = -(temp);
                    }

                    break;

                case 'R':
                    direction = (quant / 90) % 4;

                    for (int i = 0; i < direction; i++) {
                        int temp = waypoint_x_distance;
                        waypoint_x_distance = waypoint_y_distance;
                        waypoint_y_distance = -(temp);
                    }

                    break;

                case 'F':
                    x_distance = x_distance + waypoint_x_distance * quant;
                    y_distance = y_distance + waypoint_y_distance * quant;

                    break;
            }
        }

        System.out.println(Math.abs(x_distance) + Math.abs(y_distance));
    }
}
