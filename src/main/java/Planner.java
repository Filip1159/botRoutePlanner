public class Planner {
    private final FloodFill floodFill;

    public Planner(Grid grid) {
        floodFill = new FloodFill(grid);
    }

    public Path getPathToProduct(Point start, String productName) {
        floodFill.setStart(start);
        floodFill.setProductName(productName);
        floodFill.fill();
        Path path = floodFill.preparePath();
        for (int i=0; i<path.stepsCount(); i++) {
            System.out.println(path.getStep(i));
        }
        return path;
    }

    public Path getPathToPoint(Point start, Point target) {
        floodFill.setStart(start);
        floodFill.setTarget(target);
        floodFill.fillToTarget();
        floodFill.printCosts();
        Path path = floodFill.preparePath();
        for (int i=0; i<path.stepsCount(); i++) {
            System.out.println(path.getStep(i));
        }
        return path;
    }
}
