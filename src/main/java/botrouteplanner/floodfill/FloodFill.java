package botrouteplanner.floodfill;

import botrouteplanner.model.Grid;
import botrouteplanner.model.Path;
import botrouteplanner.model.Point;
import lombok.Setter;

import java.util.HashSet;

public abstract class FloodFill {
    @Setter
    protected Grid grid;
    protected Point start;
    private final float[][] travelTimes;
    private float timer = 0;
    protected final HashSet<Point> floodedPoints, newFloodedPoints, pointsToRemove;

    public FloodFill(Grid grid) {
        this.grid = grid;
        travelTimes = new float[grid.getHeight()][grid.getWidth()];
        floodedPoints = new HashSet<>();
        newFloodedPoints = new HashSet<>();
        pointsToRemove = new HashSet<>();
    }

    public Path preparePath() {
        fill();
        Point currentStep = getDestination();
        Path path = new Path(currentStep, timer);
        while (!currentStep.equals(start)) {
            Point floodSource = getFloodSource(currentStep);
            path.addStep(floodSource);
            currentStep = floodSource;
        }
        return path;
    }

    private void fill() {
        reset();
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
    }

    private Point getFloodSource(Point floodedPoint) {
        Point[] neighbors = floodedPoint.getNeighbors();
        for (Point neighbor : neighbors) {
            if (grid.isAccessible(neighbor)) {
                float transitionCost = grid.getTransitionCost(floodedPoint, neighbor);
                if (getTravelTime(neighbor) + transitionCost == getTravelTime(floodedPoint))
                    return neighbor;
            }
        }
        throw new IllegalStateException("Internal error: cannot find flood source!");
    }

    private void reset() {
        for (int i=0; i<grid.getHeight(); i++)
            for (int j=0; j<grid.getWidth(); j++)
                travelTimes[i][j] = -1;
        floodedPoints.clear();
        travelTimes[start.y][start.x] = 0;
        floodedPoints.add(start);
        timer = 0;
    }

    private void tryToFloodNeighbors(Point p) {
        boolean hasFloodableNeighbors = false;
        for (Point neighbor : p.getNeighbors()) {
            if (isFloodable(neighbor)) {
                hasFloodableNeighbors = true;
                if (shouldBeFlooded(p, neighbor))
                    floodCell(neighbor);
            }
        }
        if (!hasFloodableNeighbors)
            pointsToRemove.add(p);
    }

    protected void tick() {
        timer += .5f;
    }

    private boolean shouldBeFlooded(Point flooder, Point candidate) {
        return getTravelTime(flooder) + grid.getTransitionCost(flooder, candidate) == timer;
    }

    protected void floodCell(Point floodedPoint) {
        if (!grid.isAccessible(floodedPoint))
            throw new IllegalArgumentException(floodedPoint + " is not accessible!");
        travelTimes[floodedPoint.y][floodedPoint.x] = timer;
        newFloodedPoints.add(floodedPoint);
    }

    private boolean isFloodable(Point p) {
        return grid.isAccessible(p) && getTravelTime(p) == -1;
    }

    private float getTravelTime(Point p) {
        if (!grid.isAccessible(p))
            throw new IllegalArgumentException(p + " is not accessible!");
        return travelTimes[p.y][p.x];
    }

    private boolean isFullyFilled() {
        return floodedPoints.isEmpty();
    }

    public void setStart(Point start) {
        if (!grid.isAccessible(start))
            throw new IllegalArgumentException(start + " is not accessible!");
        this.start = start;
    }

    protected abstract boolean isDone();

    protected abstract Point getDestination();
}
