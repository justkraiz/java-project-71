package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.Differ;
import hexlet.code.formatters.utils.FormatterUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class JSONFormatter implements Formatter {

    @Override
    public String format() {
        // Получаем данные для форматирования
        var data1 = Differ.getData1();
        var data2 = Differ.getData2();

        // Создаем объединенный набор всех ключей
        Set<String> allKeys = new HashSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        // Создаем структуру для хранения результата
        Map<String, Map<String, Object>> differences = new TreeMap<>();

        // Обрабатываем каждый ключ
        allKeys.forEach(key -> processKey(differences, data1, data2, key));

        // Преобразуем результат в JSON
        return convertToJson(differences);
    }

    private void processKey(Map<String, Map<String, Object>> differences,
                            Map<String, Object> data1,
                            Map<String, Object> data2,
                            String key) {
        Map<String, Object> entry = new HashMap<>();
        if (FormatterUtils.isUpdated(data1, data2, key)) {
            entry.put("status", "updated");
            entry.put("oldValue", data1.get(key));
            entry.put("newValue", data2.get(key));
        } else if (FormatterUtils.isRemoved(data1, data2, key)) {
            entry.put("status", "removed");
            entry.put("value", data1.get(key));
        } else if (FormatterUtils.isAdded(data1, data2, key)) {
            entry.put("status", "added");
            entry.put("value", data2.get(key));
        } else {
            entry.put("status", "unchanged");
            entry.put("value", data1.get(key));
        }
        differences.put(key, entry);
    }


    private String convertToJson(Map<String, Map<String, Object>> differences) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(differences);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting differences to JSON", e);
        }
    }
}
