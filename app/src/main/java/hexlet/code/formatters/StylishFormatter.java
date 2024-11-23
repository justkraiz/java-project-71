package hexlet.code.formatters;

import hexlet.code.formatters.utils.TreeBuilder;
import java.util.Map;

public final class StylishFormatter implements Formatter {

    @Override
    public String format() {
        // Получаем различия с помощью утилитного метода
        Map<String, Map<String, Object>> differences = TreeBuilder.getDifferences();

        // Создаем структуру для хранения результата
        StringBuilder result = new StringBuilder("{\n");

        // Форматируем результат, используя различия
        differences.forEach((key, entry) -> result.append(formatKey(entry, key)));

        result.append("}");

        // Преобразуем результат в строку
        return result.toString();
    }

    private String formatKey(Map<String, Object> entry, String key) {
        String status = (String) entry.get("status");
        return switch (status) {
            case "unchanged" -> formatUnchangedKey(key, entry.get("value"));
            case "updated" -> formatUpdatedKey(key, entry.get("oldValue"),
                    entry.get("newValue"));
            case "removed" -> formatRemovedKey(key, entry.get("value"));
            case "added" -> formatAddedKey(key, entry.get("value"));
            default -> "";
        };
    }

    private String formatUnchangedKey(String key, Object value) {
        return String.format("    %s: %s\n", key, value);
    }

    private String formatUpdatedKey(String key, Object value1, Object value2) {
        return String.format("  - %s: %s\n  + %s: %s\n", key, value1, key, value2);
    }

    private String formatRemovedKey(String key, Object value) {
        return String.format("  - %s: %s\n", key, value);
    }

    private String formatAddedKey(String key, Object value) {
        return String.format("  + %s: %s\n", key, value);
    }
}
