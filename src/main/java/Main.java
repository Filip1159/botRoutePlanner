import java.io.File;
import java.io.IOException;

public class Main {
    static final String GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/grid-1.txt";
    static final String JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/job-1.txt";

    public static void main(String[] args) throws IOException {
        Grid grid = Grid.loadFromFile(new File(GRID_PATH));
        grid.print();

        Job job = Job.loadFromFile(new File(JOB_PATH));

        FloodFill floodFill = new FloodFill(grid);
        floodFill.setRoute(job.getXStart(), job.getYStart(), job.getXStation(), job.getYStation());
        floodFill.fill();
        floodFill.printCosts();
    }
}
