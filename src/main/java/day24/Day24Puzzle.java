package day24;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day24Puzzle {
    static List<String> directions = Stream.of("e", "w", "se", "sw", "ne", "nw").collect(Collectors.toList());

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        Set<String> tilesToBeTurned = new HashSet<>();

        while ((line = reader.readLine()) != null) {
            int x = 0, y = 0;

            for (int i = 0; i < line.length(); i++) {
                StringBuilder builder = new StringBuilder();
                char character = line.charAt(i);
                builder.append(character);

                if (character == 's' || character == 'n') {
                    i++;
                    builder.append(line.charAt(i));
                }

                switch (builder.toString()) {
                    case "e":
                        x = x + 2;
                        break;
                    case "w":
                        x = x - 2;
                        break;
                    case "sw":
                        x = x - 1;
                        y = y - 1;
                        break;
                    case "se":
                        x = x + 1;
                        y = y - 1;
                        break;
                    case "nw":
                        x = x - 1;
                        y = y + 1;
                        break;
                    case "ne":
                        x = x + 1;
                        y = y + 1;
                        break;
                }
            }

            String tile = x + "," + y;
            if (tilesToBeTurned.contains(tile)) {
                tilesToBeTurned.remove(tile);
            } else {
                tilesToBeTurned.add(tile);
            }
        }

        System.out.println(tilesToBeTurned.size());

        int count = 1;

        Set<String> newTilesToBeTurned = new HashSet<>(tilesToBeTurned);
        while (count <= 100) {
            Set<String> tempTilesToBeTurned = new HashSet<>();
            tempTilesToBeTurned.addAll(getTilesToBeTurned(newTilesToBeTurned));
            newTilesToBeTurned = tempTilesToBeTurned;
            count++;
        }

        System.out.println(newTilesToBeTurned.size());
    }

    public static Set<String> getTilesToBeTurned(Set<String> current) {
        Set<String> tilesToBeTurned = new HashSet<>(current);
        int x = 0, y = 0;
        performTilesTurningComputation(tilesToBeTurned, current, x, y, 0, new HashSet<String>());
        return tilesToBeTurned;
    }

    public static void performTilesTurningComputation(Set<String> tilesToBeTurned, Set<String> current, int x, int y, int numberOfWhiteLayers, Set<String> traversedNodes) {
        if (numberOfWhiteLayers == 10) {
            return;
        }

        String currentTile = x + "," + y;

        if (traversedNodes.contains(currentTile)) {
            return;
        }

        int adjacentBlackTiles = getAdjacentBlackTiles(current, x, y);
        boolean isCurrentTileBlack = current.contains(currentTile);

        if (isCurrentTileBlack) {
            if (adjacentBlackTiles == 0 || adjacentBlackTiles > 2) {
                tilesToBeTurned.remove(currentTile);
            }
        } else {
            if (adjacentBlackTiles == 2) {
                tilesToBeTurned.add(currentTile);
            }
        }

        traversedNodes.add(currentTile);

        for (String direction : directions) {
            int a = x, b = y;
            switch (direction) {
                case "e":
                    a = x + 2;
                    break;
                case "w":
                    a = x - 2;
                    break;
                case "sw":
                    a = x - 1;
                    b = y - 1;
                    break;
                case "se":
                    a = x + 1;
                    b = y - 1;
                    break;
                case "nw":
                    a = x - 1;
                    b = y + 1;
                    break;
                case "ne":
                    a = x + 1;
                    b = y + 1;
                    break;
            }

            performTilesTurningComputation(tilesToBeTurned, current, a, b, (tilesToBeTurned.contains(currentTile) ? 0 : numberOfWhiteLayers + 1), traversedNodes);
        }
    }

    public static int getAdjacentBlackTiles(Set<String> currentBlackTiles, int x, int y) {
        Set<String> adjacentTiles = new HashSet<>();

        for (String direction : directions) {
            String tile = null;

            switch (direction) {
                case "e":
                    tile = (x + 2) + "," + y;
                    break;
                case "w":
                    tile = (x - 2) + "," + y;
                    break;
                case "sw":
                    tile = (x - 1) + "," + (y - 1);
                    break;
                case "se":
                    tile = (x + 1) + "," + (y - 1);
                    break;
                case "nw":
                    tile = (x - 1) + "," + (y + 1);
                    break;
                case "ne":
                    tile = (x + 1) + "," + (y + 1);
                    break;
            }

            if (currentBlackTiles.contains(tile)) {
                adjacentTiles.add(tile);
            }
        }

        return adjacentTiles.size();
    }
}
