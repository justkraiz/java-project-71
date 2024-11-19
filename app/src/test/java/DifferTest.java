import hexlet.code.Differ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import utils.Reader;

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
    private static String expectedStylishFixture;
    private static String expectedJsonFixture;
    private static String expectedPlainFixture;

    @BeforeAll
    public static void setUp() throws IOException {
        file1Json = "src/test/resources/file1_new.json";
        file2Json = "src/test/resources/file2_new.json";
        file3Json = "src/test/resources/file3_new.json";
        file1Yaml = "src/test/resources/file1_new.yml";
        file2Yaml = "src/test/resources/file2_new.yaml";
        file3Yml = "src/test/resources/file3_new.yml";
        emptyFile = "src/test/resources/empty.json";

        expectedStylishFixture = Reader.readFile("src/test/resources/expected_stylish_format.txt");
        expectedJsonFixture = Reader.readFile("src/test/resources/expected_Json_format.txt");
        expectedPlainFixture = Reader.readFile("src/test/resources/expected_plain_format.txt");
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
    void testDifferWithTwoArgsOnly() throws IOException {
        assertNotNull(Differ.generate(file1Json, file2Json));
        assertNotNull(Differ.generate(file1Yaml, file2Yaml));
        assertEquals(expectedStylishFixture, Differ.generate(file1Yaml, file2Yaml));
        assertEquals(expectedStylishFixture, Differ.generate(file1Json, file2Json));
    }

    @Test
    void testDifferWithTwoArgsDefaultFormat() throws IOException {
        assertEquals(expectedStylishFixture, Differ.generate(file1Json, file2Json));
    }

    @Test
    void testDifferJsonFormat() throws IOException {
        assertEquals(expectedJsonFixture, Differ.generate(file1Json, file2Json, "json"));
    }

    @Test
    void testDifferPlainFormatJsonConfigs() throws IOException {
        assertEquals(expectedPlainFixture, Differ.generate(file1Json, file2Json, "plain"));
    }

    @Test
    void testDifferStylishFormatJsonConfigs() throws IOException {
        assertEquals(expectedStylishFixture, Differ.generate(file1Json, file2Json, "stylish"));
    }

    @Test
    void testDifferStylishFormatYamlConfigs() throws IOException {
        assertEquals(expectedStylishFixture, Differ.generate(file1Yaml, file2Yaml, "stylish"));
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
