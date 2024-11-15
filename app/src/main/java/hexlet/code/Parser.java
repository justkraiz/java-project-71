package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import hexlet.code.utils.Utils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.TreeMap;

public class Parser {

    public static void parse(Path filepath1, Path filepath2) throws IOException, NullPointerException {
        Differ.setData1(readFile(Utils.absOrConvertPath(filepath1)));
        Differ.setData2(readFile(Utils.absOrConvertPath(filepath2)));
    }

    public static TreeMap<String, Object> readFile(Path filepath) throws IOException {
        var fileExtension = Utils.getFileExtension(filepath);
        ObjectMapper mapper;
        if (fileExtension == null) {
            throw new IOException(filepath + " is not a valid file, file extension = null");
        } else {
            return switch (fileExtension.toLowerCase()) {
                case "json" -> {
                    // json mapper
                    mapper = new ObjectMapper();
                    // Используем TreeMap для сортировки ключей
                    yield mapper.readValue(Files.readString(filepath), new TypeReference<>() {
                    });
                }
                case "yaml","yml" -> {
                    // yaml mapper
                    mapper = new ObjectMapper(new YAMLFactory());
                    // Используем TreeMap для сортировки ключей
                    yield mapper.readValue(Files.readString(filepath), new TypeReference<>() {
                    });
                }
                default -> throw new IOException("Unsupported file extension: " + fileExtension);
            };
        }
    }
}
