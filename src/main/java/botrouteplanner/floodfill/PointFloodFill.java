package botrouteplanner.floodfill;

import botrouteplanner.model.Grid;
import botrouteplanner.model.Point;
import lombok.Setter;

public class PointFloodFill extends FloodFill {
    @Setter
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
    protected Point getTarget() {
        return target;
    }
}
