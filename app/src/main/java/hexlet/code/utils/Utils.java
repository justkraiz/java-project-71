package hexlet.code.utils;

import java.nio.file.Path;

public class Utils {

    public static String getFileExtension(Path filePath) {
        // Получаем имя файла
        String fileName = filePath.getFileName().toString();
        // Проверяем, есть ли в имени файла точка
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            // Извлекаем расширение файла
            return fileName.substring(dotIndex + 1);
        } else {
            System.out.println("File doesn't have extension: " + fileName);
            return null;
        }
    }

    public static Path absOrConvertPath(Path filepath) {
        return filepath.isAbsolute() ? filepath : filepath.toAbsolutePath();
    }
}
