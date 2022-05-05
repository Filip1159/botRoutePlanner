import java.util.ArrayList;
import java.util.HashSet;

public class FloodFill {
    private final Grid grid;
    private final Job job;
    private final float[][] costs;
    private float timer = 0;
    private final HashSet<Point> floodedPoints;
    private final HashSet<Point> newFloodedPoints;
    private final HashSet<Point> pointsToRemove;
    private Point foundProductPoint;
    private final HashSet<ContainerPicker> pickers;

    public FloodFill(Grid grid, Job job) {
        this.grid = grid;
        this.job = job;
        floodedPoints = new HashSet<>();
        newFloodedPoints = new HashSet<>();
        pointsToRemove = new HashSet<>();
        pickers = new HashSet<>();
        costs = new float[grid.getHeight()][grid.getWidth()];
    }

    public void fill() {
        resetWeights();
        costs[job.getStart().y][job.getStart().x] = 0;
        floodedPoints.add(job.getStart());
        timer = 0;
        while (!checkPickingFinishedAndSavePickedProduct()) {
            timer += 0.5;
            tickContainerPickers();
            if (!isFullyFilled()) {
                for (Point p : floodedPoints)
                    tryToFloodNeighbors(p);
                floodedPoints.removeAll(pointsToRemove);
                floodedPoints.addAll(newFloodedPoints);
            }
        }
    }

    private void tryToFloodNeighbors(Point p) {
        boolean hasFloodableNeighbors = false;
        for (Point neighbor : getNeighbors(p)) {
            if (isInsideBounds(neighbor) && isFloodable(neighbor)) {
                hasFloodableNeighbors = true;
                if (shouldBeFlooded(p, neighbor))
                    floodCell(neighbor);
            }
        }
        if (!hasFloodableNeighbors)
            pointsToRemove.add(p);
    }

    private boolean shouldBeFlooded(Point flooder, Point candidate) {
        return areEqual(costs[flooder.y][flooder.x] + grid.getTransitionCost(flooder, candidate), timer);
    }

    private void floodCell(Point floodedPoint) {
        costs[floodedPoint.y][floodedPoint.x] = timer;
        Product product;
        if ((product = grid.getProductOrNull(job.getProductName(), floodedPoint)) != null) {
            pickers.add(new ContainerPicker(product, grid.getCellType(product.getLocation())));
        }
        newFloodedPoints.add(floodedPoint);
    }

    private void tickContainerPickers() {
        for (ContainerPicker p : pickers)
            p.tickTimeElapsed();
    }

    private boolean checkPickingFinishedAndSavePickedProduct() {
        for (ContainerPicker p : pickers) {
            if (p.didFinish()) {
                foundProductPoint = p.getLocation();
                return true;
            }
        }
        return false;
    }

    public ArrayList<Point> preparePath() {
        Point currentStep = foundProductPoint;
        ArrayList<Point> path = new ArrayList<>();
        path.add(currentStep);
        while (!currentStep.equals(job.getStart())) {
            Point[] neighbors = getNeighbors(currentStep);
            for (Point neighbor : neighbors) {
                if (!isOutOfBounds(neighbor) && !grid.isOutOfService(neighbor)) {
                    float transitionCost = grid.getTransitionCost(currentStep, neighbor);
                    if (areEqual(costs[neighbor.y][neighbor.x] + transitionCost, costs[currentStep.y][currentStep.x])) {
                        path.add(neighbor);
                        currentStep = neighbor;
                        break;
                    }
                }
            }
        }
        return path;
    }

    private boolean isFloodable(Point p) {
        return !grid.isOutOfService(p) && costs[p.y][p.x] == -1;
    }

    private boolean isOutOfBounds(int x, int y) {
        return !isInsideBounds(x, y);
    }

    private boolean isOutOfBounds(Point p) {
        return !isInsideBounds(p);
    }

    private boolean isInsideBounds(Point p) {
        return isInsideBounds(p.x, p.y);
    }

    private boolean isInsideBounds(int x, int y) {
        return x >= 0 && x < grid.getWidth() && y >= 0 && y < grid.getHeight();
    }

    private boolean isFullyFilled() {
        return floodedPoints.isEmpty();
    }

    private void resetWeights() {
        for (int i=0; i<grid.getHeight(); i++) {
            for (int j=0; j<grid.getWidth(); j++) {
                costs[i][j] = -1;
            }
        }
    }

    public void printCosts() {
        for (int i=0; i<grid.getHeight(); i++) {
            for (int j=0; j<grid.getWidth(); j++) {
                System.out.printf("%7.1f  ", costs[i][j]);
            }
            System.out.println();
        }
    }

    private Point[] getNeighbors(Point p) {
        return new Point[] {getNeighborCoordinates(p, Direction.N),
                getNeighborCoordinates(p, Direction.E),
                getNeighborCoordinates(p, Direction.S),
                getNeighborCoordinates(p, Direction.W)};
    }

    private Point getNeighborCoordinates(Point p, Direction direction) {
        switch (direction) {
            case N: return new Point(p.x, p.y-1);
            case E: return new Point(p.x+1, p.y);
            case S: return new Point(p.x, p.y+1);
            default: return new Point(p.x-1, p.y);
        }
    }

    private boolean areEqual(float f1, float f2) {
        return Math.abs(f1 - f2) < 0.1;
    }
}
