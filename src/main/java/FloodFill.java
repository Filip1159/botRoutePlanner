public class FloodFill {
    private int xStart, yStart, xTarget, yTarget;
    int radius = 0;
    private final Grid grid;
    private final float[][] costs;

    public FloodFill(Grid grid) {
        this.grid = grid;
        costs = new float[grid.getHeight()][grid.getWidth()];
    }

    public void setRoute(int xStart, int yStart, int xTarget, int yTarget) {
        this.xStart = xStart;
        this.yStart = yStart;
        this.xTarget = xTarget;
        this.yTarget = yTarget;
    }

    public void fill() {
        resetWeights();
        costs[yStart][xStart] = 0;
        while (!isFullyFilled()) {
            String[] edges = {"NW", "NE", "SE", "SW"};
            for (String edge : edges)
                updateEdge(edge);
            radius++;
        }
    }

    private void updateEdge(String edge) {
        for (int i=0; i<radius; i++) {
            int xCoordinate, yCoordinate;
            switch (edge) {
                case "NW":
                    xCoordinate = xStart - radius + i;
                    yCoordinate = yStart - i;
                    break;
                case "NE":
                    xCoordinate = xStart + i;
                    yCoordinate = yStart - radius + i;
                    break;
                case "SE":
                    xCoordinate = xStart + radius - i;
                    yCoordinate = yStart + i;
                    break;
                default: // SW
                    xCoordinate = xStart - i;
                    yCoordinate = xStart + radius - i;
            }
            if (isInsideBounds(xCoordinate, yCoordinate) && costs[yCoordinate][xCoordinate] == -1)
                costs[yCoordinate][xCoordinate] = getMinCellCost(xCoordinate, yCoordinate);
        }
    }

    private boolean isInsideBounds(int x, int y) {
        return x >= 0 && x < grid.getWidth() && y >= 0 && y < grid.getHeight();
    }

    private boolean isOutOfBounds(int x, int y) {
        return !isInsideBounds(x, y);
    }

    private boolean isFullyFilled() {
        return isOutOfBounds(xStart - radius/2, yStart) && isOutOfBounds(xStart + radius/2, yStart) &&
                isOutOfBounds(xStart, yStart - radius/2) && isOutOfBounds(xStart, yStart + radius/2);
    }

    private void resetWeights() {
        for (int i=0; i<grid.getHeight(); i++) {
            for (int j=0; j<grid.getWidth(); j++) {
                costs[i][j] = -1;
            }
        }
    }

    private float getMinCellCost(int xPosition, int yPosition) {
        float minCost = Integer.MAX_VALUE;
        for (Direction d : Direction.values()) {
            int[] neighborCoordinates = getNeighborCoordinates(xPosition, yPosition, d);
            int neighborX = neighborCoordinates[0];
            int neighborY = neighborCoordinates[1];
            if (isInsideBounds(neighborX, neighborY)) {
                float fromNeighborCost = costs[neighborY][neighborX];
                if (fromNeighborCost >= 0) {
                    float transitionCost = grid.getTransitionCost(xPosition, yPosition, neighborX, neighborY);
                    fromNeighborCost += transitionCost;
                    if (fromNeighborCost < minCost) minCost = fromNeighborCost;
                }
            }
        }
        if (minCost == Integer.MAX_VALUE) return -1;
        return minCost;
    }

    private int[] getNeighborCoordinates(int xSource, int ySource, Direction direction) {
        switch (direction) {
            case N: return new int[] {xSource, ySource-1};
            case E: return new int[] {xSource+1, ySource};
            case S: return new int[] {xSource, ySource+1};
            default: return new int[] {xSource-1, ySource};
        }
    }

    private float calculateStepCost(int xStart, int yStart, int xTarget, int yTarget) {
        if (!areNeighbors(xStart, yStart, xTarget, yTarget))
            throw new IllegalArgumentException(String.format("(%d, %d) and (%d, %d) are not neighbors!", xStart, yStart, xTarget, yTarget));
        float startWeight = grid.getWeightAt(xStart, yStart);
        float neighborWeight = grid.getWeightAt(xTarget, yTarget);
        return Math.max(startWeight, neighborWeight);
    }

    private boolean areNeighbors(int xStart, int yStart, int xTarget, int yTarget) {
        return (Math.abs(xStart - xTarget) == 0 && Math.abs(yStart - yTarget) == 1) || (Math.abs(xStart - xTarget) == 1 && Math.abs(yStart - yTarget) == 0);
    }

    public void printCosts() {
        for (int i=0; i<grid.getHeight(); i++) {
            for (int j=0; j<grid.getWidth(); j++) {
                System.out.printf("%7.1f  ", costs[i][j]);
            }
            System.out.println();
        }
    }
}
