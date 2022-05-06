package botrouteplanner.model;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class JobTest {
    final String MALFORMED_START_COORDINATES_PATH = getPath("src/test/resources/malformed/malformed-start-coordinates.txt");
    final String MALFORMED_STATION_COORDINATES_PATH = getPath("src/test/resources/malformed/malformed-station-coordinates.txt");
    final String MISSING_PRODUCT_PATH = getPath("src/test/resources/malformed/missing-product.txt");

    private String getPath(String resource) {
        return new File(resource).getAbsolutePath();
    }

    @Test
    void malformedStartCoordinatesTest() {
        assertThrows(IllegalArgumentException.class, () -> Job.loadFromFile(MALFORMED_START_COORDINATES_PATH));
    }

    @Test
    void malformedStationCoordinatesTest() {
        assertThrows(IllegalArgumentException.class, () -> Job.loadFromFile(MALFORMED_STATION_COORDINATES_PATH));
    }

    @Test
    void missingProductTest() {
        assertThrows(IllegalArgumentException.class, () -> Job.loadFromFile(MISSING_PRODUCT_PATH));
    }
}