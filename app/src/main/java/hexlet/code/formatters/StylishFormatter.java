package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StylishFormatter implements Formatter {

    @Override
    public String format() {
        var data1 = Differ.getData1();
        var data2 = Differ.getData2();
        Set<String> allKeys = new HashSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());
        StringBuilder result = new StringBuilder("{\n");

        allKeys.stream().sorted().forEach(key -> result.append(formatKey(data1, data2, key)));

        result.append("}");
        return result.toString();
    }

    private String formatKey(Map<String, Object> data1, Map<String, Object> data2, String key) {
        Object value1 = data1.get(key);
        Object value2 = data2.get(key);

        if (isUnchanged(data1, data2, key)) {
            return formatUnchangedKey(key, value1);
        } else if (isUpdated(data1, data2, key)) {
            return formatUpdatedKey(key, value1, value2);
        } else if (isRemoved(data1, data2, key)) {
            return formatRemovedKey(key, value1);
        } else {
            return formatAddedKey(key, value2);
        }
    }

    private boolean isUnchanged(Map<String, Object> data1, Map<String, Object> data2, String key) {
        return data1.containsKey(key) && data2.containsKey(key)
                && data1.get(key) != null && data1.get(key).equals(data2.get(key));
    }

    private boolean isUpdated(Map<String, Object> data1, Map<String, Object> data2, String key) {
        return data1.containsKey(key) && data2.containsKey(key)
                && (data1.get(key) == null || !data1.get(key).equals(data2.get(key)));
    }

    private boolean isRemoved(Map<String, Object> data1, Map<String, Object> data2, String key) {
        return data1.containsKey(key) && !data2.containsKey(key);
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
