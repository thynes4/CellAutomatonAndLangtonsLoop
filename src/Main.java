import java.util.Scanner;
import OneDimensionCA.OneDimensionCA;
import LangtonsLoopCA.LangtonsLoop;

/**
 * Welcome to my Cellular Automata Project!
 */
public class Main {
    public static void main(String[] args) {
        Scanner scnr = new Scanner(System.in) ;

        System.out.println("What Cellular Automata would you like to see?");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("\"1D\" for 1D CA           ~");
        System.out.println("\"LL\" for Langton's Loop  ~");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~");

        String input = scnr.next();

        switch (input) {
            case "1D", "1d" -> OneDimensionCA.main(args);
            case "LL", "ll" -> LangtonsLoop.main(args);
            default -> {
                System.out.println(input + " is not recognized, please enter either \"1D\", \"GOL\", or \"LL\" \n");
                main(args);
            }
        }
    }
}
