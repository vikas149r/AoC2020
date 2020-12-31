package day17;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Day17PuzzlePart2 {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line = reader.readLine();
        int count = 0;
        char[][][][] fourDimensionArray;

        if (line == null) {
            return;
        }

        count = line.length();
        fourDimensionArray = new char[count][count][count][count];

        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                for (int k = 0; k < count; k++) {
                    for (int l = 0; l < count; l++) {
                        fourDimensionArray[i][j][k][l] = '.';
                    }
                }
            }
        }

        for (int i = 0; i < count; i++, line = reader.readLine()) {
            char[] characters = line.toCharArray();

            for (int j = 0; j < count; j++) {
                char character = characters[j];
                fourDimensionArray[i][j][1][1] = character;
            }
        }

        for (int w = 0; w < count; w++) {
            System.out.println();
            System.out.println();

            for (int z = 0; z < count; z++) {
                System.out.println();
                System.out.println();

                for (int x = 0; x < count; x++) {
                    System.out.println();

                    for (int y = 0; y < count; y++) {
                        System.out.print(fourDimensionArray[x][y][z][w]);
                    }
                }
            }
        }

        int newCount = count;
        char[][][][] tempArray;
        char[][][][] modifiedArray = null;

        for (int cycle = 0; cycle < 6; cycle++) {
            if (modifiedArray == null) {
                tempArray = fourDimensionArray;
            } else {
                tempArray = modifiedArray;
            }

            newCount += 2;
            char[][][][] array = new char[newCount][newCount][newCount][newCount];
            modifiedArray = new char[newCount][newCount][newCount][newCount];

            for (int i = 0; i < newCount; i++) {
                for (int j = 0; j < newCount; j++) {
                    for (int k = 0; k < newCount; k++) {
                        for (int l = 0; l < newCount; l++) {
                            array[i][j][k][l] = '.';
                            modifiedArray[i][j][k][l] = '.';
                        }
                    }
                }
            }

            for (int i = 1; i < newCount - 1; i++) {
                for (int j = 1; j < newCount - 1; j++) {
                    for (int k = 1; k < newCount - 1; k++) {
                        for (int l = 1; l < newCount - 1; l++) {
                            array[i][j][k][l] = tempArray[i - 1][j - 1][k - 1][l - 1];
                        }
                    }
                }
            }

            for (int i = 0; i < newCount; i++) {
                for (int j = 0; j < newCount; j++) {
                    for (int k = 0; k < newCount; k++) {
                        for (int l = 0; l < newCount; l++) {
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

                                        for (int w = l - 1; w <= l + 1; w++) {
                                            if (w < 0 || w == newCount) {
                                                continue;
                                            }

                                            if (x == i && y == j && z == k && w == l) {
                                                continue;
                                            }

                                            if (array[x][y][z][w] == '#') {
                                                activeCubes++;
                                            }
                                        }
                                    }
                                }
                            }

                            if (array[i][j][k][l] == '#') {
                                if (activeCubes == 2 || activeCubes == 3) {
                                    modifiedArray[i][j][k][l] = '#';
                                } else {
                                    modifiedArray[i][j][k][l] = '.';
                                }
                            }

                            if (array[i][j][k][l] == '.') {
                                if (activeCubes == 3) {
                                    modifiedArray[i][j][k][l] = '#';
                                }
                            }
                        }
                    }
                }
            }

            /*for (int z = 0; z < newCount; z++) {
                System.out.println();
                System.out.println();

                for (int x = 0; x < newCount; x++) {
                    System.out.println();

                    for (int y = 0; y < newCount; y++) {
                        System.out.print(array[x][y][z]);
                    }
                }
            }*/
        }

        /*for (int z = 0; z < newCount; z++) {
            System.out.println();
            System.out.println();

            for (int x = 0; x < newCount; x++) {
                System.out.println();

                for (int y = 0; y < newCount; y++) {
                    System.out.print(modifiedArray[x][y][z]);
                }

            }
        }*/

        int total = 0;

        for (int i = 0; i < newCount; i++) {
            for (int j = 0; j < newCount; j++) {
                for (int k = 0; k < newCount; k++) {
                    for (int l = 0; l < newCount; l++) {
                        if (modifiedArray[i][j][k][l] == '#') {
                            total++;
                        }
                    }
                }
            }
        }

        System.out.println(total);
    }

}
