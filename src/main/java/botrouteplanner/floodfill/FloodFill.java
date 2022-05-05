package botrouteplanner.floodfill;

import botrouteplanner.model.Grid;
import botrouteplanner.model.Path;
import botrouteplanner.model.Point;
import lombok.Setter;

import java.util.HashSet;

public abstract class FloodFill {
    @Setter
    protected Grid grid;
    @Setter
    protected Point start, target;
    private final float[][] costs;
    private float timer = 0;
    protected final HashSet<Point> floodedPoints, newFloodedPoints, pointsToRemove;

    public FloodFill(Grid grid) {
        this.grid = grid;
        costs = new float[grid.getHeight()][grid.getWidth()];
        floodedPoints = new HashSet<>();
        newFloodedPoints = new HashSet<>();
        pointsToRemove = new HashSet<>();
    }

    public void fill() {
        setUp();
        while (!isDone()) {
            tick();
            if (!isFullyFilled()) {
                for (Point p : floodedPoints)
                    tryToFloodNeighbors(p);
                floodedPoints.removeAll(pointsToRemove);
                pointsToRemove.clear();
                floodedPoints.addAll(newFloodedPoints);
                newFloodedPoints.clear();
            }
        }
        target = getTarget();
    }

    protected void tick() {
        timer += .5f;
    }

    protected abstract Point getTarget();

    protected abstract boolean isDone();

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

    protected void floodCell(Point floodedPoint) {
        costs[floodedPoint.y][floodedPoint.x] = timer;
        newFloodedPoints.add(floodedPoint);
    }

    public Path preparePath() {
        fill();
        Point currentStep = target;
        Path path = new Path();
        path.setTravelTime(timer);
        path.addStep(currentStep);
        while (!currentStep.equals(start)) {
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

    private Point[] getNeighbors(Point p) {
        return new Point[] {new Point(p.x, p.y-1),
                new Point(p.x+1, p.y),
                new Point(p.x, p.y+1),
                new Point(p.x-1, p.y)};
    }
}
