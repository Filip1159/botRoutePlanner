package botrouteplanner.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Product {
    private String name;
    private Point location;
    private int depth;

    public static Product loadFromString(String input) {
        String[] split_input = input.split(" ");
        if (split_input.length != 4)
            throw new IllegalArgumentException("Malformed input: " + input);
        String name = split_input[0];
        Point location = new Point(Integer.parseInt(split_input[1]), Integer.parseInt(split_input[2]));
        int depth = Integer.parseInt(split_input[3]);
        if (depth < 0)
            throw new IllegalArgumentException("Illegal negative product depth: " + depth);
        return new Product(name, location, depth);
    }
}
