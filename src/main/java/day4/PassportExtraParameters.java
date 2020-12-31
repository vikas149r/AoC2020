package aoc.day4;

import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class PassportExtraParameters {
    public interface Constraints<T> {
        public boolean validate(T input);
    }

    public static class BYRConstraints implements Constraints<String> {
        @Override
        public boolean validate(String value) {
            int input = 0;

            try {
                input = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("BYR : " + value);
                return false;
            }

            return (input >= 1920 && input <= 2002);
        }
    }

    public static class IYRConstraints implements Constraints<String> {
        @Override
        public boolean validate(String value) {

            int input = 0;

            try {
                input = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("IYR : " + value);
                return false;
            }

            return (input >= 2010 && input <= 2020);
        }
    }

    public static class EYRConstraints implements Constraints<String> {
        @Override
        public boolean validate(String value) {
            int input = 0;

            try {
                input = Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("EYR : " + value);
                return false;
            }

            return (input >= 2020 && input <= 2030);
        }
    }

    public static class HGTConstraints implements Constraints<String> {
        @Override
        public boolean validate(String input) {
            String unit = input.substring(input.length() - 2);
            String count = input.substring(0, input.length() - 2);
            int measure;
            switch (unit) {
                case "in":
                    measure = Integer.parseInt(count);
                    return measure >= 59 && measure <= 76;
                case "cm":
                    measure = Integer.parseInt(count);
                    return measure >= 150 && measure <= 193;
                default:
                    return false;
            }
        }
    }

    public static class HCLConstraints implements Constraints<String> {
        @Override
        public boolean validate(String input) {
            return Pattern.matches("#{1}[0-9a-f]{6}", input);
        }
    }

    public static class ECLConstraints implements Constraints<String> {
        @Override
        public boolean validate(String input) {
            switch (input) {
                case "amb":
                    return true;
                case "blu":
                    return true;
                case "brn":
                    return true;
                case "gry":
                    return true;
                case "grn":
                    return true;
                case "hzl":
                    return true;
                case "oth":
                    return true;
                default:
                    return false;
            }
        }
    }

    public static class PIDConstraints implements Constraints<String> {
        @Override
        public boolean validate(String input) {
            boolean isANumber = false;
            try {
                Integer.parseInt(input);
                isANumber = true;
            } catch (NumberFormatException e) {
                isANumber = false;
            }

            return input.toCharArray().length == 9 && isANumber;
        }
    }

    public static void main(String[] args) throws Exception {
        Set<String> validFields = new HashSet<>();
        validFields.add("byr");
        validFields.add("iyr");
        validFields.add("eyr");
        validFields.add("hgt");
        validFields.add("hcl");
        validFields.add("ecl");
        validFields.add("pid");

        Map<String, Constraints> constraintsMap = new HashMap<>();

        constraintsMap.put("byr", new BYRConstraints());
        constraintsMap.put("iyr", new IYRConstraints());
        constraintsMap.put("eyr", new EYRConstraints());
        constraintsMap.put("hgt", new HGTConstraints());
        constraintsMap.put("hcl", new HCLConstraints());
        constraintsMap.put("ecl", new ECLConstraints());
        constraintsMap.put("pid", new PIDConstraints());

        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        Set<String> personFields = new HashSet<>(validFields);
        int validCount = 0;
        int records = 0;

        while ((line = reader.readLine()) != null) {
            if (line.equals("")) {
                records++;
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
                    boolean isValid = constraintsMap.get(mapping[0]).validate(mapping[1]);

                    if (isValid) {
                        personFields.remove(mapping[0]);
                    }
                }
            }
        }

        records++;

        if (personFields.isEmpty()) {
            validCount++;
        }

        System.out.println("Records : " + records);
        System.out.println("Valid count : " + validCount);
    }
}
