package botrouteplanner.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Iterator;
import java.util.LinkedList;

@ToString
public class Path implements Iterable<Point> {
    private final LinkedList<Point> steps;
    @Getter
    @Setter
    private float travelTime;
    @Getter
    private Point destination;
    @Getter
    private int transitionsCount;

    public Path(Point destination, float travelTime) {
        this();
        this.destination = destination;
        addStep(destination);
        this.travelTime = travelTime;
    }

    public Path() {
        steps = new LinkedList<>();
        transitionsCount = 0;
        travelTime = 0;
    }

    public void addStep(Point p) {
        if (destination == null)
            destination = p;
        else
            transitionsCount++;
        steps.add(0, p);
    }

    public Point getStep(int index) {
        return steps.get(index);
    }

    public int stepsCount() {
        return transitionsCount+1;
    }

    public Point getStart() {
        return steps.get(0);
    }

    @Override
    public Iterator<Point> iterator() {
        return steps.iterator();
    }
}
