package hexlet.code.formatters;

import hexlet.code.formatters.utils.TreeBuilder;

import java.util.Map;


public final class PlainFormatter implements Formatter {

    @Override
    public String format() {
        // Получаем различия с использованием утилитного метода
        Map<String, Map<String, Object>> differences = TreeBuilder.getDifferences();

        // Создаем строку для хранения результата
        StringBuilder result = new StringBuilder();

        // Форматируем результат
        differences.forEach((key, entry) -> result.append(formatKey(entry, key)));

        // Преобразуем результат в строку
        return result.toString().trim();
    }

    private String formatKey(Map<String, Object> entry, String key) {
        String status = (String) entry.get("status");
        return switch (status) {
            case "updated" -> formatUpdatedProperty(key, entry.get("oldValue"), entry.get("newValue"));
            case "removed" -> formatRemovedProperty(key);
            case "added" -> formatAddedProperty(key, entry.get("value"));
            default -> "";
        };
    }

    private String formatUpdatedProperty(String key, Object value1, Object value2) {
        return String.format(
                "Property '%s' was updated. From %s to %s\n",
                key, stringify(value1), stringify(value2)
        );
    }

    private String formatRemovedProperty(String key) {
        return String.format("Property '%s' was removed\n", key);
    }

    private String formatAddedProperty(String key, Object value) {
        return String.format(
                "Property '%s' was added with value: %s\n",
                key, stringify(value)
        );
    }

    private String stringify(Object value) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else if (value instanceof Map || value instanceof Iterable) {
            return "[complex value]";
        } else {
            return value.toString();
        }
    }
}
