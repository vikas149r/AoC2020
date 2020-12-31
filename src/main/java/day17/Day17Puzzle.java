package day17;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Day17Puzzle {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line = reader.readLine();
        int count = 0;
        char[][][] threeDimensionArray;

        if (line == null) {
            return;
        }

        count = line.length();
        threeDimensionArray = new char[count][count][count];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                for (int k = 0; k < count; k++) {
                    threeDimensionArray[i][j][k] = '.';
                }
            }
        }

        for (int i = 0; i < count; i++, line = reader.readLine()) {
            char[] characters = line.toCharArray();

            for (int j = 0; j < count; j++) {
                char character = characters[j];
                threeDimensionArray[i][j][1] = character;
            }
        }

        int newCount = count;
        char[][][] tempArray;
        char[][][] modifiedArray = null;

        for (int cycle = 0; cycle < 6; cycle++) {
            if (modifiedArray == null) {
                tempArray = threeDimensionArray;
            } else {
                tempArray = modifiedArray;
            }

            newCount += 2;
            char[][][] array = new char[newCount][newCount][newCount];
            modifiedArray = new char[newCount][newCount][newCount];

            for (int i = 0; i < newCount; i++) {
                for (int j = 0; j < newCount; j++) {
                    for (int k = 0; k < newCount; k++) {
                        array[i][j][k] = '.';
                        modifiedArray[i][j][k] = '.';
                    }
                }
            }

            for (int i = 1; i < newCount - 1; i++) {
                for (int j = 1; j < newCount - 1; j++) {
                    for (int k = 1; k < newCount - 1; k++) {
                        array[i][j][k] = tempArray[i - 1][j - 1][k - 1];
                    }
                }
            }

            for (int i = 0; i < newCount; i++) {
                for (int j = 0; j < newCount; j++) {
                    for (int k = 0; k < newCount; k++) {
                        int activeCubes = 0;

                        for (int x = i - 1; x <= i + 1; x++) {
                            if (x < 0 || x == newCount) {
                                continue;
                            }

                            for (int y = j - 1; y <= j + 1; y++) {
                                if (y < 0 || y == newCount) {
                                    continue;
                                }

                                for (int z = k - 1; z <= k + 1; z++) {
                                    if (z < 0 || z == newCount) {
                                        continue;
                                    }

                                    if (x == i && y == j && z == k) {
                                        continue;
                                    }

                                    if (array[x][y][z] == '#') {
                                        activeCubes++;
                                    }
                                }
                            }
                        }

                        if (array[i][j][k] == '#') {
                            if (activeCubes == 2 || activeCubes == 3) {
                                modifiedArray[i][j][k] = '#';
                            } else {
                                modifiedArray[i][j][k] = '.';
                            }
                        }

                        if (array[i][j][k] == '.') {
                            if (activeCubes == 3) {
                                modifiedArray[i][j][k] = '#';
                            }
                        }
                    }
                }
            }
        }

        int total = 0;

        for (int i = 0; i < newCount; i++) {
            for (int j = 0; j < newCount; j++) {
                for (int k = 0; k < newCount; k++) {
                    if (modifiedArray[i][j][k] == '#') {
                        total++;
                    }
                }
            }
        }

        System.out.println(total);
    }
}
