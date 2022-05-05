import lombok.Setter;

import java.util.HashSet;

public class ProductFloodFill extends FloodFill {
    private final HashSet<ContainerPicker> pickers;
    @Setter
    private String productName;

    public ProductFloodFill(Grid grid) {
        super(grid);
        pickers = new HashSet<>();
    }

    @Override
    protected boolean isDone() {
        for (ContainerPicker p : pickers) {
            if (p.didFinish()) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected Point getTarget() {
        for (ContainerPicker p : pickers) {
            if (p.didFinish()) {
                return p.getLocation();
            }
        }
        return null;
    }

    @Override
    protected void tick() {
        super.tick();
        for (ContainerPicker p : pickers)
            p.tickTimeElapsed();
    }

    @Override
    protected void floodCell(Point floodedPoint) {
        super.floodCell(floodedPoint);
        Product product;
        if ((product = grid.getProductOrNull(productName, floodedPoint)) != null) {
            pickers.add(new ContainerPicker(product, grid.getCellType(product.getLocation())));
        }
    }
}
