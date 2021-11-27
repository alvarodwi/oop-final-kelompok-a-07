package team.emergence._15puzzle.util;

import java.io.InputStream;
import java.util.Objects;

public class Helper {
    public static InputStream loadResource(String path) {
        return Objects.requireNonNull(Helper.class.getClassLoader().getResourceAsStream(path));
    }
}
