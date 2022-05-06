package botrouteplanner.floodfill;

import botrouteplanner.model.Grid;
import botrouteplanner.model.Point;
import botrouteplanner.model.Product;
import lombok.Setter;

import java.util.HashSet;
import java.util.Optional;

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
        for (ContainerPicker p : pickers)
            if (p.didFinish())
                return true;
        return false;
    }

    @Override
    protected Point getDestination() {
        for (ContainerPicker p : pickers)
            if (p.didFinish())
                return p.getLocation();
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
        Optional<Product> optionalProduct = grid.getProductIfPresent(productName, floodedPoint);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            pickers.add(new ContainerPicker(product, grid.getCellType(product.getLocation())));
        }
    }
}
