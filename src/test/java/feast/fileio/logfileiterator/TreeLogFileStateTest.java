package feast.fileio.logfileiterator;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreeLogFileStateTest {

    @Test
    public void testRegExp() {
        String line = "tree     STATE_2600 = [&R] ((A,B),C); ";
        Matcher m = TreeLogFileState.logLinePattern.matcher(line);

        boolean result = m.find();

        assertTrue(result);
        assertEquals("2600", m.group(1));
        assertEquals("[&R]", m.group(2));
        assertEquals("((A,B),C); ", m.group(3));
    }
}
