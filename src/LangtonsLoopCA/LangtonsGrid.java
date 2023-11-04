package LangtonsLoopCA;

import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

import java.util.List;

/**
 * This class stores the information for the completed JavaFX grid which gets cleared and updated while the timer runs
 */
public final class LangtonsGrid {

    private final GridPane Pane;
    private final int CellSize;
    private final List<LangtonRule> Rules;
    private final List<List<LangtonCell>> Generation;

    public LangtonsGrid(final GridPane gridpane,final List<List<LangtonCell>> Generation,final int size,final List<LangtonRule> Rules) {
        this.Pane = gridpane;
        this.CellSize = size;
        this.Generation = Generation;
        this.Rules = Rules;
    }

    /**
     *  calls evolve in langtons logic to create a new grid with the same rules cell size and pane but the
     *  generation is updated
     * @return Next generation for given grid
     */
    public final LangtonsGrid nextGeneration() {
        final List<List<LangtonCell>> nextGen = LangtonsLogic.evolve(Generation, Rules);
        return new LangtonsGrid(Pane, nextGen, CellSize, Rules);
    }

    /**
     *  updates the pane for each Langtons grid to display the given generation using rectangles
     *  of different colors using get color for each cell
     */
    public final void gridDisplay() {
        for (final List<LangtonCell> row : Generation) {
            for (final LangtonCell cell : row) {
                final Rectangle rect = new Rectangle(CellSize, CellSize, cell.getColor());
                Pane.add(rect, cell.getCol(), cell.getRow());
            }
        }
    }

    /**
     *  used to prevent stacking of grids and causing severe lag that breaks the program
     */
    public void clear() {
        this.Pane.getChildren().clear();
    }
}
