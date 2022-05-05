package botrouteplanner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public class Product {
    private String name;
    private Point location;
    private int depth;

    public static Product loadFromString(String input) {
        String[] split_input = input.split(" ");
        String name = split_input[0];
        Point location = new Point(Integer.parseInt(split_input[1]), Integer.parseInt(split_input[2]));
        int depth = Integer.parseInt(split_input[3]);
        return new Product(name, location, depth);
    }
}
