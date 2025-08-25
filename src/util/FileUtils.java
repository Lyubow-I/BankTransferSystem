package util;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;

public class FileUtils {

    public static List<String> readLines(String filePath) throws IOException {
        return java.nio.file.Files.readAllLines(Paths.get(filePath));
    }

    public static void writeLines(String filePath, List<String> lines) throws IOException {
        java.nio.file.Files.write(Paths.get(filePath), lines);
    }

    public static boolean fileExists(String filePath) {
        return Files.exists(Paths.get(filePath));
    }

    public static boolean isTxtFile(String filename) {
        return filename.toLowerCase().endsWith(".txt");
    }
}
