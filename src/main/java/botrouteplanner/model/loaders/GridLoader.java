package botrouteplanner.model.loaders;

import botrouteplanner.model.Grid;
import botrouteplanner.model.Point;
import botrouteplanner.model.Product;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import static botrouteplanner.model.loaders.ReaderUtils.splitLine;

public class GridLoader {
    public static Grid loadFromFile(String filepath) throws IOException, IllegalArgumentException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
        Grid grid = loadHeader(reader);
        loadGrid(grid, reader);
        return loadProducts(grid, reader);
    }

    private static Grid loadHeader(BufferedReader reader) throws IOException {
        Integer[] items = splitLine(reader.readLine());
        if (items.length != 3)
            throw new IllegalArgumentException("Error reading first line. Found " + items.length + " items but it has to be 3");
        int width = items[0];
        int height = items[1];
        int depth = items[2];
        return new Grid(width, height, depth);
    }

    private static void loadGrid(Grid grid, BufferedReader reader) throws IOException {
        for (int i=0; i<grid.getHeight(); i++) {
            String line = reader.readLine();
            if (line.length() != grid.getHeight())
                throw new IllegalArgumentException("Error reading line containing grid data no. " + (i+1) + " length is " + line.length() + " but it has to be " + grid.getWidth());
            for (int j=0; j<grid.getWidth(); j++)
                grid.setCell(new Point(j, i), line.charAt(j));
        }
    }

    private static Grid loadProducts(Grid grid, BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                Product product = Product.loadFromString(line);
                if (product.getDepth() >= grid.getDepth())
                    throw new IllegalArgumentException("Error: " + product + " is under max depth = " + (grid.getDepth() - 1));
                grid.addProduct(Product.loadFromString(line));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Error reading line containing product data: " + line + ": " + e.getMessage());
            }
        }
        return grid;
    }
}
