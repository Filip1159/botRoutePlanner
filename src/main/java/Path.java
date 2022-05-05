import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;

@ToString
public class Path {
    private final ArrayList<Point> steps;
    @Getter
    @Setter
    private float travelTime;

    public Path() {
        steps = new ArrayList<>();
        travelTime = 0;
    }

    public void addStep(Point p) {
        steps.add(0, p);
    }

    public Point getStep(int index) {
        return steps.get(index);
    }

    public int stepsCount() {
        return steps.size();
    }

    public int transitionsCount() {
        return steps.size()-1;
    }

    public Point getTarget() {
        return steps.get(steps.size()-1);
    }

    public Point getStart() {
        return steps.get(0);
    }
}
