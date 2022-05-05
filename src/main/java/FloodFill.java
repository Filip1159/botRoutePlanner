import lombok.Setter;

import java.util.HashSet;

public class FloodFill {
    @Setter
    private Grid grid;
    @Setter
    private Point start, target;
    @Setter
    private String productName;
    private final float[][] costs;
    private float timer = 0;
    private final HashSet<Point> floodedPoints, newFloodedPoints, pointsToRemove;
    private Point foundProductPoint;
    private final HashSet<ContainerPicker> pickers;

    public FloodFill(Grid grid) {
        this.grid = grid;
        costs = new float[grid.getHeight()][grid.getWidth()];
        floodedPoints = new HashSet<>();
        newFloodedPoints = new HashSet<>();
        pointsToRemove = new HashSet<>();
        pickers = new HashSet<>();
    }

    public void fill() {
        setUp();
        while (!checkPickingFinishedAndSavePickedProduct()) {
            timer += 0.5;
            tickContainerPickers();
            if (!isFullyFilled()) {
                for (Point p : floodedPoints)
                    tryToFloodNeighbors(p);
                floodedPoints.removeAll(pointsToRemove);
                pointsToRemove.clear();
                floodedPoints.addAll(newFloodedPoints);
                newFloodedPoints.clear();
            }
        }
    }

    public void fillToTarget() {
        setUp();
        while (!isTargetReached()) {
            timer += 0.5;
            for (Point p : floodedPoints)
                tryToFloodNeighbors(p);
            floodedPoints.removeAll(pointsToRemove);
            pointsToRemove.clear();
            floodedPoints.addAll(newFloodedPoints);
            newFloodedPoints.clear();
            System.out.println(floodedPoints);
        }
    }

    private boolean isTargetReached() {
        foundProductPoint = target;
        for (Point p : floodedPoints)
            if (p.equals(target)) return true;
        return false;
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
        return costs[flooder.y][flooder.x] + grid.getTransitionCost(flooder, candidate) == timer;
    }

    private void floodCell(Point floodedPoint) {
        costs[floodedPoint.y][floodedPoint.x] = timer;
        Product product;
        if ((product = grid.getProductOrNull(productName, floodedPoint)) != null) {
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

    public Path preparePath() {
        Point currentStep = foundProductPoint;
        Path path = new Path(grid);
        path.addStep(currentStep);
        while (!currentStep.equals(start)) {
            System.out.println(currentStep);
            printLocal(currentStep);
            Point[] neighbors = getNeighbors(currentStep);
            for (Point neighbor : neighbors) {
                if (!isOutOfBounds(neighbor) && grid.isAccessible(neighbor)) {
                    float transitionCost = grid.getTransitionCost(currentStep, neighbor);
                    if (costs[neighbor.y][neighbor.x] + transitionCost == costs[currentStep.y][currentStep.x]) {
                        path.addStep(neighbor);
                        currentStep = neighbor;
                        break;
                    }
                }
            }
        }
        return path;
    }

    public void printLocal(Point p) {
        int startX = p.x - 2;
        if (startX < 0) startX = 0;
        int startY = p.y - 2;
        if (startY < 0) startY = 0;
        int endX = p.x + 3;
        if (endX > grid.getWidth()) endX = grid.getWidth();
        int endY = p.y + 3;
        if (endY > grid.getHeight()) endY = grid.getHeight();
        for (int i=startY; i<endY; i++) {
            for (int j=startX; j<endX; j++) {
                System.out.print(costs[i][j] + "  ");
            }
            System.out.println();
        }
    }

    private boolean isFloodable(Point p) {
        return grid.isAccessible(p) && costs[p.y][p.x] == -1;
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

    private void setUp() {
        for (int i=0; i<grid.getHeight(); i++)
            for (int j=0; j<grid.getWidth(); j++)
                costs[i][j] = -1;
        floodedPoints.clear();
        costs[start.y][start.x] = 0;
        floodedPoints.add(start);
        timer = 0;
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
        return new Point[] {new Point(p.x, p.y-1),
                new Point(p.x+1, p.y),
                new Point(p.x, p.y+1),
                new Point(p.x-1, p.y)};
    }
}
