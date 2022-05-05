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
    public CellType[][] array;
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
        CellType[][] array = new CellType[height][width];
        for (int i=0; i<height; i++) {
            String line = reader.readLine();
            for (int j=0; j<width; j++) {
                array[i][j] = charToCellType(line.charAt(j));
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

    private static CellType charToCellType(char c) {
        switch (c) {
            case 'H': return CellType.H;
            case 'B': return CellType.B;
            case 'S': return CellType.S;
            case 'O': return CellType.O;
            default: throw new IllegalArgumentException("Illegal cellType code: " + c);
        }
    }

    public float getRouteWeight(CellType c) {
        switch (c) {
            case H: return 0.5f;
            case B: return 1;
            case S: return 2;
            default:
                throw new IllegalArgumentException("Illegal cell code: " + c);
        }
    }

    public CellType getCellType(Point p) {
        return array[p.y][p.x];
    }

    public float getTransitionCost(Point p1, Point p2) {
        if (!areNeighbors(p1, p2))
            throw new IllegalArgumentException(String.format("%s and %s are not neighbors!", p1, p2));
        float weight1 = getRouteWeight(array[p1.y][p1.x]);
        float weight2 = getRouteWeight(array[p2.y][p2.x]);
        return Math.max(weight1, weight2);
    }

    public Product getProductOrNull(String name, Point location) {
        for (Product p : products) {
            if (p.getLocation().equals(location) && p.getName().equals(name))
                return p;
        }
        return null;
    }

    private boolean areNeighbors(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (Math.abs(p1.x - p2.x) == 1 && p1.y == p2.y);
    }

    public boolean isAccessible(Point p) {
        return array[p.y][p.x] != CellType.O;
    }
}
