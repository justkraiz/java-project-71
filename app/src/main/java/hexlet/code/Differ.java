package hexlet.code;

import hexlet.code.formatters.Formatter;
import hexlet.code.formatters.JSONFormatter;
import hexlet.code.formatters.PlainFormatter;
import hexlet.code.formatters.StylishFormatter;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;


public class Differ {

    @Getter @Setter
    private static Map<String, Object> data1;
    @Getter @Setter
    private static Map<String, Object> data2;

    public static String generate(String filepath1, String filepath2) throws IOException, NullPointerException {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format)
            throws IOException, NullPointerException {

        Parser.parse(Path.of(filepath1), Path.of(filepath2));
        Formatter formatter = switch (format) {
            case "stylish" -> new StylishFormatter();
            case "plain" -> new PlainFormatter();
            case "json" -> new JSONFormatter();
            default -> throw new IllegalArgumentException("Unsupported format: " + format);
        };
        return formatter.format();
    }

}
