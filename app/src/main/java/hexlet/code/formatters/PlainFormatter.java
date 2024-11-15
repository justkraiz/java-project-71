package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlainFormatter implements Formatter {

    @Override
    public String format() {
        var data1 = Differ.getData1();
        var data2 = Differ.getData2();
        Set<String> allKeys = new HashSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());

        StringBuilder result = new StringBuilder();
        allKeys.stream().sorted().forEach(key -> result.append(formatKey(data1, data2, key)));

        return result.toString().trim();
    }

    private String formatKey(Map<String, Object> data1, Map<String, Object> data2, String key) {
        boolean isInData1 = data1.containsKey(key);
        boolean isInData2 = data2.containsKey(key);

        if (isUpdated(data1, data2, key)) {
            return formatUpdatedProperty(key, data1.get(key), data2.get(key));
        } else if (isRemoved(isInData1, isInData2)) {
            return formatRemovedProperty(key);
        } else if (isAdded(isInData1, isInData2)) {
            return formatAddedProperty(key, data2.get(key));
        }
        return "";
    }

    private boolean isUpdated(Map<String, Object> data1, Map<String, Object> data2, String key) {
        return data1.containsKey(key) && data2.containsKey(key)
                && (data1.get(key) == null || !data1.get(key).equals(data2.get(key)));
    }

    private boolean isRemoved(boolean isInData1, boolean isInData2) {
        return isInData1 && !isInData2;
    }

    private boolean isAdded(boolean isInData1, boolean isInData2) {
        return !isInData1 && isInData2;
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
