package botrouteplanner.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

@ToString
public class Path {
    private final LinkedList<Point> steps;
    @Getter
    @Setter
    private float travelTime;

    public Path() {
        steps = new LinkedList<>();
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
