package aoc.day18;

import aoc.day16.Day16Puzzle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Day18Puzzle {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));
        long sum = 0;
        String line = null;
        Stack<Long> processingStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        while ((line = reader.readLine()) != null) {
            line = line.replace(" ", "");

            if (line.length() < 4) {
                System.out.println(line);
            }

            StringBuilder processingNotation = new StringBuilder();

            for (int i = 0; i < line.length(); i++) {
                char character = line.charAt(i);

                switch (character) {
                    case '*':
                    case '+':
                        if (!operatorStack.empty() && operatorStack.peek() != '(') {
                            processingNotation.append(operatorStack.pop());
                        }
                    case '(':
                        operatorStack.push(character);
                        break;

                    case ')':
                        char operator;

                        while ((operator = operatorStack.pop()) != '(') {
                            processingNotation.append(operator);
                        }
                        break;
                    default:
                        processingNotation.append(character);
                        break;
                }
            }

            if (!operatorStack.empty() && operatorStack.peek() != '(') {
                processingNotation.append(operatorStack.pop());
            }

            String rpnString = processingNotation.toString();

            for (int i = 0; i < rpnString.length(); i++) {
                char character = rpnString.charAt(i);

                switch(character) {
                    case '*':
                        processingStack.push(processingStack.pop() * processingStack.pop());
                        break;

                    case '+':
                        processingStack.push(processingStack.pop() + processingStack.pop());
                        break;

                    default:
                        processingStack.push((long) Character.digit(character, 10));
                        break;
                }
            }

            long result = processingStack.pop();
            sum += result;
        }

        System.out.println(sum);
    }
}