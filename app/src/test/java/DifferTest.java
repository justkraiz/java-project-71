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
    void testDifferJsonFormat() throws IOException {
        String expected = """
                {
                  "chars1" : {
                    "value" : [ "a", "b", "c" ],
                    "status" : "unchanged"
                  },
                  "chars2" : {
                    "newValue" : false,
                    "oldValue" : [ "d", "e", "f" ],
                    "status" : "updated"
                  },
                  "checked" : {
                    "newValue" : true,
                    "oldValue" : false,
                    "status" : "updated"
                  },
                  "default" : {
                    "newValue" : [ "value1", "value2" ],
                    "oldValue" : null,
                    "status" : "updated"
                  },
                  "id" : {
                    "newValue" : null,
                    "oldValue" : 45,
                    "status" : "updated"
                  },
                  "key1" : {
                    "value" : "value1",
                    "status" : "removed"
                  },
                  "key2" : {
                    "value" : "value2",
                    "status" : "added"
                  },
                  "numbers1" : {
                    "value" : [ 1, 2, 3, 4 ],
                    "status" : "unchanged"
                  },
                  "numbers2" : {
                    "newValue" : [ 22, 33, 44, 55 ],
                    "oldValue" : [ 2, 3, 4, 5 ],
                    "status" : "updated"
                  },
                  "numbers3" : {
                    "value" : [ 3, 4, 5 ],
                    "status" : "removed"
                  },
                  "numbers4" : {
                    "value" : [ 4, 5, 6 ],
                    "status" : "added"
                  },
                  "obj1" : {
                    "value" : {
                      "nestedKey" : "value",
                      "isNested" : true
                    },
                    "status" : "added"
                  },
                  "setting1" : {
                    "newValue" : "Another value",
                    "oldValue" : "Some value",
                    "status" : "updated"
                  },
                  "setting2" : {
                    "newValue" : 300,
                    "oldValue" : 200,
                    "status" : "updated"
                  },
                  "setting3" : {
                    "newValue" : "none",
                    "oldValue" : true,
                    "status" : "updated"
                  }
                }""";
        assertEquals(expected, Differ.generate(file1JsonPathNew, file2JsonPathNew, "json"));
    }

    @Test
    void testDifferPlainFormatJsonConfigs() throws IOException {
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
    void testDifferStylishFormatYamlConfigsNew() throws IOException {
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
    void testDifferStylishFormatJsonConfigsNew() throws IOException {
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
    void testDifferStylishFormatsJsonOldConfigs() throws Exception {
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
    void testDifferStylishFormatYamlConfigsOld() throws Exception {
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
