package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.formatters.utils.FormatterUtils;

import java.util.Map;

public final class JSONFormatter implements Formatter {
    @Override
    public String format() {
        return convertToJson(FormatterUtils.getDifferences());
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
