package day20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class Day20Puzzle {
    static class Pixel {
        String tileName;
        List<Character> top = new LinkedList<>();
        List<Character> bottom = new LinkedList<>();
        List<Character> left = new LinkedList<>();
        List<Character> right = new LinkedList<>();

        public Pixel(String tileName) {
            this.tileName = tileName;
        }

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

        public Pixel flipPixel() {
            Pixel pixel = new Pixel(tileName);
            pixel.top = new LinkedList<>(top);
            pixel.bottom = new LinkedList<>(bottom);
            pixel.left = new LinkedList<>(right);
            pixel.right = new LinkedList<>(left);

            Collections.reverse(pixel.top);
            Collections.reverse(pixel.bottom);
            return pixel;
        }

        public Pixel rotatePixel() {
            Pixel pixel = new Pixel(tileName);
            pixel.right = new LinkedList<>(top);
            pixel.bottom = new LinkedList<>(right);
            pixel.left = new LinkedList<>(bottom);
            pixel.top = new LinkedList<>(left);

            Collections.reverse(pixel.top);
            Collections.reverse(pixel.bottom);
            return pixel;
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
                    '}';
        }
    }

    public static void main(String[] args) throws Exception {
        final File inputFile = new File(Class.forName(Thread.currentThread().getStackTrace()[1].getClassName()).getResource("Input.txt").toURI().getPath());
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile)));

        String line;
        Pixel pixel = null;
        int rowCount = 0;
        Set<Pixel> pixels = new HashSet<>();

        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                pixels.add(pixel);
                rowCount = 0;
                continue;
            }

            if (line.startsWith("Tile")) {
                pixel = new Pixel(line);
                continue;
            }

            char[] characterArray = line.toCharArray();
            pixel.addLeft(characterArray[0]);
            pixel.addRight(characterArray[characterArray.length - 1]);

            if (rowCount == 0) {
                for (char character : characterArray) {
                    pixel.addTop(character);
                }
            }

            if (rowCount == characterArray.length - 1) {
                for (char character : characterArray) {
                    pixel.addBottom(character);
                }
            }

            rowCount++;
        }

        pixels.add(pixel);
        System.out.println(pixels.size());

        List<Pixel> cornerPixels = getEdgePixels(pixels, 2);
        System.out.println(cornerPixels.size());
        System.out.println(cornerPixels);
        List<Pixel> edgePixels = getEdgePixels(pixels, 3);
        System.out.println(edgePixels.size());
        System.out.println(edgePixels);
    }

    public static List<Pixel> getEdgePixels(Set<Pixel> pixels, int matchCount) {
        List<Pixel> edgePixels = new LinkedList<>();
        int matches = 0;

        for (Pixel firstPixel : pixels) {
            matches = 0;

            for (Pixel secondPixel : pixels) {
                if (secondPixel.equals(firstPixel)) {
                    continue;
                }

                if (edgeMatches(firstPixel, secondPixel)) {
                    matches++;
                    continue;
                }
            }

            /*if (matches > matchCount) {
                continue;
            }

            matches = 0;
            Pixel firstPixelRotate = firstPixel.rotatePixel();

            for (Pixel secondPixel : pixels) {
                if (secondPixel.equals(firstPixelRotate)) {
                    continue;
                }

                if (edgeMatches(firstPixelRotate, secondPixel)) {
                    matches++;
                    continue;
                }
            }

            if (matches > matchCount) {
                continue;
            }

            matches = 0;
            Pixel firstPixelRotatedTwice = firstPixel.rotatePixel().rotatePixel();

            for (Pixel secondPixel : pixels) {
                if (secondPixel.equals(firstPixelRotatedTwice)) {
                    continue;
                }

                if (edgeMatches(firstPixelRotatedTwice, secondPixel)) {
                    matches++;
                    continue;
                }
            }

            if (matches > matchCount) {
                continue;
            }

            matches = 0;
            Pixel firstPixelRotatedThrice = firstPixel.rotatePixel().rotatePixel().rotatePixel();

            for (Pixel secondPixel : pixels) {
                if (secondPixel.equals(firstPixelRotatedThrice)) {
                    continue;
                }

                if (edgeMatches(firstPixelRotatedThrice, secondPixel)) {
                    matches++;
                    continue;
                }
            }

            if (matches > matchCount) {
                continue;
            }

            matches = 0;
            Pixel firstPixelFlip = firstPixel.flipPixel();

            for (Pixel secondPixel : pixels) {
                if (secondPixel.equals(firstPixelFlip)) {
                    continue;
                }

                if (edgeMatches(firstPixelFlip, secondPixel)) {
                    matches++;
                    continue;
                }
            }

            if (matches > matchCount) {
                continue;
            }

            matches = 0;
            Pixel firstPixelFlipRotate = firstPixelFlip.rotatePixel();

            for (Pixel secondPixel : pixels) {
                if (secondPixel.equals(firstPixelFlipRotate)) {
                    continue;
                }

                if (edgeMatches(firstPixelFlipRotate, secondPixel)) {
                    matches++;
                    continue;
                }
            }

            if (matches > matchCount) {
                continue;
            }

            matches = 0;
            Pixel firstPixelFlipRotatedTwice = firstPixelFlip.rotatePixel().rotatePixel();

            for (Pixel secondPixel : pixels) {
                if (secondPixel.equals(firstPixelFlipRotatedTwice)) {
                    continue;
                }

                if (edgeMatches(firstPixelFlipRotatedTwice, secondPixel)) {
                    matches++;
                    continue;
                }
            }

            if (matches > matchCount) {
                continue;
            }

            matches = 0;
            Pixel firstPixelFlipRotatedThrice = firstPixelFlip.rotatePixel().rotatePixel().rotatePixel();

            for (Pixel secondPixel : pixels) {
                if (secondPixel.equals(firstPixelFlipRotatedThrice)) {
                    continue;
                }

                if (edgeMatches(firstPixelFlipRotatedThrice, secondPixel)) {
                    matches++;
                    continue;
                }
            }*/

            if (matches == matchCount) {
                edgePixels.add(firstPixel);
            }
        }

        return edgePixels;
    }

    public static boolean edgeMatches(Pixel firstPixel, Pixel secondPixel) {
        if (matches(firstPixel, secondPixel)) {
            return true;
        }

        Pixel secondPixelRotate = secondPixel.rotatePixel();

        if (matches(firstPixel, secondPixelRotate)) {
            return true;
        }

        Pixel secondPixelRotatedTwice = secondPixel.rotatePixel().rotatePixel();

        if (matches(firstPixel, secondPixelRotatedTwice)) {
            return true;
        }

        Pixel secondPixelRotatedThrice = secondPixel.rotatePixel().rotatePixel().rotatePixel();

        if (matches(firstPixel, secondPixelRotatedThrice)) {
            return true;
        }

        Pixel secondPixelFlip = secondPixel.flipPixel();

        if (matches(firstPixel, secondPixelFlip)) {
            return true;
        }

        Pixel secondPixelFlipRotate = secondPixelFlip.rotatePixel();

        if (matches(firstPixel, secondPixelFlipRotate)) {
            return true;
        }

        Pixel secondPixelFlipRotatedTwice = secondPixelFlip.rotatePixel().rotatePixel();

        if (matches(firstPixel, secondPixelFlipRotatedTwice)) {
            return true;
        }

        Pixel secondPixelFlipRotatedThrice = secondPixelFlip.rotatePixel().rotatePixel().rotatePixel();

        return matches(firstPixel, secondPixelFlipRotatedThrice);
    }

    public static boolean matches(Pixel firstPixel, Pixel secondPixel) {
        if (firstPixel.right.equals(secondPixel.left)) {
            return true;
        }

        if (firstPixel.left.equals(secondPixel.right)) {
            return true;
        }

        if (firstPixel.top.equals(secondPixel.bottom)) {
            return true;
        }

        return firstPixel.bottom.equals(secondPixel.top);
    }


}
