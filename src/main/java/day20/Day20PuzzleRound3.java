package day20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day20PuzzleRound3 {
    static Map<String, Pixel> nameToPixel = new HashMap<>();
    static Map<String, Cell> nameToCell = new HashMap<>();
    static Set<Pixel> pixels = new HashSet<>();

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        Pixel pixel = null;

        int noOfLayers = 5;
        int row = 0;

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                pixels.add(pixel);
                row = 0;
                continue;
            }

            if (line.startsWith("Tile")) {
                pixel = new Pixel(line.replace(":", ""), noOfLayers);
                continue;
            }

            char[] rowCharacters = line.toCharArray();

            for (int i = 0; i < rowCharacters.length; i++) {
                pixel.twoDimensionalArray[row][i] = rowCharacters[i];
            }

            row++;
        }

        pixels.add(pixel);
        pixels.forEach(p -> p.parseTwoDimensionalArray());
        System.out.println(pixels.size());

        for (Pixel imagePixel : pixels) {
            Cell cell = new Cell();
            cell.tileName = imagePixel.tileName;
            nameToCell.put(imagePixel.tileName, cell);
        }

        Pixel firstPixel = pixels.iterator().next();

        /*char[][] array = firstPixel.getTwoDimensionalArray();
        System.out.println(firstPixel.tileName);
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }*/

        Set<Pixel> originPixels = new HashSet<>();
        match(firstPixel, originPixels);
        startMatching(firstPixel, originPixels);

        System.out.println(nameToCell);

        Cell firstCell = nameToCell.values().iterator().next();
        Set<String> tilesPopulated = new HashSet<>();
        tilesPopulated.add(firstCell.tileName);
        populateCoordinates(firstCell, tilesPopulated);

        int maxX = -20, minX = -20, maxY = -20, minY = -20;

        for (Cell cell : nameToCell.values()) {
            if (minX == -20 || cell.x < minX) {
                minX = cell.x;
            }

            if (maxX == -20 || cell.x > maxX) {
                maxX = cell.x;
            }

            if (minY == -20 || cell.y < minY) {
                minY = cell.y;
            }

            if (maxY == -20 || cell.y > maxY) {
                maxY = cell.y;
            }
        }

        final int minimumX = minX;
        final int minimumY = minY;
        nameToCell.values().stream().forEach(cell -> {
            cell.x = cell.x - minimumX;
            cell.y = cell.y - minimumY;

            if (cell.rightTile != null) {
                nameToPixel.putIfAbsent(cell.rightTile.tileName, cell.rightTile);
            }

            if (cell.leftTile != null) {
                nameToPixel.putIfAbsent(cell.leftTile.tileName, cell.leftTile);
            }

            if (cell.topTile != null) {
                nameToPixel.putIfAbsent(cell.topTile.tileName, cell.topTile);
            }

            if (cell.bottomTile != null) {
                nameToPixel.putIfAbsent(cell.bottomTile.tileName, cell.bottomTile);
            }
        });
        /*System.out.println((maxX - minX) + " - " + (maxY - minY));
        System.out.println(nameToCell);*/

        char[][] image = new char[(maxX - minX + 1) * 8][(maxY - minY + 1) * 8];

        nameToCell.values().stream().forEach(cell -> {
            Pixel currentPixel = nameToPixel.get(cell.tileName);
            char[][] array = currentPixel.getTwoDimensionalArray();

            int xCounter = 8 * cell.x, yCounter = 8 * cell.y;

            for (int y = yCounter; y < yCounter + 8; y++) {
                for (int x = xCounter; x < xCounter + 8; x++) {
                    image[y][x] = array[y - yCounter + 1][x - xCounter + 1];
                }
            }
        });

        int hashCount = 0;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                if (image[i][j] == '#') {
                    hashCount++;
                }
            }
        }

        System.out.println(hashCount);
        flipRotateAndFindMonsters(image);
    }

    static void flipRotateAndFindMonsters(char[][] image) {
        int monsters = findMonsters(image);

        if (monsters != 0) {
            System.out.println("Plain : " + monsters);
            return;
        }

        List<Layer> layers = parseTwoDimensionalArray(image);

        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).rotate();
        }

        char[][] newImage = getTwoDimensionalArray(layers);
        monsters = findMonsters(newImage);

        if (monsters != 0) {
            System.out.println("First rotate : " + monsters);
            return;
        }

        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).rotate();
        }

        newImage = getTwoDimensionalArray(layers);
        monsters = findMonsters(newImage);

        if (monsters != 0) {
            System.out.println("Second rotate : " + monsters);
            return;
        }

        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).rotate();
        }

        newImage = getTwoDimensionalArray(layers);
        monsters = findMonsters(newImage);

        if (monsters != 0) {
            System.out.println("Third rotate : " + monsters);
            return;
        }

        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).flip();
        }

        newImage = getTwoDimensionalArray(layers);
        monsters = findMonsters(newImage);

        if (monsters != 0) {
            System.out.println("Flip : " + monsters);
            return;
        }

        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).rotate();
        }

        newImage = getTwoDimensionalArray(layers);
        monsters = findMonsters(newImage);

        if (monsters != 0) {
            System.out.println("Flip and rotate : " + monsters);
            return;
        }

        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).rotate();
        }

        newImage = getTwoDimensionalArray(layers);
        monsters = findMonsters(newImage);

        if (monsters != 0) {
            System.out.println("Flip and second rotate : " + monsters);
            return;
        }

        for (int i = 0; i < layers.size(); i++) {
            layers.get(i).rotate();
        }

        newImage = getTwoDimensionalArray(layers);
        monsters = findMonsters(newImage);

        if (monsters != 0) {
            System.out.println("Flip and third rotate : " + monsters);
            return;
        }
    }

    static int findMonsters(char[][] image) {
        int monsterCount = 0;

        for (int i = 1; i < image.length - 1; i++) {
            for (int j = 0; j <= image.length - 20; j++) {
                if (image[i][j] == '#' && image[i][j + 5] == '#' && image[i][j + 6] == '#' && image[i][j + 11] == '#'
                        && image[i][j + 12] == '#' && image[i][j + 17] == '#' && image[i][j + 18] == '#'
                        && image[i][j + 19] == '#') {
                    if (image[i - 1][j + 18] == '#' && image[i + 1][j + 1] == '#' && image[i + 1][j + 4] == '#'
                            && image[i + 1][j + 7] == '#' && image[i + 1][j + 10] == '#' && image[i + 1][j + 13] == '#'
                            && image[i + 1][j + 16] == '#') {
                        monsterCount++;
                    }
                }
            }
        }

        return monsterCount;
    }

    static void populateCoordinates(Cell cell, Set<String> tilesPopulated) {
        if (cell.rightTile != null && !tilesPopulated.contains(cell.rightTile.tileName)) {
            nameToCell.get(cell.rightTile.tileName).x = cell.x + 1;
            nameToCell.get(cell.rightTile.tileName).y = cell.y;
        }

        if (cell.leftTile != null && !tilesPopulated.contains(cell.leftTile.tileName)) {
            nameToCell.get(cell.leftTile.tileName).x = cell.x - 1;
            nameToCell.get(cell.leftTile.tileName).y = cell.y;
        }

        if (cell.topTile != null && !tilesPopulated.contains(cell.topTile.tileName)) {
            nameToCell.get(cell.topTile.tileName).x = cell.x;
            nameToCell.get(cell.topTile.tileName).y = cell.y - 1;
        }

        if (cell.bottomTile != null && !tilesPopulated.contains(cell.bottomTile.tileName)) {
            nameToCell.get(cell.bottomTile.tileName).x = cell.x;
            nameToCell.get(cell.bottomTile.tileName).y = cell.y + 1;
        }

        if (cell.rightTile != null && !tilesPopulated.contains(cell.rightTile.tileName)) {
            tilesPopulated.add(cell.rightTile.tileName);
            populateCoordinates(nameToCell.get(cell.rightTile.tileName), tilesPopulated);
        }

        if (cell.leftTile != null && !tilesPopulated.contains(cell.leftTile.tileName)) {
            tilesPopulated.add(cell.leftTile.tileName);
            populateCoordinates(nameToCell.get(cell.leftTile.tileName), tilesPopulated);
        }

        if (cell.topTile != null && !tilesPopulated.contains(cell.topTile.tileName)) {
            tilesPopulated.add(cell.topTile.tileName);
            populateCoordinates(nameToCell.get(cell.topTile.tileName), tilesPopulated);
        }

        if (cell.bottomTile != null && !tilesPopulated.contains(cell.bottomTile.tileName)) {
            tilesPopulated.add(cell.bottomTile.tileName);
            populateCoordinates(nameToCell.get(cell.bottomTile.tileName), tilesPopulated);
        }
    }

    static void startMatching(Pixel pixel, Set<Pixel> originPixels) {
        if (pixel == null) {
            return;
        }

        if (originPixels.contains(pixel)) {
            return;
        }

        Cell cell = nameToCell.get(pixel.tileName);

        match(cell.rightTile, originPixels);
        match(cell.leftTile, originPixels);
        match(cell.topTile, originPixels);
        match(cell.bottomTile, originPixels);

        originPixels.add(pixel);

        startMatching(cell.rightTile, originPixels);
        startMatching(cell.leftTile, originPixels);
        startMatching(cell.topTile, originPixels);
        startMatching(cell.bottomTile, originPixels);
    }

    static void match(Pixel pixel, Set<Pixel> originPixels) {
        if (pixel != null) {
            performMatches(pixel, originPixels);
        }
    }

    static void performMatches(Pixel firstPixel, Set<Pixel> originPixels) {
        for (Pixel secondPixel : pixels) {
            if (originPixels.contains(secondPixel)) {
                continue;
            }

            if (secondPixel.equals(firstPixel)) {
                continue;
            }

            if (edgeMatches(firstPixel, secondPixel) != null) {
                continue;
            }
        }
    }

    static void print(Pixel firstPixel, Pixel secondPixel, String title, String output) {
        /*System.out.println(firstPixel.tileName + " " + secondPixel.tileName + " - " + title);
        switch (output) {
            case "Right":
                System.out.println(firstPixel.getLayer(0).right);
                System.out.println(secondPixel.getLayer(0).left);
                break;
            case "Left":
                System.out.println(firstPixel.getLayer(0).left);
                System.out.println(secondPixel.getLayer(0).right);
                break;
            case "Top":
                System.out.println(firstPixel.getLayer(0).top);
                System.out.println(secondPixel.getLayer(0).bottom);
                break;
            case "Bottom":
                System.out.println(firstPixel.getLayer(0).bottom);
                System.out.println(secondPixel.getLayer(0).top);
                break;
        }*/
        /*System.out.println(firstPixel.getLayer(0).top + " " + firstPixel.getLayer(0).right + " " + firstPixel.getLayer(0).bottom + " " + firstPixel.getLayer(0).left);
        System.out.println(secondPixel.getLayer(0).top + " " + secondPixel.getLayer(0).right + " " + secondPixel.getLayer(0).bottom + " " + secondPixel.getLayer(0).left);*/
    }

    public static Pixel edgeMatches(Pixel firstPixel, Pixel secondPixel) {
        String output = matches(firstPixel, secondPixel);

        if (!output.equals("None")) {
            print(firstPixel, secondPixel, "Plain", output);
            return secondPixel;
        }

        Pixel pixel = secondPixel.copy();
        pixel.rotatePixel();
        output = matches(firstPixel, pixel);

        if (!output.equals("None")) {
            print(firstPixel, pixel, "First rotate", output);
            return pixel;
        }

        pixel.rotatePixel();
        output = matches(firstPixel, pixel);

        if (!output.equals("None")) {
            print(firstPixel, pixel, "Second rotate", output);
            return pixel;
        }

        pixel.rotatePixel();
        output = matches(firstPixel, pixel);

        if (!output.equals("None")) {
            print(firstPixel, pixel, "Third rotate", output);
            return pixel;
        }

        pixel.flipPixel();
        output = matches(firstPixel, pixel);

        if (!output.equals("None")) {
            print(firstPixel, pixel, "Flip", output);
            return pixel;
        }

        pixel.rotatePixel();
        output = matches(firstPixel, pixel);

        if (!output.equals("None")) {
            print(firstPixel, pixel, "Flip & rotate", output);
            return pixel;
        }

        pixel.rotatePixel();
        output = matches(firstPixel, pixel);

        if (!output.equals("None")) {
            print(firstPixel, pixel, "Flip & second rotate", output);
            return pixel;
        }

        pixel.rotatePixel();
        output = matches(firstPixel, pixel);

        if (!output.equals("None")) {
            print(firstPixel, pixel, "Flip & third rotate", output);
            return pixel;
        }

        return null;
    }

    public static String matches(Pixel firstPixel, Pixel secondPixel) {
        Cell pixelCell = nameToCell.get(firstPixel.tileName);

        if (firstPixel.getLayer(0).right.equals(secondPixel.getLayer(0).left)) {
            pixelCell.rightTile = secondPixel;
            return "Right";
        }

        if (firstPixel.getLayer(0).left.equals(secondPixel.getLayer(0).right)) {
            pixelCell.leftTile = secondPixel;
            return "Left";
        }

        if (firstPixel.getLayer(0).top.equals(secondPixel.getLayer(0).bottom)) {
            pixelCell.topTile = secondPixel;
            return "Top";
        }

        if (firstPixel.getLayer(0).bottom.equals(secondPixel.getLayer(0).top)) {
            pixelCell.bottomTile = secondPixel;
            return "Bottom";
        }

        return "None";
    }

    public static List<Layer> parseTwoDimensionalArray(char[][] twoDimensionalArray) {
        List<Layer> layers = new ArrayList<>();
        int noOfLayers = twoDimensionalArray.length / 2;

        for (int i = 0; i < noOfLayers; i++) {
            layers.add(new Layer());
        }

        int arrayLength = twoDimensionalArray.length;

        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                int xLayer = (i < noOfLayers) ? i : arrayLength - 1 - i;
                int yLayer = (j < noOfLayers) ? j : arrayLength - 1 - j;

                int layer = (xLayer < yLayer) ? xLayer : yLayer;

                boolean top = i < noOfLayers;
                boolean left = j < noOfLayers;

                char character = twoDimensionalArray[i][j];
                if (left) {
                    if (top) {
                        if (j >= i) {
                            if (j == i) {
                                layers.get(layer).addLeft(character);
                            }

                            layers.get(layer).addTop(character);
                        } else {
                            layers.get(layer).addLeft(character);
                        }
                    } else {
                        if (j >= arrayLength - 1 - i) {
                            if (j == arrayLength - 1 - i) {
                                layers.get(layer).addLeft(character);
                            }

                            layers.get(layer).addBottom(character);
                        } else {
                            layers.get(layer).addLeft(character);
                        }
                    }
                } else {
                    if (top) {
                        if (arrayLength - 1 - j >= i) {
                            if (arrayLength - 1 - j == i) {
                                layers.get(layer).addRight(character);
                            }

                            layers.get(layer).addTop(character);
                        } else {
                            layers.get(layer).addRight(character);
                        }
                    } else {
                        if (arrayLength - 1 - j >= arrayLength - 1 - i) {
                            if (arrayLength - 1 - j == arrayLength - 1 - i) {
                                layers.get(layer).addRight(character);
                            }

                            layers.get(layer).addBottom(character);
                        } else {
                            layers.get(layer).addRight(character);
                        }
                    }
                }
            }
        }

        return layers;
    }

    public static char[][] getTwoDimensionalArray(List<Layer> layers) {
        int noOfLayers = layers.size();
        int arrayLength = noOfLayers * 2;
        char[][] array = new char[arrayLength][arrayLength];

        for (int i = 0; i < arrayLength; i++) {
            for (int j = 0; j < arrayLength; j++) {
                int xLayer = (i < noOfLayers) ? i : arrayLength - 1 - i;
                int yLayer = (j < noOfLayers) ? j : arrayLength - 1 - j;

                int layer = (xLayer < yLayer) ? xLayer : yLayer;

                boolean top = i < layers.size();
                boolean left = j < layers.size();

                if (left) {
                    if (top) {
                        if (j >= i) {
                            array[i][j] = layers.get(layer).top.get(j - layer);
                        } else {
                            array[i][j] = layers.get(layer).left.get(i - layer);
                        }
                    } else {
                        if (j >= arrayLength - 1 - i) {
                            array[i][j] = layers.get(layer).bottom.get(j - layer);
                        } else {
                            array[i][j] = layers.get(layer).left.get(i - layer);
                        }
                    }
                } else {
                    if (top) {
                        if (arrayLength - 1 - j >= i) {
                            array[i][j] = layers.get(layer).top.get(j - layer);
                        } else {
                            array[i][j] = layers.get(layer).right.get(i - layer);
                        }
                    } else {
                        if (arrayLength - 1 - j >= arrayLength - 1 - i) {
                            array[i][j] = layers.get(layer).bottom.get(j - layer);
                        } else {
                            array[i][j] = layers.get(layer).right.get(i - layer);
                        }
                    }
                }
            }
        }

        return array;
    }

    static class Cell {
        String tileName;
        Pixel rightTile = null, leftTile = null, topTile = null, bottomTile = null;
        int x = 0;
        int y = 0;

        public int getNeighborCount() {
            int count = 0;

            if (rightTile != null) {
                count++;
            }

            if (leftTile != null) {
                count++;
            }

            if (topTile != null) {
                count++;
            }

            if (bottomTile != null) {
                count++;
            }

            return count;
        }

        public boolean isCenterPiece() {
            int count = getNeighborCount();
            return count == 4;
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "tileName='" + tileName + '\'' +
                    ((rightTile != null) ? ", rightTile=" + rightTile.tileName : "") +
                    ((leftTile != null) ? ", leftTile=" + leftTile.tileName : "") +
                    ((topTile != null) ? ", topTile=" + topTile.tileName : "") +
                    ((bottomTile != null) ? ", bottomTile=" + bottomTile.tileName : "") +
                    ", x=" + x +
                    ", y=" + y +
                    '}';
        }

        public boolean isEdge() {
            int count = getNeighborCount();
            return count == 3;
        }

        public boolean isCorner() {
            int count = getNeighborCount();
            return count == 2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return Objects.equals(tileName, cell.tileName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tileName);
        }

    }

    static class Layer {
        List<Character> top = new LinkedList<>();
        List<Character> bottom = new LinkedList<>();
        List<Character> left = new LinkedList<>();
        List<Character> right = new LinkedList<>();

        public void addTop(Character character) {
            top.add(character);
        }

        public void addBottom(Character character) {
            bottom.add(character);
        }

        public void addLeft(Character character) {
            left.add(character);
        }

        public void addRight(Character character) {
            right.add(character);
        }

        public void flip() {
            List<Character> temp = right;

            right = left;
            left = temp;

            Collections.reverse(top);
            Collections.reverse(bottom);
        }

        public void rotate() {
            List<Character> temp = right;
            right = new LinkedList<>(top);
            top = new LinkedList<>(left);
            left = new LinkedList<>(bottom);
            bottom = new LinkedList<>(temp);

            Collections.reverse(top);
            Collections.reverse(bottom);
        }
    }

    static class Pixel {
        String tileName;
        int noOfLayers;
        List<Layer> layers = new ArrayList<>();
        char[][] twoDimensionalArray;

        public Pixel(String tileName, int noOfLayers) {
            this.tileName = tileName;
            this.noOfLayers = noOfLayers;

            for (int i = 0; i < noOfLayers; i++) {
                layers.add(new Layer());
            }

            twoDimensionalArray = new char[noOfLayers * 2][noOfLayers * 2];
        }

        public Pixel copy() {
            Pixel pixel = new Pixel(this.tileName, this.noOfLayers);
            pixel.twoDimensionalArray = Arrays.copyOf(twoDimensionalArray, twoDimensionalArray.length);

            for (int i = 0; i < noOfLayers; i++) {
                pixel.getLayer(i).top.addAll(this.getLayer(i).top);
                pixel.getLayer(i).right.addAll(this.getLayer(i).right);
                pixel.getLayer(i).bottom.addAll(this.getLayer(i).bottom);
                pixel.getLayer(i).left.addAll(this.getLayer(i).left);
            }

            return pixel;
        }

        public void setTwoDimensionalArray(char[][] twoDimensionalArray) {
            this.twoDimensionalArray = twoDimensionalArray;
        }

        public void parseTwoDimensionalArray() {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    int xLayer = (i < noOfLayers) ? i : 9 - i;
                    int yLayer = (j < noOfLayers) ? j : 9 - j;

                    int layer = (xLayer < yLayer) ? xLayer : yLayer;

                    boolean top = i < noOfLayers;
                    boolean left = j < noOfLayers;

                    char character = twoDimensionalArray[i][j];
                    if (left) {
                        if (top) {
                            if (j >= i) {
                                if (j == i) {
                                    getLayer(layer).addLeft(character);
                                }

                                getLayer(layer).addTop(character);
                            } else {
                                getLayer(layer).addLeft(character);
                            }
                        } else {
                            if (j >= 9 - i) {
                                if (j == 9 - i) {
                                    getLayer(layer).addLeft(character);
                                }

                                getLayer(layer).addBottom(character);
                            } else {
                                getLayer(layer).addLeft(character);
                            }
                        }
                    } else {
                        if (top) {
                            if (9 - j >= i) {
                                if (9 - j == i) {
                                    getLayer(layer).addRight(character);
                                }

                                getLayer(layer).addTop(character);
                            } else {
                                getLayer(layer).addRight(character);
                            }
                        } else {
                            if (9 - j >= 9 - i) {
                                if (9 - j == 9 - i) {
                                    getLayer(layer).addRight(character);
                                }

                                getLayer(layer).addBottom(character);
                            } else {
                                getLayer(layer).addRight(character);
                            }
                        }
                    }
                }
            }
        }

        public char[][] getTwoDimensionalArray() {
            char[][] array = new char[10][10];

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    int xLayer = (i < noOfLayers) ? i : 9 - i;
                    int yLayer = (j < noOfLayers) ? j : 9 - j;

                    int layer = (xLayer < yLayer) ? xLayer : yLayer;

                    boolean top = i < noOfLayers;
                    boolean left = j < noOfLayers;

                    if (left) {
                        if (top) {
                            if (j >= i) {
                                array[i][j] = getLayer(layer).top.get(j - layer);
                            } else {
                                array[i][j] = getLayer(layer).left.get(i - layer);
                            }
                        } else {
                            if (j >= 9 - i) {
                                array[i][j] = getLayer(layer).bottom.get(j - layer);
                            } else {
                                array[i][j] = getLayer(layer).left.get(i - layer);
                            }
                        }
                    } else {
                        if (top) {
                            if (9 - j >= i) {
                                array[i][j] = getLayer(layer).top.get(j - layer);
                            } else {
                                array[i][j] = getLayer(layer).right.get(i - layer);
                            }
                        } else {
                            if (9 - j >= 9 - i) {
                                array[i][j] = getLayer(layer).bottom.get(j - layer);
                            } else {
                                array[i][j] = getLayer(layer).right.get(i - layer);
                            }
                        }
                    }
                }
            }

            return array;
        }

        public Layer getLayer(int i) {
            return layers.get(i);
        }

        public void flipPixel() {
            for (int i = 0; i < noOfLayers; i++) {
                layers.get(i).flip();
            }
        }

        public void rotatePixel() {
            for (int i = 0; i < noOfLayers; i++) {
                layers.get(i).rotate();
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pixel pixel = (Pixel) o;
            return Objects.equals(tileName, pixel.tileName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(tileName);
        }

        @Override
        public String toString() {
            return "Pixel{" +
                    "tileName='" + tileName + '\'' +
                    ", noOfLayers=" + noOfLayers +
                    '}';
        }
    }
}
