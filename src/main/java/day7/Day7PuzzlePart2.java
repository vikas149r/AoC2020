package day7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day7PuzzlePart2 {
    private static final Map<String, Set<BagContainer>> bagToContainer = new HashMap<>();

    static class BagContainer {
        private final String bagName;
        private final Integer bagCount;

        public BagContainer(String bagName, Integer bagCount) {
            this.bagName = bagName;
            this.bagCount = bagCount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BagContainer that = (BagContainer) o;
            return Objects.equals(bagName, that.bagName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(bagName);
        }

        @Override
        public String toString() {
            return "BagContainer{" +
                    "bagName='" + bagName + '\'' +
                    ", bagCount=" + bagCount +
                    '}';
        }
    }

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
                if (separateBag.contains(noBagsString)) {
                    break;
                }

                String bagCountString = separateBag.substring(0, separateBag.indexOf(" ")).trim();
                Integer bagCount = Integer.parseInt(bagCountString);
                String actualBag = separateBag.substring(separateBag.indexOf(" ") + 1);
                actualBag = actualBag.substring(0, actualBag.indexOf(bagString));

                Set<BagContainer> bagContainerSet = bagToContainer.get(containerBag);

                if (bagContainerSet == null || bagContainerSet.isEmpty()) {
                    bagContainerSet = new HashSet<>();
                    bagToContainer.put(containerBag, bagContainerSet);
                }

                bagContainerSet.add(new BagContainer(actualBag, bagCount));
            }
        }

        AtomicInteger totalCount = new AtomicInteger();
        retrieveBags(totalCount, "shiny gold");
        System.out.println(totalCount);
    }

    private static void retrieveBags(AtomicInteger totalCount, String bagName) {
        Set<BagContainer> bagContainers = bagToContainer.get(bagName);

        if (bagContainers == null) {
            return;
        }

        for (BagContainer container : bagContainers) {
            AtomicInteger bagCount = new AtomicInteger();
            retrieveBags(bagCount, container.bagName);
            totalCount.addAndGet(container.bagCount * bagCount.get());
            totalCount.addAndGet(container.bagCount);
        }
    }
}
