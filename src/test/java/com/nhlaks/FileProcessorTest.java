package com.nhlaks;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for FileProcessor using JUnit 5.
 */
class FileProcessorTest {

    private FileProcessor processor;
    private static final String TEST_INPUT  = "src/test/resources/test_input.txt";
    private static final String TEST_OUTPUT = "src/test/resources/test_output.txt";


    @Test
    @DisplayName("readFile returns correct number of lines")
    void testReadFileLineCount() throws IOException {
        List<String> lines = processor.readFile(TEST_INPUT);
        assertEquals(3, lines.size());
    }

    @Test
    @DisplayName("processContent counts words correctly")
    void testWordCount() {
        List<String> lines = List.of("Hello world", "This is a test");
        Map<String, Integer> stats = processor.processContent(lines);
        assertEquals(6, stats.get("wordCount"));
    }

    @Test
    @DisplayName("processContent counts lines correctly")
    void testLineCount() {
        List<String> lines = List.of("Line one", "Line two", "Line three");
        Map<String, Integer> stats = processor.processContent(lines);
        assertEquals(3, stats.get("lineCount"));
    }

    @Test
    @DisplayName("processContent counts characters correctly")
    void testCharCount() {
        List<String> lines = List.of("abc", "de");
        Map<String, Integer> stats = processor.processContent(lines);
        assertEquals(5, stats.get("charCount"));
    }

    @Test
    @DisplayName("writeResults creates output file")
    void testWriteResultsCreatesFile() throws IOException {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("lineCount", 3);
        stats.put("wordCount", 9);
        stats.put("charCount", 40);

        processor.writeResults(stats, TEST_OUTPUT);

        assertTrue(Files.exists(Path.of(TEST_OUTPUT)));
    }

    @Test
    @DisplayName("writeResults output contains correct stats")
    void testWriteResultsContent() throws IOException {
        Map<String, Integer> stats = new LinkedHashMap<>();
        stats.put("lineCount", 2);
        stats.put("wordCount", 5);
        stats.put("charCount", 20);

        processor.writeResults(stats, TEST_OUTPUT);
        String content = Files.readString(Path.of(TEST_OUTPUT));

        assertTrue(content.contains("Words  : 5"));
        assertTrue(content.contains("Lines  : 2"));
    }

    @Test
    @DisplayName("readFile throws FileNotFoundException for missing file")
    void testFileNotFoundException() {
        Exception exception = assertThrows(FileNotFoundException.class, () ->
            processor.readFile("nonexistent_file.txt")
        );
        assertTrue(exception instanceof FileNotFoundException);
    }

    @Test
    @DisplayName("processContent handles empty list")
    void testEmptyInput() {
        Map<String, Integer> stats = processor.processContent(List.of());
        assertEquals(0, stats.get("lineCount"));
        assertEquals(0, stats.get("wordCount"));
        assertEquals(0, stats.get("charCount"));
    }
}