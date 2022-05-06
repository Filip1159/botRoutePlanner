package botrouteplanner.model;

import botrouteplanner.enumeration.CellType;
import lombok.Getter;
import lombok.ToString;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@ToString
public class Grid {
    public CellType[][] array;
    @Getter
    private final int width, height, depth;
    private final ArrayList<Product> products;

    public Grid(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        array = new CellType[height][width];
        products = new ArrayList<>();
    }

    public static Grid loadFromFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        Integer[] items = splitLine(reader.readLine());
        int width = items[0];
        int height = items[1];
        int depth = items[2];
        Grid grid = new Grid(width, height, depth);
        for (int i=0; i<height; i++) {
            String line = reader.readLine();
            for (int j=0; j<width; j++)
                grid.setCell(new Point(j, i), line.charAt(j));
        }
        String line;
        while ((line = reader.readLine()) != null)
            grid.addProduct(Product.loadFromString(line));
        return grid;
    }

    private static Integer[] splitLine(String line) {
        String[] items = line.split(" ");
        return Arrays.stream(items)
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
    }

    private void setCell(Point cell, char cellCode) {
        array[cell.y][cell.x] = charToCellType(cellCode);
    }

    private void addProduct(Product p) {
        products.add(p);
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

    public Optional<Product> getProductIfPresent(String name, Point location) {
        for (Product p : products) {
            if (p.getLocation().equals(location) && p.getName().equals(name))
                return Optional.of(p);
        }
        return Optional.empty();
    }

    private boolean areNeighbors(Point p1, Point p2) {
        return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) || (Math.abs(p1.x - p2.x) == 1 && p1.y == p2.y);
    }

    public boolean isAccessible(Point p) {
        return array[p.y][p.x] != CellType.O;
    }
}
