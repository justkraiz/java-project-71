package hexlet.code.formatters;

import hexlet.code.Differ;
import hexlet.code.formatters.utils.FormatterUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StylishFormatter implements Formatter {

    @Override
    public String format() {
        // Получаем данные для форматирования
        var data1 = Differ.getData1();
        var data2 = Differ.getData2();

        // Создаем объединенный набор всех ключей
        Set<String> allKeys = new HashSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        // Создаем структуру для хранения результата
        StringBuilder result = new StringBuilder("{\n");

        // Обрабатываем каждый ключ
        allKeys.stream().sorted().forEach(key -> result.append(formatKey(data1, data2, key)));

        result.append("}");

        // Преобразуем результат в строку
        return result.toString();
    }

    private String formatKey(Map<String, Object> data1, Map<String, Object> data2, String key) {
        if (FormatterUtils.isUnchanged(data1, data2, key)) {
            return formatUnchangedKey(key, data1.get(key));
        } else if (FormatterUtils.isUpdated(data1, data2, key)) {
            return formatUpdatedKey(key, data1.get(key), data2.get(key));
        } else if (FormatterUtils.isRemoved(data1, data2, key)) {
            return formatRemovedKey(key, data1.get(key));
        } else {
            return formatAddedKey(key, data2.get(key));
        }
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
