package botrouteplanner.floodfill;

import botrouteplanner.model.Grid;
import botrouteplanner.model.Job;
import botrouteplanner.model.Path;
import botrouteplanner.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductFloodFillTest {
    private Grid smallGrid, bigGrid;
    private Job smallJob, bigJob;

    @BeforeEach
    void setup() throws IOException {
        final String SMALL_GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/grid-1.txt";
        final String SMALL_JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/job-1.txt";
        final String BIG_GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/grid-2.txt";
        final String BIG_JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/job-2.txt";
        smallGrid = Grid.loadFromFile(SMALL_GRID_PATH);
        bigGrid = Grid.loadFromFile(BIG_GRID_PATH);
        smallJob = Job.loadFromFile(SMALL_JOB_PATH);
        bigJob = Job.loadFromFile(BIG_JOB_PATH);
    }

    @Test
    void shouldThrowOnProductsOnInaccessibleCells() {
        ProductFloodFill productFloodFill = new ProductFloodFill(bigGrid);
        assertThrows(IllegalArgumentException.class, () -> productFloodFill.setProductName("10"));
        assertThrows(IllegalArgumentException.class, () -> productFloodFill.setProductName("67"));
        assertThrows(IllegalArgumentException.class, () -> productFloodFill.setProductName("158"));
        assertThrows(IllegalArgumentException.class, () -> productFloodFill.setProductName("198"));
        assertThrows(IllegalArgumentException.class, () -> productFloodFill.setProductName(""));
        assertThrows(IllegalArgumentException.class, () -> productFloodFill.setProductName("ABCD"));

    }

    @Test
    void shouldFindPathToProductBig() {
        List<Point> expectedPointsToProduct = List.of(
                new Point(99, 21),
                new Point(98, 21),
                new Point(98, 22),
                new Point(98, 23),
                new Point(98, 24),
                new Point(98, 25),
                new Point(97, 25),
                new Point(97, 26),
                new Point(97, 27),
                new Point(97, 28),
                new Point(97, 29),
                new Point(96, 29),
                new Point(96, 30),
                new Point(95, 30),
                new Point(95, 31),
                new Point(95, 32),
                new Point(94, 32),
                new Point(94, 33),
                new Point(94, 34),
                new Point(93, 34),
                new Point(92, 34),
                new Point(91, 34),
                new Point(90, 34),
                new Point(89, 34),
                new Point(88, 34),
                new Point(87, 34),
                new Point(86, 34),
                new Point(85, 34),
                new Point(84, 34),
                new Point(84, 35),
                new Point(83, 35),
                new Point(83, 36),
                new Point(82, 36),
                new Point(81, 36),
                new Point(80, 36),
                new Point(79, 36),
                new Point(78, 36));

        ProductFloodFill productFloodFill = new ProductFloodFill(bigGrid);
        productFloodFill.setProductName("107");
        productFloodFill.setStart(new Point(99, 21));
        Path pathToProduct = productFloodFill.preparePath();
        assertIterableEquals(expectedPointsToProduct, pathToProduct);
        assertEquals(36, pathToProduct.getTransitionsCount());
        assertEquals(45.0, pathToProduct.getTravelTime());
    }

    @Test
    void exampleJob1() {
        List<Point> expectedPointsToProduct = List.of(
                new Point(1, 1),
                new Point(2, 1),
                new Point(3, 1),
                new Point(3, 2));

        ProductFloodFill productFloodFill = new ProductFloodFill(smallGrid);
        productFloodFill.setProductName(smallJob.getProductName());
        productFloodFill.setStart(smallJob.getStart());
        Path pathToProduct = productFloodFill.preparePath();
        assertIterableEquals(expectedPointsToProduct, pathToProduct);
        assertEquals(3, pathToProduct.getTransitionsCount());
        assertEquals(5.5, pathToProduct.getTravelTime());
    }

    @Test
    void exampleJob2() {
        List<Point> expectedPointsToProduct = List.of(
                new Point(52, 25),
                new Point(52, 24),
                new Point(51, 24),
                new Point(51, 23),
                new Point(51, 22),
                new Point(51, 21),
                new Point(51, 20),
                new Point(51, 19),
                new Point(51, 18),
                new Point(50, 18),
                new Point(50, 17),
                new Point(50, 16),
                new Point(50, 15));


        ProductFloodFill productFloodFill = new ProductFloodFill(bigGrid);
        productFloodFill.setProductName(bigJob.getProductName());
        productFloodFill.setStart(bigJob.getStart());
        Path pathToProduct = productFloodFill.preparePath();
        assertIterableEquals(expectedPointsToProduct, pathToProduct);
        assertEquals(12, pathToProduct.getTransitionsCount());
        assertEquals(15.5, pathToProduct.getTravelTime());
    }
}