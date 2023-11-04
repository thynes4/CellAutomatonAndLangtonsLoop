package OneDimensionCA;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Welcome to One Dimensional Cellular Automata!
 */
public class OneDimensionCA extends Application {

    // Initial list of cells that gets generated and set after user input is valid or file input is valid
    private List<Generation.Cell> initGenList;
    // Rule list that gets generated and set after user input is valid or file input is valid
    private List<String> Rule;

    public static void main(final String[] args) {
        launch(args);
    }

    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Elementary CA");

        // Scanner to read in file locations
        Scanner scnr = new Scanner(System.in);

        System.out.println("Would you like to read from a file? (y/n)");
        String response = scnr.nextLine();

        // If the user does not want to read from a file
        if (response.equals("n")) {
            // Initial list of cells
            final List<Generation.Cell> initGenList = new ArrayList<>();
            // Boolean used to check if the user correctly entered i or b for integer or binary rule
            boolean goodResponse = false;
            // Boolean used to check if the rule itself is valid
            boolean goodRule = false;


            while (!goodResponse) {
                primaryStage.setTitle("Elementary CA");
                System.out.println("Is your rule in integer or binary form? (i,b)");
                String IntOrBinary = scnr.nextLine();


                if (IntOrBinary.equals("i")) {
                    goodResponse = true;

                    while (!goodRule) {
                        System.out.println("What is your rule integer? (enter between 0 and 255)");
                        int ruleInt =  Integer.parseInt(scnr.nextLine());
                        goodRule = InputHelpers.checkRule(ruleInt);
                        if (goodRule) {
                            this.Rule = new LinkedList<>(InputHelpers.handleIntegerRule(ruleInt));
                        }
                    }

                } else if (IntOrBinary.equals("b")) {
                    goodResponse = true;

                    while (!goodRule) {
                        System.out.println("What is your rule in binary? (enter between 00000000 and 11111111)");
                        String binaryRuleString = scnr.nextLine();
                        goodRule = InputHelpers.checkRule(binaryRuleString);
                        if (goodRule) {
                            this.Rule = new LinkedList<>(InputHelpers.handleBinaryRule(binaryRuleString));
                        }
                    }
                } else {
                    System.out.println("please enter either \"i\" or \"b\"");
                }
            }

            System.out.println("What is your initial state?");
            String startState = scnr.next();

            this.initGenList = InputHelpers.generateFirstGen(initGenList, startState);

        // If the user does want to read from a file
        } else if (response.equals("y")) {
            System.out.println("What is the file path?");
            String pathName = scnr.nextLine();
            try (Scanner s = new Scanner(new File(pathName))) {
                boolean goodRule;
                final List<Generation.Cell> initGenList = new ArrayList<>();
                String binaryRuleString = s.nextLine();
                String startState = s.nextLine();

                goodRule = InputHelpers.checkRule(binaryRuleString);
                if (goodRule) {
                    this.Rule = new LinkedList<>(InputHelpers.handleBinaryRule(binaryRuleString));
                }
                this.initGenList = InputHelpers.generateFirstGen(initGenList, startState);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        primaryStage.setWidth(880);
        primaryStage.setHeight(880);
        // Create grid to hold visual representation of CA
        final GridPane grid = new GridPane();
        // Set the scene
        final Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();

        final Generation initGen = new Generation(this.initGenList, this.Rule);
        // Create animation timer to run the animation
        final AnimationTimer timer = new AnimationTimer() {
            private int curGenIndex = 0;
            private Generation curGen = initGen;
            private final int genSize = curGen.size();
            private long prevUpdate = 0;

            @Override
            public void handle(final long now) {
                if (now - prevUpdate >= TimeUnit.SECONDS.toNanos(1)) {
                    // Create visual representation of current generation
                    int curCol = 0;
                    for (final Neighborhood neighborhood : curGen) {
                        final Rectangle rect = new Rectangle();
                        // Set fill based on state of middle cell
                        if (neighborhood.middle() == Generation.Cell.ALIVE) {
                            rect.setFill(Color.BLACK);
                        } else {
                            rect.setFill(Color.WHITE);
                        }
                        // Bind the rects size according to the size of the window
                        rect.widthProperty().bind(primaryStage.widthProperty().divide(genSize));
                        rect.heightProperty().bind(rect.widthProperty());
                        grid.add(rect, curCol, curGenIndex);
                        // Increment the column
                        curCol++;
                    }
                    // Evolve next gen
                    curGen = curGen.evolve();
                    // Increment the row
                    curGenIndex++;
                    // Update previous update time
                    prevUpdate = now;
                }
            }
        };
        timer.start();
    }
}
