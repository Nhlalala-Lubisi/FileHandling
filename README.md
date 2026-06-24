# FileHandling

A Maven-based Java 21 program that reads a text file, processes its content (word count, line count, character count), writes the results to an output file, and handles file-related exceptions.

---

## Project Structure

```
FileHandling/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/filehandling/
    │   │   ├── FileProcessor.java     # Core read/process/write logic
    │   │   └── Main.java              # Entry point
    │   └── resources/
    │       ├── input.txt              # Sample input file
    │       └── output.txt             # Generated after running
    └── test/
        ├── java/com/filehandling/
        │   └── FileProcessorTest.java # JUnit 5 unit tests
        └── resources/
            ├── test_input.txt         # Auto-created by tests
            └── test_output.txt        # Auto-created by tests
```

---

## Prerequisites

| Tool | Version |
|------|---------|
| JDK  | 21+     |
| Maven | 3.8+  |
| VS Code | Any  |
| Extension Pack for Java | Latest |

---

## Getting Started

### 1. Clone / open the project

```bash
cd FileHandling
```

### 2. Compile

```bash
mvn compile
```

### 3. Run

```bash
mvn exec:java -Dexec.mainClass="com.filehandling.Main"
```

Expected console output:

```
File processed successfully!
Lines : 5
Words : 38
Chars : 249
Results written to: src/main/resources/output.txt
```

### 4. Run Tests

```bash
mvn test
```

---

## How It Works

```
input.txt  →  readFile()  →  processContent()  →  writeResults()  →  output.txt
```

| Method | Responsibility |
|--------|---------------|
| `readFile(path)` | Reads all lines; throws `FileNotFoundException` if missing |
| `processContent(lines)` | Counts lines, words (split on whitespace), and characters |
| `writeResults(stats, path)` | Writes a formatted stats report; creates directories if needed |
| `process(input, output)` | Full pipeline — read → process → write |

---

## Output File Format

`src/main/resources/output.txt` is generated on each run:

```
===== File Processing Results =====
Lines  : 5
Words  : 38
Chars  : 249
===================================
```

---

## Exception Handling

| Exception | When it occurs | How it's handled |
|-----------|---------------|-----------------|
| `FileNotFoundException` | Input file path does not exist | Caught in `Main`, prints descriptive error |
| `IOException` | Disk error, permission issue, bad path | Caught in `Main`, prints I/O error message |

---

## Test Coverage

| Test | What it verifies |
|------|-----------------|
| `testReadFileLineCount` | Correct number of lines read |
| `testWordCount` | Word splitting and counting |
| `testLineCount` | Line count from processed content |
| `testCharCount` | Character count (no newlines) |
| `testWriteResultsCreatesFile` | Output file is created on disk |
| `testWriteResultsContent` | Output file contains correct stats |
| `testFileNotFoundException` | Missing file throws correct exception |
| `testEmptyInput` | Empty list returns all zeros |

---

## Dependencies

```xml
<!-- JUnit 5 (test scope only) -->
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>5.10.2</version>
    <scope>test</scope>
</dependency>
```

---

## Learning Objectives Covered

- [x] Read a text file and process content (word count, line count, char count)
- [x] Write processed data to a new output file
- [x] Handle `FileNotFoundException` and `IOException`
- [x] Unit tested with JUnit 5

---

## Author

**Nhlalala** — Advanced Diploma in Information Technology (NQF Level 7)  
Vaal University of Technology · 2026
