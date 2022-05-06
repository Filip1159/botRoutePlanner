import botrouteplanner.Planner;
import botrouteplanner.model.Path;

import java.io.IOException;

public class Main {
    static final String GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/grid-2.txt";
    static final String JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/job-2.txt";

    public static void main(String[] args) throws IOException {
        Planner planner = Planner.load(GRID_PATH, JOB_PATH);
        Path path = planner.createRoute();
        System.out.println(path.getTransitionsCount());
        System.out.println(path.getTravelTime());
        path.printFormatted();
    }
}
