package OneDimensionCA;

/**
 * This class stores the neighborhood for each cell and compares the cells to their neighbors
 */
public record Neighborhood(Generation.Cell left, Generation.Cell middle, Generation.Cell right) {

    public boolean equals(final char leftCell, final char middleCell, final char rightCell) {
        return this.equals(new Neighborhood(
                Generation.Cell.fromChar(leftCell),
                Generation.Cell.fromChar(middleCell),
                Generation.Cell.fromChar(rightCell)));
    }
}
