import lombok.Getter;

import java.util.HashSet;

public class FloodFill {
    @Getter
    private Point start, target;
    private final HashSet<Point> floodingPoints;
    private final Grid grid;
    private final float[][] costs;

    public FloodFill(Grid grid) {
        this.grid = grid;
        floodingPoints = new HashSet<>();
        costs = new float[grid.getHeight()][grid.getWidth()];
    }

    public void setRoute(int xStart, int yStart, int xTarget, int yTarget) {
        this.start = new Point(xStart, yStart);
        this.target = new Point(xTarget, yTarget);
    }

    public void fill() {
        resetWeights();
        costs[start.y][start.x] = 0;
        floodingPoints.add(start);
        float timer = 0;
        while (!isFullyFilled()) {
            timer += 0.5;
            HashSet<Point> newFloodingPoints = new HashSet<>();
            HashSet<Point> pointsToRemove = new HashSet<>();
            for (Point p : floodingPoints) {
                boolean hasFloodableNeighbors = false;
                for (Point neighbor : getNeighbors(p)) {
                    if (isInsideBounds(neighbor) && isFloodable(neighbor)) {
                        hasFloodableNeighbors = true;
                        if (areEqual(costs[p.y][p.x] + grid.getTransitionCost(p, neighbor), timer)) {
                            costs[neighbor.y][neighbor.x] = timer;
                            newFloodingPoints.add(neighbor);
                        }
                    }
                }
                if (!hasFloodableNeighbors) {
                    pointsToRemove.add(p);
                }
            }
            floodingPoints.removeAll(pointsToRemove);
            floodingPoints.addAll(newFloodingPoints);
        }
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
        return floodingPoints.isEmpty();
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
