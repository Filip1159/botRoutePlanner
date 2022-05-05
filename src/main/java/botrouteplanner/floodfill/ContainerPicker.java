package botrouteplanner.floodfill;

import botrouteplanner.enumeration.CellType;
import botrouteplanner.model.Point;
import botrouteplanner.model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class ContainerPicker {
    private Product product;
    private CellType cellType;
    private int waitingTime;
    private float timeElapsed;

    public ContainerPicker(Product product, CellType cellType) {
        this.product = product;
        this.cellType = cellType;
        timeElapsed = 0;
        waitingTime = getWaitingTime();
    }

    public boolean didFinish() {
        return waitingTime == timeElapsed;
    }

    public void tickTimeElapsed() {
        timeElapsed += .5f;
    }

    public Point getLocation() {
        return product.getLocation();
    }

    private int getWaitingTime() {
        switch (cellType) {
            case H: return 3 * product.getDepth() + 4;
            case B: return 2 * product.getDepth() + 2;
            case S: return product.getDepth() + 1;
            default:
                throw new IllegalArgumentException("Asking for waiting time at CellType.O is illegal");
        }
    }

    public static int getWaitingTime(Product p, CellType cellType) {
        ContainerPicker picker = new ContainerPicker(p, cellType);
        return picker.getWaitingTime();
    }
}
