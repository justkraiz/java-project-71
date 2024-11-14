import hexlet.code.Parser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    void testParseNullFile() {
        assertThrows(NullPointerException.class, () -> Parser.parse(null, null));
    }
}
