import hexlet.code.utils.Utils;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UtilsTest {

    @Test
    void testGetFileExtensionWithExtension() {
        Path filePath = Path.of("asd.json");
        String extension = Utils.getFileExtension(filePath);
        assertEquals("json", extension);
    }

    @Test
    void testGetFileExtensionWithoutExtension() {
        Path filePath = Path.of("example");
        String extension = Utils.getFileExtension(filePath);
        assertNull(extension);
    }

    @Test
    void testGetFileExtensionHiddenFile() {
        Path filePath = Path.of(".gitignore");
        String extension = Utils.getFileExtension(filePath);
        assertNull(extension);
    }

    @Test
    void testGetFileExtensionMultipleDots() {
        Path filePath = Path.of("asd.json.xml");
        String extension = Utils.getFileExtension(filePath);
        assertEquals("xml", extension);
    }

    @Test
    void testGetFileExtensionEmptyFileName() {
        Path filePath = Path.of("");
        String extension = Utils.getFileExtension(filePath);
        assertNull(extension);
    }


    // Абсолютный путь не подлежит преобразованию
    @Test
    void testAbsOrConvertPathAbsolute() {
        Path absolutePath = Path.of("/usr/local/bin");
        Path resultPath = Utils.absOrConvertPath(absolutePath);
        assertEquals(absolutePath, resultPath);
    }

    // Относительный путь подлежит преобразованию
    @Test
    void testAbsOrConvertPathRelative() {
        Path relativePath = Path.of("docs/manual.txt");
        Path resultPath = Utils.absOrConvertPath(relativePath);
        assertTrue(resultPath.isAbsolute());
        assertNotEquals(resultPath, relativePath);
        assertTrue(resultPath.toString().endsWith("docs/manual.txt"));
    }

    @Test
    void testAbsOrConvertPathEmptyPath() {
        Path emptyPath = Path.of("");
        Path resultPath = Utils.absOrConvertPath(emptyPath);
        assertNotEquals(emptyPath, resultPath);
        assertTrue(resultPath.isAbsolute());
    }


}
