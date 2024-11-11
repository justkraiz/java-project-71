package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Differ {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String generate(Path filepath1, Path filepath2) throws IOException {
        // Конвертируем если нужно пути
        // Чтение и парсинг JSON файлов в мап
        Map<String, Object> data1 = readFile(absOrAutoConvertPath(filepath1));
        Map<String, Object> data2 = readFile(absOrAutoConvertPath(filepath2));

        // Собираем объединённый набор ключей и сортируем их
        Set<String> allKeys = new HashSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        return getComparisonResult(allKeys, data1, data2);
    }

    private static String getComparisonResult(Set<String> allKeys, Map<String,
            Object> data1, Map<String, Object> data2) {
        // Формируем результирующую строку
        StringBuilder result = new StringBuilder("{\n");

        for (String key : allKeys.stream().sorted().toList()) {
            if (data1.containsKey(key) && data2.containsKey(key)) {
                if (data1.get(key).equals(data2.get(key))) {
                    result.append("    ").append(key).append(": ").append(data1.get(key)).append("\n");
                } else {
                    result.append("  - ").append(key).append(": ").append(data1.get(key)).append("\n");
                    result.append("  + ").append(key).append(": ").append(data2.get(key)).append("\n");
                }
            } else if (data1.containsKey(key)) {
                result.append("  - ").append(key).append(": ").append(data1.get(key)).append("\n");
            } else {
                result.append("  + ").append(key).append(": ").append(data2.get(key)).append("\n");
            }
        }
        result.append("}");
        return result.toString();
    }

    private static Path absOrAutoConvertPath(Path filepath) {
        return filepath.isAbsolute() ? filepath : filepath.toAbsolutePath();
    }

    private static TreeMap<String, Object> readFile(Path filepath) throws IOException {
        String content = Files.readString(filepath);
        return MAPPER.readValue(content, new TypeReference<>() { });  // Используем TreeMap для сортировки ключей
    }
}
