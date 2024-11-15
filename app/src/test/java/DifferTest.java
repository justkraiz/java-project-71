import hexlet.code.Differ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class DifferTest {
    private static String file1Json;
    private static String file2Json;
    private static String file3Json;
    private static String file1Yaml;
    private static String file2Yaml;
    private static String file3Yml;
    private static String emptyFile;

    @BeforeAll
    public static void setUp() {
        file1Json = "src/test/resources/file1_new.json";
        file2Json = "src/test/resources/file2_new.json";
        file3Json = "src/test/resources/file3_new.json";
        file1Yaml = "src/test/resources/file1_new.yml";
        file2Yaml = "src/test/resources/file2_new.yaml";
        file3Yml = "src/test/resources/file3_new.yml";
        emptyFile = "src/test/resources/empty.json";

    }

    // Объединенный тест, что методы не возвращают null
    // Далее сравним конкретно по ожидаемому выводу.
    @Test
    void testDifferAllFormatsJsonAnsYamlConfigs() throws IOException {
        assertNotNull(Differ.generate(file1Json, file2Json, "stylish"));
        assertNotNull(Differ.generate(file1Json, file2Json, "plain"));
        assertNotNull(Differ.generate(file1Json, file2Json, "json"));
        assertNotNull(Differ.generate(file1Yaml, file2Yaml, "stylish"));
        assertNotNull(Differ.generate(file1Yaml, file2Yaml, "plain"));
        assertNotNull(Differ.generate(file1Yaml, file2Yaml, "json"));
        assertNotNull(Differ.generate(file1Yaml, file3Yml, "stylish"));
    }

    @Test
    void testDifferWithTwoArgsDefaultFormat() throws IOException {
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
        assertEquals(expected, Differ.generate(file1Json, file2Json));
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
        assertEquals(expected, Differ.generate(file1Json, file2Json, "json"));
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
        assertEquals(expected, Differ.generate(file1Json, file2Json, "plain"));
    }

    @Test
    void testDifferStylishFormatJsonConfigs() throws IOException {
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
        assertEquals((expected), Differ.generate(file1Json, file2Json, "stylish"));
    }

    @Test
    void testDifferStylishFormatYamlConfigs() throws IOException {
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
        assertEquals(expected, Differ.generate(file1Yaml, file2Yaml, "stylish"));
    }

    @Test
    void testDifferThrowsIllegalArgumentException() {
        // Проверяем, что метод выбрасывает исключение
        // IllegalArgumentException при передаче несуществующего пути
        assertThrows(IOException.class, () ->
                Differ.generate(
                        "asd/asd.null",
                        "asd/empty.null",
                        "stylish"));
    }

    @Test
    void testDifferThrowsNullPointerExceptionForNullFilePaths() {
        assertThrows(NullPointerException.class, () ->
                Differ.generate(null, file2Json));
    }

    @Test
    void testDifferThrowsIllegalArgumentExceptionForUnsupportedFormat() {
        assertThrows(IllegalArgumentException.class, () ->
                Differ.generate(file1Json, file2Json, "unsupported_format"));
    }

    @Test
    void testDifferWithEmptyFiles() {
        assertThrows(IOException.class, () ->
                Differ.generate(emptyFile, file2Json));
    }

    @Test
    void testDifferWithOneParam() throws IOException {
        String minimalFile1 = "src/test/resources/oneParameter.json";
        String minimalFile2 = "src/test/resources/oneParameter.json";
        String expected = """
            {
                key: value
            }""";
        assertEquals(expected, Differ.generate(minimalFile1, minimalFile2, "stylish"));
    }

    // Тест для проверки, что статические поля
    // data1, data2 класса Differ корректно обнуляются
    @Test
    void testDifferHandlesStaticFieldsCorrectly() throws IOException {
        // Первый вызов
        Differ.generate(file3Json, file2Json, "stylish");
        Map<String, Object> firstData1 = Differ.getData1();
        Map<String, Object> firstData2 = Differ.getData2();

        // Второй вызов с yaml
        Differ.generate(file1Yaml, file3Yml, "stylish");
        Map<String, Object> secondData1 = Differ.getData1();
        Map<String, Object> secondData2 = Differ.getData2();

        assertNotEquals(firstData1, secondData1);
        assertNotEquals(firstData2, secondData2);
    }
}
