package hexlet.code.formatters;

import hexlet.code.Differ;

import java.util.HashSet;
import java.util.Set;

public class StylishFormatter implements Formatter {

    @Override
    public String format() {
        var data1 = Differ.getData1();
        var data2 = Differ.getData2();
        Set<String> allKeys = new HashSet<>(data1.keySet());
        allKeys.addAll(data2.keySet());
        StringBuilder result = new StringBuilder("{\n");

        for (String key : allKeys.stream().sorted().toList()) {
            Object value1 = data1.get(key);
            Object value2 = data2.get(key);

            if (data1.containsKey(key) && data2.containsKey(key)) {
                if (value1 != null && value1.equals(value2)) {
                    result.append("    ").append(key).append(": ").append(value1).append("\n");
                } else {
                    result.append("  - ").append(key).append(": ").append(value1).append("\n");
                    result.append("  + ").append(key).append(": ").append(value2).append("\n");
                }
            } else if (data1.containsKey(key)) {
                result.append("  - ").append(key).append(": ").append(value1).append("\n");
            } else {
                result.append("  + ").append(key).append(": ").append(value2).append("\n");
            }
        }
        result.append("}");
        return result.toString();
    }
}
