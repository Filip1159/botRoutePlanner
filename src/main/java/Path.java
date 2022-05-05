import java.util.ArrayList;

public class Path {
    private final Grid grid;
    private final ArrayList<Point> steps;
    private float travelTime;

    public Path(Grid grid) {
        this.grid = grid;
        steps = new ArrayList<>();
        travelTime = 0;
    }

    public void addStep(Point p) {
        if (steps.size() > 0)
            travelTime += grid.getTransitionCost(p, steps.get(0));
        steps.add(0, p);
    }

    public Point getStep(int index) {
        return steps.get(index);
    }

    public int stepsCount() {
        return steps.size();
    }

    public Point getTarget() {
        return steps.get(steps.size()-1);
    }

    public Point getStart() {
        return steps.get(0);
    }
}
