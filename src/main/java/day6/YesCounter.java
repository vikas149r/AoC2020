package day6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class YesCounter {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        int totalCount = 0;
        Set<Character> alphabets = new HashSet<>();
        Set<Character> allAlphabets = new HashSet<>();

        allAlphabets.add('a');
        allAlphabets.add('b');
        allAlphabets.add('c');
        allAlphabets.add('d');
        allAlphabets.add('e');
        allAlphabets.add('f');
        allAlphabets.add('g');
        allAlphabets.add('h');
        allAlphabets.add('i');
        allAlphabets.add('j');
        allAlphabets.add('k');
        allAlphabets.add('l');
        allAlphabets.add('m');
        allAlphabets.add('n');
        allAlphabets.add('o');
        allAlphabets.add('p');
        allAlphabets.add('q');
        allAlphabets.add('r');
        allAlphabets.add('s');
        allAlphabets.add('t');
        allAlphabets.add('u');
        allAlphabets.add('v');
        allAlphabets.add('w');
        allAlphabets.add('x');
        allAlphabets.add('y');
        allAlphabets.add('z');

        Set<Character> noAlphabets = new HashSet<>(allAlphabets);

        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                Set<Character> output = new HashSet<>(allAlphabets);
                output.removeAll(alphabets);
                totalCount += output.size();
                alphabets = new HashSet<>();
                continue;
            }

            char[] questionsAnswered = line.toCharArray();
            noAlphabets = new HashSet<>(allAlphabets);

            for (char question : questionsAnswered) {
                noAlphabets.remove(question);
            }

            alphabets.addAll(noAlphabets);
        }

        Set<Character> output = new HashSet<>(allAlphabets);
        output.removeAll(alphabets);
        totalCount += output.size();

        System.out.println(totalCount);
    }
}
