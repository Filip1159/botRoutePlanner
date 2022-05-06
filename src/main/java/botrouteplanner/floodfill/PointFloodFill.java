package botrouteplanner.floodfill;

import botrouteplanner.model.Grid;
import botrouteplanner.model.Point;

public class PointFloodFill extends FloodFill {
    private Point target;

    public PointFloodFill(Grid grid) {
        super(grid);
    }

    @Override
    protected boolean isDone() {
        for (Point p : floodedPoints)
            if (p.equals(target)) return true;
        return false;
    }

    @Override
    protected Point getDestination() {
        return target;
    }

    public void setTarget(Point target) {
        if (!grid.isAccessible(target)) throw new IllegalArgumentException(target + " is not accessible!");
        this.target = target;
    }
}
