package botrouteplanner;

import botrouteplanner.floodfill.PointFloodFill;
import botrouteplanner.floodfill.ProductFloodFill;
import botrouteplanner.model.*;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

@AllArgsConstructor
public class Planner {
    private Grid grid;
    private Job job;

    public static Planner load(String gridFilePath, String jobFilePath) throws IOException {
        Grid grid = Grid.loadFromFile(new File(gridFilePath));
        Job job = Job.loadFromFile(new File(jobFilePath));
        return new Planner(grid, job);
    }

    public void printRoute() {
        ProductFloodFill productFloodFill = new ProductFloodFill(grid);
        productFloodFill.setProductName(job.getProductName());
        productFloodFill.setStart(job.getStart());
        Path pathToProduct = productFloodFill.preparePath();
        Point productPoint = pathToProduct.getDestination();

        PointFloodFill pointFloodFill = new PointFloodFill(grid);
        pointFloodFill.setStart(productPoint);
        pointFloodFill.setTarget(job.getStation());
        Path pathToStation = pointFloodFill.preparePath();

        int totalTransitionsCount = pathToProduct.getTransitionsCount() + pathToStation.getTransitionsCount();
        float totalTravelTime = pathToProduct.getTravelTime() + pathToStation.getTravelTime();
        System.out.println(totalTransitionsCount);
        System.out.println(totalTravelTime);

        for (Point p : pathToProduct)
            System.out.println(p.x + " " + p.y);

        Iterator<Point> pathToStationIterator = pathToStation.iterator();
        pathToStationIterator.next();
        while (pathToStationIterator.hasNext()) {
            Point p = pathToStationIterator.next();
            System.out.println(p.x + " " + p.y);
        }
    }
}
