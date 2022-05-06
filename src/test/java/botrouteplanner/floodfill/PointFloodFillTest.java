package botrouteplanner.floodfill;

import botrouteplanner.model.Grid;
import botrouteplanner.model.Job;
import botrouteplanner.model.Path;
import botrouteplanner.model.Point;
import botrouteplanner.model.loaders.GridLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PointFloodFillTest {
    private Grid smallGrid, bigGrid, singlePointGrid;
    private Job smallJob, bigJob;

    @BeforeEach
    void setup() throws IOException {
        final String SINGLE_POINT_GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/test/resources/single-point-grid.txt";
        final String SMALL_GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/test/resources/grid-1.txt";
        final String SMALL_JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/test/resources/job-1.txt";
        final String BIG_GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/test/resources/grid-2.txt";
        final String BIG_JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/test/resources/job-2.txt";
        smallGrid = GridLoader.loadFromFile(SMALL_GRID_PATH);
        bigGrid = GridLoader.loadFromFile(BIG_GRID_PATH);
        smallJob = Job.loadFromFile(SMALL_JOB_PATH);
        bigJob = Job.loadFromFile(BIG_JOB_PATH);
        singlePointGrid = GridLoader.loadFromFile(SINGLE_POINT_GRID_PATH);
    }

    @Test
    void shouldTestSinglePointGrid() {
        PointFloodFill floodFill = new PointFloodFill(singlePointGrid);
        floodFill.setStart(new Point(0, 0));
        floodFill.setTarget(new Point(0, 0));
        Path path = floodFill.preparePath();
        assertIterableEquals(List.of(new Point(0, 0)), path);
        assertEquals(0, path.getTransitionsCount());
        assertEquals(1, path.stepsCount());
        assertEquals(0, path.getTravelTime());
    }

    @Test
    void shouldThrowWhenPointsNotAccessible() {
        PointFloodFill floodFill = new PointFloodFill(bigGrid);
        assertThrows(IllegalArgumentException.class, () -> floodFill.setStart(new Point(0, 2)));
        assertThrows(IllegalArgumentException.class, () -> floodFill.setStart(new Point(-1, 20)));
        assertThrows(IllegalArgumentException.class, () -> floodFill.setStart(new Point(0, -12)));
        assertThrows(IllegalArgumentException.class, () -> floodFill.setTarget(new Point(3, 0)));
        assertThrows(IllegalArgumentException.class, () -> floodFill.setTarget(new Point(0, -2)));
        assertThrows(IllegalArgumentException.class, () -> floodFill.setTarget(new Point(-10, 2)));
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

    @Test
    void exampleJob1() {
        List<Point> expectedPointsToStation = List.of(
                new Point(3, 2),
                new Point(3, 1),
                new Point(2, 1),
                new Point(1, 1),
                new Point(1, 0),
                new Point(0, 0));

        PointFloodFill pointFloodFill = new PointFloodFill(smallGrid);
        pointFloodFill.setStart(new Point(3, 2));
        pointFloodFill.setTarget(smallJob.getStation());
        Path pathToStation = pointFloodFill.preparePath();
        assertIterableEquals(expectedPointsToStation, pathToStation);
        assertEquals(5, pathToStation.getTransitionsCount());
        assertEquals(5, pathToStation.getTravelTime());
    }

    @Test
    void exampleJob2() {
        List<Point> expectedPointsToStation = List.of(
                new Point(50, 15),
                new Point(50, 16),
                new Point(49, 16),
                new Point(48, 16),
                new Point(48, 17),
                new Point(47, 17),
                new Point(46, 17),
                new Point(45, 17),
                new Point(44, 17),
                new Point(43, 17),
                new Point(42, 17),
                new Point(41, 17),
                new Point(40, 17),
                new Point(39, 17),
                new Point(38, 17),
                new Point(37, 17),
                new Point(36, 17),
                new Point(35, 17),
                new Point(34, 17),
                new Point(33, 17),
                new Point(32, 17),
                new Point(31, 17),
                new Point(30, 17),
                new Point(29, 17),
                new Point(29, 16),
                new Point(29, 15),
                new Point(28, 15),
                new Point(28, 14),
                new Point(27, 14),
                new Point(26, 14),
                new Point(25, 14),
                new Point(25, 13),
                new Point(24, 13),
                new Point(23, 13),
                new Point(22, 13),
                new Point(21, 13),
                new Point(20, 13),
                new Point(19, 13),
                new Point(18, 13),
                new Point(17, 13),
                new Point(16, 13),
                new Point(15, 13),
                new Point(14, 13),
                new Point(13, 13),
                new Point(12, 13),
                new Point(11, 13),
                new Point(10, 13),
                new Point(9, 13),
                new Point(8, 13),
                new Point(7, 13),
                new Point(6, 13),
                new Point(5, 13),
                new Point(4, 13),
                new Point(3, 13),
                new Point(3, 12),
                new Point(3, 11),
                new Point(3, 10),
                new Point(3, 9),
                new Point(3, 8),
                new Point(3, 7),
                new Point(3, 6),
                new Point(3, 5),
                new Point(3, 4),
                new Point(3, 3),
                new Point(3, 2),
                new Point(3, 1),
                new Point(2, 1),
                new Point(1, 1),
                new Point(1, 0),
                new Point(0, 0));

        PointFloodFill pointFloodFill = new PointFloodFill(bigGrid);
        pointFloodFill.setStart(new Point(50, 15));
        pointFloodFill.setTarget(bigJob.getStation());
        Path pathToStation = pointFloodFill.preparePath();
        assertIterableEquals(expectedPointsToStation, pathToStation);
        assertEquals(69, pathToStation.getTransitionsCount());
        assertEquals(66.5, pathToStation.getTravelTime());
    }

}