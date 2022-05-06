package botrouteplanner;

import botrouteplanner.model.Path;
import botrouteplanner.model.Point;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlannerTest {
    final String SMALL_GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/grid-1.txt";
    final String SMALL_JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/job-1.txt";
    final String BIG_GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/grid-2.txt";
    final String BIG_JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/job-2.txt";

    @Test
    void exampleJob1() throws IOException {
        List<Point> expectedPoints = List.of(
                new Point(1, 1),
                new Point(2, 1),
                new Point(3, 1),
                new Point(3, 2),
                new Point(3, 1),
                new Point(2, 1),
                new Point(1, 1),
                new Point(1, 0),
                new Point(0, 0));

        Planner planner = Planner.load(SMALL_GRID_PATH, SMALL_JOB_PATH);
        Path path = planner.createRoute();
        assertIterableEquals(expectedPoints, path);
        assertEquals(8, path.getTransitionsCount());
        assertEquals(10.5, path.getTravelTime());
    }

    @Test
    void exampleJob2() throws IOException {
        List<Point> expectedPoints = List.of(
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

        Planner planner = Planner.load(BIG_GRID_PATH, BIG_JOB_PATH);
        Path path = planner.createRoute();
        assertIterableEquals(expectedPoints, path);
        assertEquals(81, path.getTransitionsCount());
        assertEquals(82.0, path.getTravelTime());
    }

    @Test
    void shouldThrowIOExceptionOnInvalidFile() {
        assertThrows(IOException.class, () -> Planner.load("ABCD", "ABCD"));
    }

}