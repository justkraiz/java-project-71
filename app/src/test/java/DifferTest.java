import hexlet.code.Differ;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DifferTest {
    static Path file1Path;
    static Path file2Path;

    @BeforeAll
    public static void setUp() {
        file1Path = Path.of("src/test/resources/file1.json");
        file2Path = Path.of("src/test/resources/file2.json");
    }

    @Test
    void testJsonComparison() throws Exception {
        Path file1 = file1Path;
        Path file2 = file2Path;

        String expectedOutput = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actualOutput = Differ.generate(file1, file2);
        assertEquals(expectedOutput, actualOutput);
    }

}
