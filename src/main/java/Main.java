import botrouteplanner.floodfill.PointFloodFill;
import botrouteplanner.floodfill.ProductFloodFill;
import botrouteplanner.model.Grid;
import botrouteplanner.model.Job;
import botrouteplanner.model.Path;
import botrouteplanner.model.Point;

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
        Point productPoint = path1.getDestination();

        PointFloodFill pointFloodFill = new PointFloodFill(grid);
        pointFloodFill.setStart(productPoint);
        pointFloodFill.setTarget(job.getStation());
        Path path2 = pointFloodFill.preparePath();

        int totalTransitionsCount = path1.getTransitionsCount() + path2.getTransitionsCount();
        float totalTravelTime = path1.getTravelTime() + path2.getTravelTime();
        System.out.println(totalTransitionsCount);
        System.out.println(totalTravelTime);

        for (Point p : path1)
            System.out.println(p.x + " " + p.y);

        for (Point p : path2)
            System.out.println(p.x + " " + p.y);
    }
}
