package aoc.day4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class PassportValidator {
    public static void main(String[] args) throws Exception {
        Set<String> validFields = new HashSet<>();
        validFields.add("byr");
        validFields.add("iyr");
        validFields.add("eyr");
        validFields.add("hgt");
        validFields.add("hcl");
        validFields.add("ecl");
        validFields.add("pid");

        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        Set<String> personFields = new HashSet<>(validFields);
        int validCount = 0;

        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                if (personFields.isEmpty()) {
                    validCount++;
                }

                personFields = new HashSet<>(validFields);
                continue;
            }

            String[] keyValue = line.split(" ");

            for (String keyValuePair : keyValue) {
                String[] mapping = keyValuePair.split(":");

                if (personFields.contains(mapping[0])) {
                    personFields.remove(mapping[0]);
                }
            }
        }

        if (personFields.isEmpty()) {
            validCount++;
        }

        System.out.println("Valid count : " + validCount);
    }
}
