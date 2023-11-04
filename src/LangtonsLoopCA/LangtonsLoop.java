package LangtonsLoopCA;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * Welcome to Langtons Loop!
 */
public class LangtonsLoop extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Langton's Loop");

        // GridPane to hold the cells
        final GridPane root = new GridPane();
        // Scanner to read in file locations
        final Scanner scnr = new Scanner(System.in);
        // List of string to store map of states of the given init file
        final List<String> GridLines = new ArrayList<>();
        // List of string to store the rule read directly from the rules file
        final List<String> rule = new ArrayList<>();
        // First generation of langton cells created from GridLines
        final List<List<LangtonCell>> firstGen = new ArrayList<>();
        // Stores rows value given in init file
        int rows;
        // Stores cols value given in init file
        int cols;
        // scene to hold GridPane, Window default size set to 1000 by 1000 pixels
        Scene scene = new Scene(root, 1000, 1000);
        stage.setScene(scene);

        // Boolean used to verify that rows and cols given at the top of the init file
        // match the rows and cols actually contained in the map of states
        boolean validRowsCols = false;

        while(!validRowsCols) {

            // Read in initial grid file
            System.out.println("Enter path to initial grid file:");
            String fileName = scnr.next();
            try (Scanner fileScnr = new Scanner(new File(fileName))){
                String nextLine = fileScnr.nextLine();
                while (fileScnr.hasNext()) {
                    GridLines.add(nextLine);
                    nextLine = fileScnr.nextLine();
                }
                GridLines.add(nextLine);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Get num rows and cols from top of init file
            String RowAndColLine = GridLines.get(0);
            String[] RowAndCol = RowAndColLine.split(" ");
            final int getRows = Integer.parseInt(RowAndCol[0]);
            final int getCols = Integer.parseInt(RowAndCol[1]);
            rows = getRows;
            cols = getCols;
            GridLines.remove(0);

            // Create first generation
            for (int i = 0; i < GridLines.get(0).length(); i++) {
                final List<LangtonCell> rowOfCell = new ArrayList<>();
                for (int j = 0; j < GridLines.size(); j++) {
                    rowOfCell.add(new LangtonCell(i, j, String.valueOf(GridLines.get(i).charAt(j))));
                }
                firstGen.add(rowOfCell);
            }

            // Check rows and cols match
            if (firstGen.size() != rows) {
                System.out.println("rows specified: " + rows + " does not equal the number of cells in a row in the " +
                        "grid provided: " + GridLines.get(0).length());
            } else if (GridLines.get(0).length() != cols) {
                System.out.println("cols specified: " + cols + " does not equal the number of cells in a col in the " +
                        "grid provided: " + GridLines.size());
            } else {
                validRowsCols = true;
            }
        }

        System.out.println("Enter path to rules file:");

        final String rulePath = scnr.next();
        // Read in rules file
        try (Scanner ruleFileScnr = new Scanner(new File(rulePath))) {
            String line = ruleFileScnr.nextLine();
            while (ruleFileScnr.hasNext()) {
                rule.add(line);
                line = ruleFileScnr.nextLine();
            }
            rule.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Turn list of string rules to list of LangtonRule
        final List<LangtonRule> rules = LangtonsLogic.StringListToRuleList(rule);
        //Grid created using all information generated
        LangtonsGrid grid = new LangtonsGrid(root, firstGen, 10, rules);

        stage.show();

        //timer used to continually update the grid and display it
        AnimationTimer runAnimation = new AnimationTimer() {

            private LangtonsGrid curGrid = grid;

            @Override
            public void handle(long now) {
                long lastUpdate = 0;
                if ((now - lastUpdate) >= TimeUnit.MILLISECONDS.toNanos(500)) {

                    curGrid.clear();
                    curGrid.gridDisplay();
                    curGrid = curGrid.nextGeneration();
                }
            }
        };
        runAnimation.start();
    }
}


