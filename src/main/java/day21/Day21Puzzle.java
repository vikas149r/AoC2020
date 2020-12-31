package aoc.day21;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day21Puzzle {
    static Map<String, List<List<String>>> allergenToIngredients = new LinkedHashMap<>();

    public static void main(String[] args) throws Exception {
        Map<String, Set<String>> allergenValues = new LinkedHashMap<>();
        Map<Set<String>, String> reverseAllergenValues = new LinkedHashMap<>();
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        List<String> ingredientsList = new LinkedList<>();

        while ((line = reader.readLine()) != null) {
            String ingredients = line.substring(0, line.indexOf("(")).trim();
            String containsString = "(contains ";
            String allergenString = line.substring(line.indexOf(containsString) + containsString.length(), line.indexOf(")"));

            ingredientsList.add(ingredients);

            String[] allergens = allergenString.split(",");

            for (String allergen : allergens) {
                allergenToIngredients.merge(allergen.trim(), Stream.of(Arrays.asList(ingredients.split(" "))).collect(Collectors.toList()),
                        (k, v) -> { k.add(Arrays.asList(ingredients.split(" "))); return k; });
            }
        }

        for (String allergen : allergenToIngredients.keySet()) {
            List<List<String>> ingredients = allergenToIngredients.get(allergen);

            Collections.sort(ingredients, new Comparator<List<String>>() {
                @Override
                public int compare(List<String> o1, List<String> o2) {
                    return o2.size() - o1.size();
                }
            });

            Set<String> firstIngredientSet = new LinkedHashSet<>(ingredients.get(0));

            for (int i = 1; i < ingredients.size(); i++) {
                firstIngredientSet.retainAll(new LinkedHashSet<>(ingredients.get(i)));
            }

            allergenValues.put(allergen, firstIngredientSet);
            reverseAllergenValues.put(firstIngredientSet, allergen);
        }

        System.out.println(allergenValues);
        System.out.println(reverseAllergenValues);
        Map<String, String> allergenMapping = new LinkedHashMap<>();

        while (allergenMapping.size() != allergenValues.size()) {
            Set<Set<String>> keys = new HashSet(reverseAllergenValues.keySet());
            for (Set<String> key : keys) {
                if (key.size() == 1) {
                    if (allergenMapping.containsKey(reverseAllergenValues.get(key))) {
                        continue;
                    }

                    String ingredient = key.iterator().next();
                    allergenMapping.put(reverseAllergenValues.get(key), ingredient);
                    allergenValues.values().forEach(set -> {
                        if (set.equals(key)) {
                            return;
                        }

                        set.remove(ingredient);
                    });
                    break;
                }
            }

            reverseAllergenValues.clear();

            for (Map.Entry<String, Set<String>> entry : allergenValues.entrySet()) {
                reverseAllergenValues.put(entry.getValue(), entry.getKey());
            }
        }

        System.out.println(allergenMapping);
        int nonAllergicIngredients = 0;

        for (String ingredientString : ingredientsList) {
            String[] ingredients = ingredientString.split(" ");

            for (String ingredient : ingredients) {
                if (allergenMapping.containsValue(ingredient)) {
                    continue;
                }

                nonAllergicIngredients++;
            }
        }

        System.out.println(nonAllergicIngredients);
//        System.out.println(allergenToIngredients);
    }
}
