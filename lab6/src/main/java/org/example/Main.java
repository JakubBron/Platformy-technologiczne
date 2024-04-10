package org.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        var inputPathStr = args.length >=1 ? args[0] : "./images";
        var outputPathStr = args.length >=2 ? args[1] : "./images-out";
        var threads = args.length >=3 ? Integer.parseInt(args[2]) : Runtime.getRuntime().availableProcessors();

        System.out.println("Input path: " + inputPathStr + ", output path: " + outputPathStr + ", threads: " + threads);

        var inputPath = Path.of(inputPathStr);
        var outputPath = Path.of(outputPathStr);

        List<Path> files;
        try (var stream = Files.list(inputPath)) {
            files = stream.collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error while reading files!");
            return;
        }
        //System.out.println(files.size());
        var parallelStream = files.parallelStream();
        long startTime = System.currentTimeMillis();
        ForkJoinPool threadPool = new ForkJoinPool(2);
        var t = threadPool.submit(
                () -> parallelStream.map(ImageOper::loadImagePair)
                        .map(ImageOper::modifyImagePair)
                        .forEach(pair -> ImageOper.saveImagePair(pair, outputPath)));
        t.join();
        threadPool.shutdown();
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (endTime - startTime) + " ms");
    }

}