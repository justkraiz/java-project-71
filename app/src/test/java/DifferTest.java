import hexlet.code.Differ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DifferTest {
    static Path file1JsonPathOld;
    static Path file2JsonPathOld;
    static Path file1JsonPathNew;
    static Path file2JsonPathNew;
    static Path file1YamlPathOld;
    static Path file2YamlPathOld;
    static Path file1YamlPathNew;
    static Path file2YamlPathNew;
    @BeforeAll
    public static void setUp() {
        file1JsonPathOld = Path.of("src/test/resources/file1_old.json");
        file2JsonPathOld = Path.of("src/test/resources/file2_old.json");
        file1JsonPathNew = Path.of("src/test/resources/file1_new.json");
        file2JsonPathNew = Path.of("src/test/resources/file2_new.json");
        file1YamlPathOld = Path.of("src/test/resources/file1_old.yaml");
        file2YamlPathOld = Path.of("src/test/resources/file2_old.yaml");
        file1YamlPathNew = Path.of("src/test/resources/file1_new.yaml");
        file2YamlPathNew = Path.of("src/test/resources/file2_new.yaml");
    }

    @Test
    void testDifferPlainFormatJson() throws IOException {
        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'""";
        assertEquals(expected, Differ.generate(file1JsonPathNew, file2JsonPathNew, "plain"));
    }

    @Test
    void testDifferStylishFormatYaml() throws IOException {
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";
        assertEquals(expected, Differ.generate(file1YamlPathNew, file2YamlPathNew, "stylish"));
    }


    @Test
    void testDifferStylishFormat() throws IOException {
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";
        assertEquals((expected), Differ.generate(file1JsonPathNew, file2JsonPathNew, "stylish"));
    }

    @Test
    void testDifferThrowsIllegalArgumentException() {
        // Проверяем, что метод выбрасывает исключение
        // IllegalArgumentException при передаче несуществующего пути
        assertThrows(IOException.class, () ->
                Differ.generate(
                        Path.of("asd/asd.null"),
                        Path.of("asd/empty.null"), "stylish"));
    }

    @Test
    void testJsonComparisonStylish() throws Exception {
        String expectedOutput = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actualOutput = Differ.generate(file1JsonPathOld, file2JsonPathOld, "stylish");
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
        String actualOutput = Differ.generate(file1YamlPathOld, file2YamlPathOld, "stylish");
        assertEquals(expectedOutput, actualOutput);
    }

}
