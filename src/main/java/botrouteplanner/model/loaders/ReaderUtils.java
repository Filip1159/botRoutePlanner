package botrouteplanner.model.loaders;

import java.util.Arrays;

public class ReaderUtils {
    public static Integer[] splitLine(String line) {
        String[] items = line.split(" ");
        return Arrays.stream(items)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }
}
