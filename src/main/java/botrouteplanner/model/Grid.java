package botrouteplanner.model;

import botrouteplanner.enumeration.CellType;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Optional;

@ToString
public class Grid {
    public CellType[][] array;
    @Getter
    private int width, height, depth;
    @Getter
    private final ArrayList<Product> products;

    public Grid(int width, int height, int depth) {
        setWidth(width);
        setHeight(height);
        setDepth(depth);
        array = new CellType[height][width];
        products = new ArrayList<>();
    }

    public void setCell(Point cell, char cellCode) {
        array[cell.y][cell.x] = charToCellType(cellCode);
    }

    public void addProduct(Product p) {
        products.add(p);
    }

    private static CellType charToCellType(char c) {
        return switch (c) {
            case 'H' -> CellType.H;
            case 'B' -> CellType.B;
            case 'S' -> CellType.S;
            case 'O' -> CellType.O;
            default -> throw new IllegalArgumentException("Illegal cellType code: " + c);
        };
    }

    public float getRouteWeight(CellType c) {
        return switch (c) {
            case H -> 0.5f;
            case B -> 1;
            case S -> 2;
            default -> throw new IllegalArgumentException("Illegal cell code: " + c);
        };
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
        if (!isInsideBounds(p)) return false;
        return array[p.y][p.x] != CellType.O;
    }

    public boolean isInsideBounds(Point p) {
        return isInsideBounds(p.x, p.y);
    }

    private boolean isInsideBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    private void setWidth(int width) {
        if (width <= 0) throw new IllegalArgumentException("Grid width must be positive, provided: " + width);
        this.width = width;
    }

    private void setHeight(int height) {
        if (height <= 0) throw new IllegalArgumentException("Grid height must be positive, provided: " + height);
        this.height = height;
    }

    private void setDepth(int depth) {
        if (depth <= 0) throw new IllegalArgumentException("Grid depth must be positive, provided: " + depth);
        this.depth = depth;
    }
}
