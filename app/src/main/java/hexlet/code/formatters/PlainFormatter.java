package hexlet.code.formatters;

import hexlet.code.Differ;
import hexlet.code.formatters.utils.FormatterUtils;

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
        if (FormatterUtils.isUpdated(data1, data2, key)) {
            return formatUpdatedProperty(key, data1.get(key), data2.get(key));
        } else if (FormatterUtils.isRemoved(data1, data2, key)) {
            return formatRemovedProperty(key);
        } else if (FormatterUtils.isAdded(data1, data2, key)) {
            return formatAddedProperty(key, data2.get(key));
        }
        return "";
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
