import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class Product {
    private String name;
    private int xPosition, yPosition, depth;

    public static Product loadFromString(String input) {
        String[] split_input = input.split(" ");
        String name = split_input[0];
        int xPosition = Integer.parseInt(split_input[1]);
        int yPosition = Integer.parseInt(split_input[2]);
        int depth = Integer.parseInt(split_input[3]);
        return new Product(name, xPosition, yPosition, depth);
    }
}
