package hexlet.code.formatters.utils;

import hexlet.code.Differ;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class FormatterUtils {
    public static Map<String, Map<String, Object>> getDifferences() {
        var data1 = Differ.getData1();
        var data2 = Differ.getData2();
        Set<String> allKeys = new HashSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        // Используем TreeMap для сортировки ключей
        Map<String, Map<String, Object>> differences = new TreeMap<>();

        allKeys.forEach(key -> {
            Map<String, Object> entry = new HashMap<>();
            if (isUpdated(data1, data2, key)) {
                entry.put("status", "updated");
                entry.put("oldValue", data1.get(key));
                entry.put("newValue", data2.get(key));
            } else if (isRemoved(data1, data2, key)) {
                entry.put("status", "removed");
                entry.put("value", data1.get(key));
            } else if (isAdded(data1, data2, key)) {
                entry.put("status", "added");
                entry.put("value", data2.get(key));
            } else {
                entry.put("status", "unchanged");
                entry.put("value", data1.get(key));
            }
            differences.put(key, entry);
        });

        return differences;
    }

    public static boolean isUpdated(Map<String, Object> data1, Map<String, Object> data2, String key) {
        return data1.containsKey(key) && data2.containsKey(key)
                && (data1.get(key) == null || !data1.get(key).equals(data2.get(key)));
    }

    public static boolean isRemoved(Map<String, Object> data1, Map<String, Object> data2, String key) {
        return data1.containsKey(key) && !data2.containsKey(key);
    }

    public static boolean isAdded(Map<String, Object> data1, Map<String, Object> data2, String key) {
        return !data1.containsKey(key) && data2.containsKey(key);
    }
}
