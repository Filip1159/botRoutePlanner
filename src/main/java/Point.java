import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@EqualsAndHashCode
public class Point {
    int x, y;

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
}
