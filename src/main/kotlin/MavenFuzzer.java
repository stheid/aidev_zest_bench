import org.apache.maven.model.io.DefaultModelReader;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@SuppressWarnings("unused")
public class MavenFuzzer {
    public static void fuzzerTestOneInput(byte[] input) {
        var stream = new ByteArrayInputStream(input);
        var reader = new DefaultModelReader();
        try {
            var model = reader.read(stream, null);
        } catch (IOException ignore) {
        }
    }
}