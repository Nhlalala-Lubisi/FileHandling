package com.nhlaks;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Map;

/**
 * Main entry point for the File Handling program.
 */
public class Main {

    public static void main(String[] args) {
        // Resolve paths relative to the project root regardless of working directory
        String projectRoot = getProjectRoot();
        String inputPath  = projectRoot + "/src/main/resources/input.txt";
        String outputPath = projectRoot + "/src/main/resources/output.txt";

        System.out.println("Reading from: " + inputPath);

        FileProcessor processor = new FileProcessor();

        try {
            Map<String, Integer> stats = processor.process(inputPath, outputPath);

            System.out.println("File processed successfully!");
            System.out.println("Lines : " + stats.get("lineCount"));
            System.out.println("Words : " + stats.get("wordCount"));
            System.out.println("Chars : " + stats.get("charCount"));
            System.out.println("Results written to: " + outputPath);

        } catch (FileNotFoundException e) {
            System.err.println("ERROR - File not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("ERROR - I/O problem: " + e.getMessage());
        }
    }

    /**
     * Resolves the project root by navigating up from the compiled classes folder.
     * target/classes -> target -> project root
     *
     * @return absolute path string to the project root
     */
    private static String getProjectRoot() {
        try {
            Path classesDir = Path.of(
                Main.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .toURI()
            );
            // classesDir = .../FileHandling/target/classes
            // go up twice to reach FileHandling/
            return classesDir.getParent().getParent().toString();
        } catch (URISyntaxException e) {
            // Fallback: use current working directory
            return System.getProperty("user.dir");
        }
    }
}