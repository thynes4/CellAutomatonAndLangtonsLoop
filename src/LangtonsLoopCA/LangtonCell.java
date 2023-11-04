package LangtonsLoopCA;

import javafx.scene.paint.Color;

/**
 * This class contains the information stored for each individual cell
 */
public final record LangtonCell (int row, int col, String state) {

    public LangtonCell(final int row, final int col, final String state) {
        this.row = row;
        this.col = col;
        this.state = state;
    }

    public final int getCol() { return col; }

    public final int getRow() { return row; }

    public final String getState() { return state; }

    /**
     * @return color of given cell used to create Java FX visualization
     */
    public final Color getColor() {
        return switch (this.state) {
            case "0" -> Color.WHITE;
            case "1" -> Color.BLACK;
            case "2" -> Color.CRIMSON;
            case "3" -> Color.ORANGE;
            case "4" -> Color.AQUA;
            case "6" -> Color.INDIGO;
            case "7" -> Color.DARKGREEN;
            default -> Color.LIME;
        };
    }
}
