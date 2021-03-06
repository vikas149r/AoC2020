package day19;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Day19PuzzlePart2 {
    static Map<String, String> wireToInstruction = new HashMap<>();
    static List<String> inputs = new ArrayList<>();
    static Map<String, String> outputInstructions = new HashMap<>();

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        int count = 0;

        while ((line = reader.readLine()) != null) {
            if (Pattern.matches("[\\w]*", line)) {
                inputs.add(line);
            } else {
                String[] instructions = line.split(": ");
                wireToInstruction.put(instructions[0], instructions[1]);
                count++;
            }
        }

        System.out.println(inputs.size());
        System.out.println(wireToInstruction.values().size());
        System.out.println(count);
        String pattern = (parseInstructions("0"));

        int total = 0;

        for (String input : inputs) {
            if (pattern.contains("n")) {
                for (int i = 1; i < 30; i++) {
                    String newPattern = pattern.replace("n", i + "");

                    if (Pattern.matches(newPattern, input)) {
                        total++;
                        break;
                    }
                }
            } else {
                if (Pattern.matches(pattern, input)) {
                    total++;
                }
            }
        }

        System.out.println(total);
    }

    public static String parseInstructions(String wire) {
        if (Pattern.matches("[\\D]*", wire)) {
            return wire.replaceAll("\"", "");
        }

        if (outputInstructions.containsKey(wire)) {
            return outputInstructions.get(wire);
        }

        String instruction = wireToInstruction.get(wire);

        if (Pattern.matches("[\\D]*", instruction)) {
            instruction = instruction.replaceAll("\"", "");
            outputInstructions.put(wire, instruction);
            return instruction;
        }

        /*if (instruction.contains("|")) {
            String[] wires = instruction.split("\\|");
            String wire1 = parseInstructions(wires[0].trim());
            String wire2 = parseInstructions(wires[1].trim());

            String output = wire1 + "|" + wire2;
            outputInstructions.put(wire, output);
            return output;
        } else */
        if (instruction.contains(" ")) {
            String infiniteLoopSolution = "";

            boolean contains = false;

            String[] wires = instruction.split(" ");
            for (String splitInstr : wires) {
                if (splitInstr.equals(wire)) {
                    contains = true;
                    break;
                }
            }

            if (contains) {
                infiniteLoopSolution = "";
                String tempInstruction = instruction.substring(0, instruction.indexOf("|"));
                String[] tempSplit = tempInstruction.trim().split(" ");

                for (String internalWire : tempSplit) {
                    String parsedWire = parseInstructions(internalWire.trim());
                    infiniteLoopSolution = infiniteLoopSolution + parsedWire;

                    if (wire.equals("8")) {
                        infiniteLoopSolution = infiniteLoopSolution + "+";
                    } else {
                        infiniteLoopSolution = infiniteLoopSolution + "{n}";
                    }
                }
            }

            String output = "(";

            for (String internalWire : wires) {
                if (internalWire.equals("|")) {
                    output = output + "|";
                    continue;
                }

                if (internalWire.equals(wire)) {
                    output = output + infiniteLoopSolution;
                    continue;
                }

                String parsedWire = parseInstructions(internalWire.trim());
                output = output + parsedWire;
            }

            output = output + ")";

            outputInstructions.put(wire, output);
            return output;
        } else {
            return parseInstructions(instruction);
        }
    }
}
