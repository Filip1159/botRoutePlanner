import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.*;
import java.util.ArrayList;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Grid {
    private char[][] array;
    @Getter
    private int width, height, depth;
    private ArrayList<Product> products;

    public static Grid loadFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        String header = reader.readLine();
        String[] header_split = header.split(" ");
        int width = Integer.parseInt(header_split[0]);
        int height = Integer.parseInt(header_split[1]);
        int depth = Integer.parseInt(header_split[2]);
        char[][] array = new char[height][width];
        for (int i=0; i<height; i++) {
            String line = reader.readLine();
            for (int j=0; j<width; j++) {
                array[i][j] = line.charAt(j);
            }
        }
        ArrayList<Product> products = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            products.add(Product.loadFromString(line));
        }
        Grid result = new Grid();
        result.width = width;
        result.height = height;
        result.depth = depth;
        result.products = products;
        result.array = array;
        return result;
    }

    public void print() {
        for (int i=0; i<height; i++) {
            for (int j=0; j<width; j++) {
                System.out.print(array[i][j]);
            }
            System.out.println();
        }
        for (Product p : products)
            System.out.println(p);
    }

    public float getRouteWeight(char c) {
        switch (c) {
            case 'H': return 0.5f;
            case 'B': return 1;
            case 'S': return 2;
            case 'O': return 10000;
            default:
                throw new IllegalArgumentException("Illegal cell code: " + c);
        }
    }

    public float getTransitionCost(int x1, int y1, int x2, int y2) {
        float weight1 = getRouteWeight(array[y1][x1]);
        float weight2 = getRouteWeight(array[y2][x2]);
        return Math.max(weight1, weight2);
    }

    public float getWeightAt(int xPosition, int yPosition) {
        return array[yPosition][xPosition];
    }
}
