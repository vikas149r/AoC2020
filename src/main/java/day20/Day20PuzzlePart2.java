package day20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day20PuzzlePart2 {
    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        Pixel pixel = null;
        Set<Pixel> pixels = new HashSet<>();

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

        getEdgePixels(pixels);
        System.out.println(pixels);

//        Pixel[][] image = createOutline(cornerPixels, edgePixels, remainingPixels);

        Pixel cornerPixel = pixels.iterator().next();

        if (cornerPixel.rightPixel != null) {
            Pixel rightPixel = cornerPixel.rightPixel;

            if (rightPixel.leftPixel != cornerPixel && rightPixel.rightPixel == cornerPixel) {
                if (rightPixel.topPixel != null) {
                    Pixel topPixel = rightPixel.topPixel;
                    rightPixel.flipPixel();
                    topPixel.flipPixel();
                } else if (rightPixel.bottomPixel != null) {
                    Pixel bottomPixel = rightPixel.bottomPixel;
                    rightPixel.flipPixel();
                }

            }
        }

        System.out.println();
        System.out.println(pixels);
    }

    public static Pixel[][] createOutline(List<Pixel> cornerPixels, List<Pixel> edgePixels, List<Pixel> remainingPixels) {
        int imageSize = (edgePixels.size() / 4) + 2;
        Pixel[][] image = new Pixel[imageSize][imageSize];

        int pixelCount = 0;
        Pixel cornerPixel = cornerPixels.iterator().next();
        Cell currentCell = new Cell(0, 0);
        image[currentCell.x][currentCell.y] = cornerPixel;
        pixelCount++;

        while (pixelCount < (imageSize * imageSize)) {
            populateNeighboringPixels(cornerPixel, image, currentCell);
            pixelCount++;
        }

        return image;
    }

    public static Pixel populateNeighboringPixels(Pixel pixel, Pixel[][] image, Cell currentCell) {
        if (pixel.rightPixel != null) {

        }

        if (pixel.leftPixel != null) {

        }

        if (pixel.bottomPixel != null) {

        }

        if (pixel.topPixel != null) {

        }

        return null;
    }

    public static void getEdgePixels(Set<Pixel> pixels) {
        for (Pixel firstPixel : pixels) {
            for (Pixel secondPixel : pixels) {
                if (secondPixel.equals(firstPixel)) {
                    continue;
                }

                if (edgeMatches(firstPixel, secondPixel)) {
                    continue;
                }
            }
        }
    }

    static void print(Pixel firstPixel, Pixel secondPixel, String title, String output) {
        System.out.println(firstPixel.tileName + " " + secondPixel.tileName + " - " + title);
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
        }
        /*System.out.println(firstPixel.getLayer(0).top + " " + firstPixel.getLayer(0).right + " " + firstPixel.getLayer(0).bottom + " " + firstPixel.getLayer(0).left);
        System.out.println(secondPixel.getLayer(0).top + " " + secondPixel.getLayer(0).right + " " + secondPixel.getLayer(0).bottom + " " + secondPixel.getLayer(0).left);*/
    }

    public static boolean edgeMatches(Pixel firstPixel, Pixel secondPixel) {
        String output = matches(firstPixel, secondPixel);

        if (!output.equals("None")) {
            print(firstPixel, secondPixel, "Plain", output);
            return true;
        }

        secondPixel.rotatePixel();
        output = matches(firstPixel, secondPixel);

        if (!output.equals("None")) {
            print(firstPixel, secondPixel, "First rotate", output);
            return true;
        }

        secondPixel.rotatePixel();
        output = matches(firstPixel, secondPixel);

        if (!output.equals("None")) {
            print(firstPixel, secondPixel, "Second rotate", output);
            return true;
        }

        secondPixel.rotatePixel();
        output = matches(firstPixel, secondPixel);

        if (!output.equals("None")) {
            print(firstPixel, secondPixel, "Third rotate", output);
            return true;
        }

        secondPixel.flipPixel();
        output = matches(firstPixel, secondPixel);

        if (!output.equals("None")) {
            print(firstPixel, secondPixel, "Flip", output);
            return true;
        }

        secondPixel.rotatePixel();
        output = matches(firstPixel, secondPixel);

        if (!output.equals("None")) {
            print(firstPixel, secondPixel, "Flip & rotate", output);
            return true;
        }

        secondPixel.rotatePixel();
        output = matches(firstPixel, secondPixel);

        if (!output.equals("None")) {
            print(firstPixel, secondPixel, "Flip & second rotate", output);
            return true;
        }

        secondPixel.rotatePixel();
        output = matches(firstPixel, secondPixel);

        if (!output.equals("None")) {
            print(firstPixel, secondPixel, "Flip & third rotate", output);
            return true;
        }

        return false;
    }

    public static String matches(Pixel firstPixel, Pixel secondPixel) {
        if (firstPixel.getLayer(0).right.equals(secondPixel.getLayer(0).left)) {
            firstPixel.rightPixel = secondPixel;
            return "Right";
        }

        if (firstPixel.getLayer(0).left.equals(secondPixel.getLayer(0).right)) {
            firstPixel.leftPixel = secondPixel;
            return "Left";
        }

        if (firstPixel.getLayer(0).top.equals(secondPixel.getLayer(0).bottom)) {
            firstPixel.topPixel = secondPixel;
            return "Top";
        }

        if (firstPixel.getLayer(0).bottom.equals(secondPixel.getLayer(0).top)) {
            firstPixel.bottomPixel = secondPixel;
            return "Bottom";
        }

        return "None";
    }

    static class Cell {
        int x = 0;
        int y = 0;

        public Cell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cell cell = (Cell) o;
            return x == cell.x &&
                    y == cell.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Cell{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
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

        Pixel rightPixel;
        Pixel leftPixel;
        Pixel topPixel;
        Pixel bottomPixel;

        char[][] twoDimensionalArray;

        public Pixel(String tileName, int noOfLayers) {
            this.tileName = tileName;
            this.noOfLayers = noOfLayers;

            for (int i = 0; i < noOfLayers; i++) {
                layers.add(new Layer());
            }

            twoDimensionalArray = new char[noOfLayers * 2][noOfLayers * 2];
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
                    ((rightPixel != null) ? ", rightPixel=" + rightPixel.tileName : "") +
                    ((leftPixel != null) ? ", leftPixel=" + leftPixel.tileName : "") +
                    ((topPixel != null) ? ", topPixel=" + topPixel.tileName : "") +
                    ((bottomPixel != null) ? ", bottomPixel=" + bottomPixel.tileName : "") +
                    '}';
        }
    }


}
