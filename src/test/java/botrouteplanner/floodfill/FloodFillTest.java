package botrouteplanner.floodfill;

import botrouteplanner.model.Grid;
import botrouteplanner.model.Job;
import botrouteplanner.model.Path;
import botrouteplanner.model.Point;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class FloodFillTest {
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
    void shouldFindPathToPointBig() {
        List<Point> expectedSteps = List.of(new Point(6, 39),
            new Point(5, 39),
            new Point(5, 38),
            new Point(5, 37),
            new Point(4, 37),
            new Point(4, 36),
            new Point(3, 36),
            new Point(2, 36),
            new Point(2, 35),
            new Point(2, 34),
            new Point(2, 33),
            new Point(2, 32),
            new Point(2, 31),
            new Point(2, 30),
            new Point(2, 29),
            new Point(2, 28),
            new Point(1, 28),
            new Point(1, 27),
            new Point(0, 27),
            new Point(0, 26),
            new Point(0, 25),
            new Point(0, 24),
            new Point(0, 23),
            new Point(0, 22),
            new Point(0, 21),
            new Point(0, 20),
            new Point(0, 19),
            new Point(0, 18),
            new Point(0, 17),
            new Point(0, 16),
            new Point(0, 15),
            new Point(0, 14),
            new Point(0, 13),
            new Point(1, 13),
            new Point(2, 13),
            new Point(3, 13),
            new Point(3, 12),
            new Point(3, 11),
            new Point(3, 10),
            new Point(3, 9),
            new Point(3, 8),
            new Point(3, 7),
            new Point(3, 6),
            new Point(3, 5),
            new Point(4, 5),
            new Point(5 ,5),
            new Point(6, 5),
            new Point(6, 4),
            new Point(7, 4),
            new Point(8, 4),
            new Point(9, 4));
        PointFloodFill floodFill = new PointFloodFill(bigGrid);
        floodFill.setStart(new Point(6, 39));
        floodFill.setTarget(new Point(9, 4));
        Path path = floodFill.preparePath();
        assertIterableEquals(expectedSteps, path);
        assertEquals(50, path.getTransitionsCount());
        assertEquals(53.0, path.getTravelTime());
        assertEquals(51, path.stepsCount());
        assertEquals(new Point(6, 39), path.getStart());
        assertEquals(new Point(9,4), path.getDestination());
    }


}