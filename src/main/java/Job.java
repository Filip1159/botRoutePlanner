import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.*;
import java.util.Arrays;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Job {
    private Point start, station;
    private String productName;

    public static Job loadFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        Integer[] items = splitLine(reader.readLine());
        Point start = new Point(items[0], items[1]);
        items = splitLine(reader.readLine());
        Point station = new Point(items[0], items[1]);
        String productName = reader.readLine();
        return new Job(start, station, productName);
    }

    private static Integer[] splitLine(String line) {
        String[] items = line.split(" ");
        return Arrays.stream(items)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }
}
