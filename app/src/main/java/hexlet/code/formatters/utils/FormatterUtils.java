package hexlet.code.formatters.utils;

import java.util.Map;

public class FormatterUtils {
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

    public static boolean isUnchanged(Map<String, Object> data1, Map<String, Object> data2, String key) {
        return data1.containsKey(key) && data2.containsKey(key)
                && data1.get(key) != null && data1.get(key).equals(data2.get(key));
    }
}
