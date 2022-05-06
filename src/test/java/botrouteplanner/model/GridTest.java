package botrouteplanner.model;

import botrouteplanner.model.loaders.GridLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {
    private Grid smallGrid;

    @BeforeEach
    void setup() throws IOException {
        final String SMALL_GRID_PATH = getPath("src/test/resources/grid-1.txt");
        smallGrid = GridLoader.loadFromFile(SMALL_GRID_PATH);
    }

    private String getPath(String resource) {
        return new File(resource).getAbsolutePath();
    }

    @Test
    void testTransitionCosts() {
        assertEquals(.5, smallGrid.getTransitionCost(new Point(0, 0), new Point(1, 0)));
        assertEquals(2, smallGrid.getTransitionCost(new Point(1, 0), new Point(2, 0)));
        assertEquals(1, smallGrid.getTransitionCost(new Point(0, 1), new Point(1, 1)));
        assertThrows(IllegalArgumentException.class, () -> smallGrid.getTransitionCost(new Point(0, 1), new Point(1, 0)));
        assertThrows(IllegalArgumentException.class, () -> smallGrid.getTransitionCost(new Point(0, 1), new Point(0, 1)));
        assertThrows(IllegalArgumentException.class, () -> smallGrid.getTransitionCost(new Point(2, 2), new Point(2, 1)));
    }

    @Test
    void testGetProductIfPresent() {
        assertEquals(Optional.empty(), smallGrid.getProductIfPresent("ABCD", new Point(0, 0)));
        assertEquals(Optional.empty(), smallGrid.getProductIfPresent("P1", new Point(1, 1)));
        assertEquals(Optional.empty(), smallGrid.getProductIfPresent("P2", new Point(4, 7)));
        assertEquals(Optional.of(new Product("P1", new Point(3, 2), 1)), smallGrid.getProductIfPresent("P1", new Point(3, 2)));
        assertEquals(Optional.of(new Product("P1", new Point(0, 2), 2)), smallGrid.getProductIfPresent("P1", new Point(0, 2)));
        assertEquals(Optional.of(new Product("P2", new Point(1, 1), 2)), smallGrid.getProductIfPresent("P2", new Point(1, 1)));
    }

    @Test
    void testIsAccessible() {
        assertFalse(smallGrid.isAccessible(new Point(-1, 0)));
        assertFalse(smallGrid.isAccessible(new Point(2, -10)));
        assertFalse(smallGrid.isAccessible(new Point(2, 3)));
        assertFalse(smallGrid.isAccessible(new Point(4, 1)));
        assertFalse(smallGrid.isAccessible(new Point(2, 2)));
        assertTrue(smallGrid.isAccessible(new Point(3, 2)));
        assertTrue(smallGrid.isAccessible(new Point(1, 0)));
    }

}