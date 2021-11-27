package team.emergence._15puzzle.util;

import java.io.InputStream;
import java.net.URL;
import java.util.Objects;

public class ResourceLoader {
    public static InputStream loadResource(String path) {
        return Objects.requireNonNull(ResourceLoader.class.getClassLoader().getResourceAsStream(path));
    }

    public static URL loadResourceURL(String path) {
        return ResourceLoader.class.getClassLoader().getResource(path);
    }
}
