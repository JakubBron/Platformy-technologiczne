package org.example;

import org.apache.commons.lang3.tuple.Pair;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageOper {

    public static Pair<String, BufferedImage> loadImagePair(Path path) throws UncheckedIOException {
        log("Loading image: " + path);
        try {
            var filename = path.getFileName().toString();
            var image = ImageIO.read(path.toFile());
            return Pair.of(filename, image);
        } catch (IOException e) {
            throw new UncheckedIOException("Error while loading image", e);
        }
    }

    public static Pair<String, BufferedImage> modifyImagePair(Pair<String, BufferedImage> input) {
        log("Modifying image: " + input.getLeft());
        BufferedImage img = input.getRight();
        BufferedImage result = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < img.getHeight(); y++) {
                var color = new Color(img.getRGB(x, y));
                result.setRGB(x, y, new Color(color.getRed(), color.getGreen(), 0).getRGB());
            }
        }
        return Pair.of(input.getLeft(), result);
    }

    public static void saveImagePair(Pair<String, BufferedImage> pair, Path outputPath) {
        log("Saving image: " + pair.getLeft());
        var img = pair.getRight();
        try {
            if (!Files.exists(outputPath)) {
                Files.createDirectories(outputPath);
            }
            ImageIO.write(pair.getRight(), "jpg", outputPath.resolve(pair.getLeft()).toFile());
            log("Image saved: " + pair.getLeft());
        } catch (IOException e) {
            throw new UncheckedIOException("Error while saving image", e);
        }
    }

    private static void log(String message) {
        System.out.println(Thread.currentThread() + " " + message);
    }
}
