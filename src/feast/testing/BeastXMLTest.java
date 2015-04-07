package feast.testing;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

/**
 * Class which provides a test case which runs BEAST on a given XML file
 * and fails unless the required output files are generated.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public abstract class BeastXMLTest {

    public abstract String[] getBEASTArgs();

    public abstract List<Path> getExpectedOutputFiles();

    @Test
    public void test() throws IOException {

        beast.app.beastapp.BeastMain.main(getBEASTArgs());

        for (Path file : getExpectedOutputFiles()) {
            try (BufferedReader reader = Files.newBufferedReader(file)) {

            }
        }

    }
}
