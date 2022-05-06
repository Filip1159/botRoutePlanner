package botrouteplanner;

import botrouteplanner.floodfill.PointFloodFill;
import botrouteplanner.floodfill.ProductFloodFill;
import botrouteplanner.model.Grid;
import botrouteplanner.model.Job;
import botrouteplanner.model.Path;
import botrouteplanner.model.Point;
import botrouteplanner.model.loaders.GridLoader;
import lombok.AllArgsConstructor;

import java.io.IOException;

@AllArgsConstructor
public class Planner {
    private Grid grid;
    private Job job;

    public static Planner load(String gridFilePath, String jobFilePath) throws IOException, IllegalArgumentException {
        Grid grid = GridLoader.loadFromFile(gridFilePath);
        Job job = Job.loadFromFile(jobFilePath);
        return new Planner(grid, job);
    }

    public Path createRoute() {
        ProductFloodFill productFloodFill = new ProductFloodFill(grid);
        productFloodFill.setProductName(job.getProductName());
        productFloodFill.setStart(job.getStart());
        Path pathToProduct = productFloodFill.preparePath();
        Point productPoint = pathToProduct.getDestination();

        PointFloodFill pointFloodFill = new PointFloodFill(grid);
        pointFloodFill.setStart(productPoint);
        pointFloodFill.setTarget(job.getStation());
        Path pathToStation = pointFloodFill.preparePath();

        pathToProduct.append(pathToStation);
        return pathToProduct;
    }
}
