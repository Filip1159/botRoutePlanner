import java.io.File;
import java.io.IOException;

public class Main {
    static final String GRID_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/grid-2.txt";
    static final String JOB_PATH = "C:/Users/PAVILION/IdeaProjects/BotRoutePlanner/src/main/resources/job-2.txt";

    public static void main(String[] args) throws IOException {
        Grid grid = Grid.loadFromFile(new File(GRID_PATH));
        Job job = Job.loadFromFile(new File(JOB_PATH));

        ProductFloodFill productFloodFill = new ProductFloodFill(grid);
        productFloodFill.setProductName(job.getProductName());
        productFloodFill.setStart(job.getStart());
        Path path1 = productFloodFill.preparePath();
        Point productPoint = path1.getTarget();

        PointFloodFill pointFloodFill = new PointFloodFill(grid);
        pointFloodFill.setStart(productPoint);
        pointFloodFill.setTarget(job.getStation());
        Path path2 = pointFloodFill.preparePath();

        System.out.println(path1.transitionsCount() + path2.transitionsCount());
        System.out.println(path1.getTravelTime() + path2.getTravelTime());
        for (int i=0; i<path1.stepsCount(); i++) {
            Point step = path1.getStep(i);
            System.out.println(step.x + " " + step.y);
        }
        for (int i=0; i<path2.stepsCount(); i++) {
            Point step = path2.getStep(i);
            System.out.println(step.x + " " + step.y);
        }
    }
}
