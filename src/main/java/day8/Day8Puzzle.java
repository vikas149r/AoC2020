package aoc.day8;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day8Puzzle {
    static List<String> lines = new ArrayList<>();
    static Set<Integer> parsedLines = new HashSet<>();

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;

        int accumulator = 0;

        while ((line = reader.readLine()) != null) {
            lines.add(line);
        }

        int index = 0;
        List<String> movements = new LinkedList<>();
        List<Integer> movementCount = new LinkedList<>();

        while (true) {
            if (parsedLines.contains(index)) {
                break;
            }

            String listLine = lines.get(index);
            parsedLines.add(index);

            String[] lineCommands = listLine.split(" ");
            int movement = Integer.parseInt(lineCommands[1]);
            movements.add(lineCommands[0]);
            movementCount.add(movement);

            switch (lineCommands[0]) {
                case "acc":
                    accumulator = accumulator + movement;
                    index++;
                    break;

                case "jmp":
                    index += movement;
                    break;

                case "nop":
                    index++;
                    break;
            }
        }

        List<String> originalMovements = new LinkedList<>(movements);
        List<Integer> originalMovementCount = new LinkedList<>(movementCount);
        Collections.reverse(movements);
        Collections.reverse(movementCount);
        boolean succeeded = false;

        for (int i = 0; i < movements.size(); i++) {
            if (succeeded) {
                break;
            }

            int newAccumulator = 0;

            switch (movements.get(i)) {
                case "acc":
                    index = index - 1;
                    parsedLines.remove(index);
                    accumulator -= movementCount.get(i);
                    break;

                case "jmp":
                    index = index - movementCount.get(i);
                    parsedLines.remove(index);
                    newAccumulator = executeInstructions(accumulator, index, new HashSet<>(parsedLines), "nop", movementCount.get(i));

                    if (newAccumulator != 0) {
                        succeeded = true;
                        System.out.println("Value is : " + newAccumulator);
                        break;
                    }
                    break;

                case "nop":
                    index = index - 1;
                    parsedLines.remove(index);
                    newAccumulator = executeInstructions(accumulator, index, new HashSet<>(parsedLines), "jmp", movementCount.get(i));

                    if (newAccumulator != 0) {
                        succeeded = true;
                        System.out.println("Value is : " + newAccumulator);
                        break;
                    }
                    break;
            }
        }
    }

    private static int executeInstructions(int accumulator, int index, Set<Integer> tempParsedLines, String instructionToPerform, int movementToPerform) {
        switch (instructionToPerform) {
            case "jmp":
                index += movementToPerform;
                break;

            case "nop":
                index++;
                break;
        }

        while (true) {
            if (tempParsedLines.contains(index)) {
                return 0;
            }

            if (index >= lines.size()) {
                return accumulator;
            }

            String listLine = lines.get(index);
            tempParsedLines.add(index);

            String[] lineCommands = listLine.split(" ");
            int movement = Integer.parseInt(lineCommands[1]);

            switch (lineCommands[0]) {
                case "acc":
                    accumulator = accumulator + movement;
                    index++;
                    break;

                case "jmp":
                    index += movement;
                    break;

                case "nop":
                    index++;
                    break;
            }
        }
    }
}
