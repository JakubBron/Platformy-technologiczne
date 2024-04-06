package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Objects;

public class Main {
    private static Image loadImage(Path path) {
        try {
            return new Image(path.getFileName().toString(), ImageIO.read(path.toFile()));
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
            return null;
        }
    }

    private static BufferedImage processImage(BufferedImage img) {
        var width = img.getWidth();
        var height = img.getHeight();
        var result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (var x = 0; x < width; x++) {
            for (var y = 0; y < height; y++) {
                var color = new Color(img.getRGB(x, y));
                result.setRGB(x, y, new Color(color.getRed(), color.getGreen(), 0).getRGB());
            }
        }
        return result;
    }
    public static void saveImage(Image img, Path outputDir) {
        try {
            ImageIO.write(img.data(), "jpg", outputDir.resolve(img.name()).toFile());
            System.out.println("Image " + img.name() + " processed & saved.");
        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Usage: java -jar <jar-file> <input-dir> <output-dir> <number-of-threads>");
            System.exit(1);
        }

        try (var stream = Files.list(Path.of(args[0]))) {
            // check if folder exists
            if (Files.exists(Path.of(args[1]))) {
                Files.walk(Path.of(args[1]))
                        .sorted(Comparator.reverseOrder())
                        .forEach(path -> {
                            try {
                                System.out.println("Deleting: " + path);
                                Files.delete(path);  //delete each file or directory
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            }
            Files.createDirectory(Path.of(args[1]));
            long time = System.currentTimeMillis();
            // setting number of threads
            System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", args[2]);

            stream  .parallel()
                    .filter(path -> path.toString().endsWith(".jpg"))
                    .map(Main::loadImage)
                    .filter(Objects::nonNull)
                    .map(img -> new Image(img.name(), processImage(img.data())))
                    .forEach(img -> saveImage(img, Path.of(args[1])));

            System.out.println("Execution time: " + (System.currentTimeMillis() - time) + " ms");
        } catch (IOException ex) {
            System.err.println("An error occurred: " + ex.getMessage());
        }
    }
}