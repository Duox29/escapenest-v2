package com.escapenest.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static void createDirectory(String name) {
        Path path = Paths.get(name);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException("không thể tạo folder");
            }
        }
    }

    public static void deleteFile(String filePath) {
        filePath = filePath.substring(1);
        Path path = Paths.get(filePath);
        try {
            Files.deleteIfExists(path);
        } catch (IOException e) {
            throw new RuntimeException("Không thể xóa file");
        }
    }
}
