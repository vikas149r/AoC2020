package day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Day7Puzzle {
    private static final Map<String, Set<String>> bagToContainer = new HashMap<>();

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        final String containString = "contain ";
        final String noBagsString = "no other bags";
        final String bagString = " bag";

        String line;

        while ((line = reader.readLine()) != null) {
            String containerBag = line.substring(0, line.indexOf(containString)).trim();
            containerBag = containerBag.substring(0, containerBag.indexOf(bagString)).trim();
            String bags = line.substring(line.indexOf(containString) + containString.length());

            String[] separateBags = bags.split(", ");

            for (String separateBag : separateBags) {
                if (separateBag.equals(noBagsString)) {
                    break;
                }

                String actualBag = separateBag.substring(separateBag.indexOf(" ") + 1);
                actualBag = actualBag.substring(0, actualBag.indexOf(bagString));

                Set<String> containerBags = bagToContainer.get(actualBag);

                if (containerBags == null) {
                    containerBags = new HashSet<>();
                    bagToContainer.put(actualBag, containerBags);
                }

                containerBags.add(containerBag);
            }
        }

//        System.out.println(bagToContainer);
        Set<String> actualContainers = new HashSet<>();
        retrieveContainers("shiny gold", actualContainers);
        System.out.println(actualContainers.size());
    }

    private static void retrieveContainers(String bagType, Set<String> actualContainers) {
        Set<String> containers = bagToContainer.get(bagType);

        if (containers == null || containers.isEmpty()) {
            return;
        }

        actualContainers.addAll(containers);

        for (String container : containers) {
            retrieveContainers(container, actualContainers);
        }
    }
}
