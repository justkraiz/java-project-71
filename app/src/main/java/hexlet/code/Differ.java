package hexlet.code;

import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Differ {

    @Getter @Setter
    private static Map<String, Object> data1;
    @Getter @Setter
    private static Map<String, Object> data2;


    public static String generate(Path filepath1, Path filepath2) throws IOException, NullPointerException {
        // Чтение и парсинг файлов в мап
        Parser.parse(filepath1, filepath2);
        return getComparisonResult();
    }

    private static String getComparisonResult() {
        StringBuilder result = new StringBuilder("{\n");
        Set<String> allKeys = new HashSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        for (String key : allKeys.stream().sorted().toList()) {
            if (data1.containsKey(key) && data2.containsKey(key)) {
                appendComparison(result, key, data1.get(key), data2.get(key));
            } else if (data1.containsKey(key)) {
                appendRemovedKey(result, key, data1.get(key));
            } else {
                appendAddedKey(result, key, data2.get(key));
            }
        }
        result.append("}");
        return result.toString();
    }

    private static void appendComparison(StringBuilder result, String key, Object value1, Object value2) {
        if (value1.equals(value2)) {
            result.append("    ").append(key).append(": ").append(value1).append("\n");
        } else {
            result.append("  - ").append(key).append(": ").append(value1).append("\n");
            result.append("  + ").append(key).append(": ").append(value2).append("\n");
        }
    }

    private static void appendRemovedKey(StringBuilder result, String key, Object value) {
        result.append("  - ").append(key).append(": ").append(value).append("\n");
    }

    private static void appendAddedKey(StringBuilder result, String key, Object value) {
        result.append("  + ").append(key).append(": ").append(value).append("\n");
    }

}
