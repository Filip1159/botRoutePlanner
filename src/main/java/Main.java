import java.io.File;
import java.io.IOException;

public class Main {
    static final String GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/grid-2.txt";
    static final String JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/job-2.txt";

    public static void main(String[] args) throws IOException {
        Grid grid = Grid.loadFromFile(new File(GRID_PATH));
        grid.print();

        Job job = Job.loadFromFile(new File(JOB_PATH));
        grid.printLocal(job.getStart());
        System.out.println(job);

        Planner planner = new Planner(grid);
        Path path1 = planner.getPathToProduct(job.getStart(), job.getProductName());

        Point productPoint = path1.getTarget();
        System.out.println(productPoint);
        planner.getPathToPoint(new Point(50, 15), job.getStation());
    }
}
