package com.nhlaks;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * FileProcessor reads a text file, processes its content,
 * and writes statistics (word count, line count, char count) to an output file.
 */
public class FileProcessor {

    /**
     * Reads all lines from the given file path.
     *
     * @param inputPath path to the input file
     * @return list of lines read from the file
     * @throws FileNotFoundException if the file does not exist
     * @throws IOException           if an I/O error occurs
     */
    public List<String> readFile(String inputPath) throws IOException {
        Path path = Path.of(inputPath);

        if (!Files.exists(path)) {
            throw new FileNotFoundException("Input file not found: " + inputPath);
        }

        return Files.readAllLines(path);
    }

    /**
     * Processes the list of lines and returns a statistics map.
     *
     * @param lines list of lines from the file
     * @return map containing lineCount, wordCount, charCount
     */
    public Map<String, Integer> processContent(List<String> lines) {
        int lineCount = lines.size();
        int wordCount = 0;
        int charCount = 0;

        for (String line : lines) {
            charCount += line.length();

            String[] words = line.trim().split("\\s+");
            if (!line.trim().isEmpty()) {
                wordCount += words.length;
            }
        }

        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("lineCount", lineCount);
        stats.put("wordCount", wordCount);
        stats.put("charCount", charCount);

        return stats;
    }

    /**
     * Writes the processed statistics to the output file.
     *
     * @param stats      map of statistics to write
     * @param outputPath path to the output file
     * @throws IOException if an I/O error occurs during writing
     */
    public void writeResults(Map<String, Integer> stats, String outputPath) throws IOException {
        Path path = Path.of(outputPath);

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("===== File Processing Results =====");
            writer.newLine();
            writer.write("Lines  : " + stats.get("lineCount"));
            writer.newLine();
            writer.write("Words  : " + stats.get("wordCount"));
            writer.newLine();
            writer.write("Chars  : " + stats.get("charCount"));
            writer.newLine();
            writer.write("===================================");
            writer.newLine();
        }
    }

    /**
     * Full pipeline: read → process → write.
     *
     * @param inputPath  path to the input file
     * @param outputPath path to write results to
     * @return the statistics map
     * @throws FileNotFoundException if the input file is missing
     * @throws IOException           if any I/O error occurs
     */
    public Map<String, Integer> process(String inputPath, String outputPath) throws IOException {
        List<String> lines = readFile(inputPath);
        Map<String, Integer> stats = processContent(lines);
        writeResults(stats, outputPath);
        return stats;
    }
}