package LangtonsLoopCA;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the most important functions in my program, one that transform the string list of rules into a
 * list of type langton rule, one to look at each cell and get the neighborhood that factors in a looping map, and one
 * that evolves the list of list of cells.
 */
public final class LangtonsLogic {

    /**
     * Looks at each rule in the list of string taking the first number as a string and stores it as current state
     * the next four numbers as the default neighborhood and the final number as the next state then creates a rule with
     * those three values and adds it to a list.
     * @param rules (list of string rules read directly from the file)
     * @return List of LangtonRules which transforms the list of string into a much more useful form
     */
    public static List<LangtonRule> StringListToRuleList(final List<String> rules) {
        final List<LangtonRule> LangtonRules = new ArrayList<>();
         String current;
         String neighborhood;
         String next;


        for (String rule : rules) {
            Scanner ruleView = new Scanner(rule);
            ruleView.useDelimiter("");

            current = ruleView.next();

            StringBuilder NeighborhoodBuilder = new StringBuilder();
            for (int i = 0; i <= 3; i++) {
                NeighborhoodBuilder.append(ruleView.next());
            }
            neighborhood = NeighborhoodBuilder.toString();

            next = ruleView.next();
            LangtonRules.add(new LangtonRule(current, neighborhood, next));
        }

        return LangtonRules;
    }

    /**
     * This function goes through each direction in order to see if the cell is an edge case and goes to the opposite
     * side of the board if so otherwise gets the state of the cell directly next to the current cell in the given
     * direction appends all of these states in order and returns them as a string.
     * @param currentCell cell being looked at
     * @param Map all cells to compare locations of current cell to and get states from
     * @return a single neighborhood given the cell in the form [NESW] where each letter represents a state
     */
    public static String getNeighborhood(final LangtonCell currentCell, final List<List<LangtonCell>> Map) {
        final String neighborhood;
        final StringBuilder sb = new StringBuilder();
        final int cellsRow = currentCell.getRow();
        final int cellsCol = currentCell.getCol();
        final int lastCol = Map.get(1).size() - 1;
        final int lastRow = Map.size() - 1;

        //North
        if (cellsRow == 0) {
            sb.append(Map.get(lastRow).get(cellsCol).getState());
        } else {
            sb.append(Map.get(cellsRow - 1).get(cellsCol).getState());
        }

        //East
        if (cellsCol == lastCol) {
            sb.append(Map.get(cellsRow).get(0).getState());
        } else {
            sb.append(Map.get(cellsRow).get(cellsCol + 1).getState());
        }

        //South
        if (cellsRow == lastRow) {
            sb.append(Map.get(0).get(cellsCol).getState());
        } else {
            sb.append(Map.get(cellsRow + 1).get(cellsCol).getState());
        }

        //West
        if (cellsCol == 0) {
            sb.append(Map.get(cellsRow).get(lastCol).getState());
        } else {
            sb.append(Map.get(cellsRow).get(cellsCol - 1).getState());
        }

        neighborhood = sb.toString();
        return neighborhood;
    }

    /**
     * This function looks through each cell in the current generation, gets the cells neighborhood using the above
     * function, looks through the given list of rules to find the rule containing the same neighborhood, creates a new
     * langton cell with the next state value in the rule and adds it at the same loction at the cell that was being
     * looked at in the newly created generation.
     * @param currentGen Generation of cells to evolve
     * @param rules to compare the neighborhoods of each cell to the neighborhoods in the rules to determine next state
     * @return nextGen evolved generation of cells
     */
    public static List<List<LangtonCell>> evolve(final List<List<LangtonCell>> currentGen, final List<LangtonRule> rules) {
        final List<List<LangtonCell>> nextGen = new ArrayList<>();

        for (final List<LangtonCell> row : currentGen) {
            final List<LangtonCell> rowCellArray = new ArrayList<>();
            for (final LangtonCell cell : row) {
                for (final LangtonRule R : rules) {
                    if (cell.getState().equals(R.getInitial())) {
                        final List<String> variations = R.getNeighborhoodVariations();
                        for (final String variation : variations) {
                            if (getNeighborhood(cell, currentGen).equals(variation)) {
                                    rowCellArray.add(new LangtonCell(cell.getRow(), cell.getCol(), R.getNext()));
                                    break;
                            }
                        }
                    }
                }
            }
            nextGen.add(rowCellArray);
        }
        return nextGen;
    }

}
