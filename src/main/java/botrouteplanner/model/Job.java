package botrouteplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static botrouteplanner.model.loaders.ReaderUtils.splitLine;

@Getter
@AllArgsConstructor
@ToString
public class Job {
    private Point start, station;
    private String productName;

    public static Job loadFromFile(String filepath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
        Integer[] items = splitLine(reader.readLine());
        Point start = new Point(items[0], items[1]);
        items = splitLine(reader.readLine());
        Point station = new Point(items[0], items[1]);
        String productName = reader.readLine();
        reader.close();
        return new Job(start, station, productName);
    }
}
