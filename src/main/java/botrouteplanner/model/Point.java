package botrouteplanner.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class Point {
    public int x, y;

    public Point[] getNeighbors() {
        return new Point[] {new Point(x, y-1),
                new Point(x+1, y),
                new Point(x, y+1),
                new Point(x-1, y)};
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
