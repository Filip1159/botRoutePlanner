import lombok.Getter;

import java.io.*;

@Getter
public class Job {
    private int xStart, yStart, xStation, yStation;
    private String productName;

    public static Job loadFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String line = reader.readLine();
        String[] items = line.split(" ");
        int xStart = Integer.parseInt(items[0]);
        int yStart = Integer.parseInt(items[1]);
        line = reader.readLine();
        items = line.split(" ");
        int xStation = Integer.parseInt(items[0]);
        int yStation = Integer.parseInt(items[1]);
        String productName = reader.readLine();
        Job result = new Job();
        result.xStart = xStart;
        result.yStart = yStart;
        result.xStation = xStation;
        result.yStation = yStation;
        result.productName = productName;
        return result;
    }
}
