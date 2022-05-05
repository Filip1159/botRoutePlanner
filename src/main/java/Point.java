import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Point {
    int x, y;

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Point)) return false;
        Point p = (Point) other;
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
