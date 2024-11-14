import hexlet.code.Differ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DifferTest {
    static Path file1JsonPath;
    static Path file2JsonPath;
    static Path file1YamlPath;
    static Path file2YamlPath;

    @BeforeAll
    public static void setUp() {
        file1JsonPath = Path.of("src/test/resources/file1.json");
        file2JsonPath = Path.of("src/test/resources/file2.json");
        file1YamlPath = Path.of("src/test/resources/file1.yaml");
        file2YamlPath = Path.of("src/test/resources/file2.yaml");
    }

    @Test
    void testDifferThrowsIllegalArgumentException() {
        // Проверяем, что метод выбрасывает исключение
        // IllegalArgumentException при передаче несуществующего пути
        assertThrows(IOException.class, () ->
                Differ.generate(
                        Path.of("asd/asd.null"),
                        Path.of("asd/empty.null")));
    }

    @Test
    void testJsonComparison() throws Exception {
        String expectedOutput = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actualOutput = Differ.generate(file1JsonPath, file2JsonPath);
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    void testYamlComparison() throws Exception {
        String expectedOutput = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";
        String actualOutput = Differ.generate(file1YamlPath, file2YamlPath);
        assertEquals(expectedOutput, actualOutput);
    }

}
