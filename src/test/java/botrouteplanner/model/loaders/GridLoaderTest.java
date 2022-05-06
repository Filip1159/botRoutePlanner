package botrouteplanner.model.loaders;

import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GridLoaderTest {
    final String INVALID_HEADER_VALUES_PATH = getPath("src/test/resources/malformed/invalid-header-values.txt");
    final String TEXT_INSIDE_HEADER_PATH = getPath("src/test/resources/malformed/text-inside-header.txt");
    final String TOO_LONG_HEADER_PATH = getPath("src/test/resources/malformed/too-long-header.txt");
    final String ILLEGAL_CELL_CODE_PATH = getPath("src/test/resources/malformed/illegal-cell-code.txt");
    final String TOO_FEW_GRID_ROWS_PATH = getPath("src/test/resources/malformed/too-few-grid-rows.txt");
    final String TOO_SHORT_GRID_ROW_PATH = getPath("src/test/resources/malformed/too-short-grid-row.txt");
    final String ILLEGAL_PRODUCT_COORDINATES_PATH = getPath("src/test/resources/malformed/illegal-product-coordinates.txt");
    final String TEXT_INSIDE_PRODUCT_COORDINATES_PATH = getPath("src/test/resources/malformed/text-inside-product-coordinates.txt");
    final String TOO_FEW_PRODUCT_COORDINATES_PATH = getPath("src/test/resources/malformed/too-few-product-coordinates.txt");

    private String getPath(String resource) {
        return new File(resource).getAbsolutePath();
    }

    @Test
    void malformedHeaderTest() {
        assertThrows(IllegalArgumentException.class, () -> GridLoader.loadFromFile(INVALID_HEADER_VALUES_PATH));
        assertThrows(IllegalArgumentException.class, () -> GridLoader.loadFromFile(TEXT_INSIDE_HEADER_PATH));
        assertThrows(IllegalArgumentException.class, () -> GridLoader.loadFromFile(TOO_LONG_HEADER_PATH));
    }

    @Test
    void malformedGridTest() {
        assertThrows(IllegalArgumentException.class, () -> GridLoader.loadFromFile(ILLEGAL_CELL_CODE_PATH));
        assertThrows(IllegalArgumentException.class, () -> GridLoader.loadFromFile(TOO_FEW_GRID_ROWS_PATH));
        assertThrows(IllegalArgumentException.class, () -> GridLoader.loadFromFile(TOO_SHORT_GRID_ROW_PATH));
    }

    @Test
    void malformedProductsTest() {
        assertThrows(IllegalArgumentException.class, () -> GridLoader.loadFromFile(ILLEGAL_PRODUCT_COORDINATES_PATH));
        assertThrows(IllegalArgumentException.class, () -> GridLoader.loadFromFile(TEXT_INSIDE_PRODUCT_COORDINATES_PATH));
        assertThrows(IllegalArgumentException.class, () -> GridLoader.loadFromFile(TOO_FEW_PRODUCT_COORDINATES_PATH));
    }
}