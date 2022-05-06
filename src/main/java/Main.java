import botrouteplanner.Planner;
import botrouteplanner.model.Path;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2)
            System.out.println("Error: app requires exactly 2 arguments");
        else {
            String gridFilePath = args[0];
            String jobFilePath = args[1];
            try {
                Planner planner = Planner.load(gridFilePath, jobFilePath);
                Path path = planner.createRoute();
                System.out.println(path.getTransitionsCount());
                System.out.println(path.getTravelTime());
                path.printFormatted();
            } catch (IOException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
