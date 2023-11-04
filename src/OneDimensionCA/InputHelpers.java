package OneDimensionCA;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class contains important functions used to check if given data is valid as well
 * as convert data into more useful forms
 */
public class InputHelpers {


    /**
     * Function used to transform integer rule into an 8 bit binary number.
     * if rule is 2, it will return a list of strings as [0,0,0,0,0,0,1,0]
     * @param intRule rule in integer form
     * @return 8 item linked list of string of rule in binary form
     */
    public static LinkedList<String> handleIntegerRule(int intRule) {
        String binaryRule = Integer.toBinaryString(intRule);
        String[] bits = (binaryRule.split(""));
        LinkedList<String> Rule = new LinkedList<>(Arrays.asList(bits));

        while (Rule.size() < 8) {
            Rule.push("0");
        }

        return Rule;
    }

    /**
     * Function used to transform string binary rule into an 8 bit binary number list.
     * if string binary rule given is 101, it will return a list of strings as [0,0,0,0,0,1,0,1]
     * @param binaryRule rule in binary form
     * @return 8 item linked list of string of rule in binary form
     */
    public static LinkedList<String> handleBinaryRule(String binaryRule) {
        String[] bits = binaryRule.split("");
        LinkedList<String> Rule = new LinkedList<>(Arrays.asList(bits));

        while (Rule.size() < 8) {
            Rule.push("0");
        }

        return Rule;
    }

    /**
     * Function used to check if the binary rule string is valid by checking all characters are 1s and 0s and the length
     * of the rule is 8 bits
     * @param binaryRuleString rule in binary form
     * @return boolean that returns true if rule is valid and false if invalid
     */
    public static boolean checkRule(String binaryRuleString) {
        String[] bits = binaryRuleString.split("");

        for (String bit : bits) {
            if (!(bit.equals("1") || bit.equals("0"))) {
                System.out.println("Character " + bit + " is not a 1 or 0");
                return false;
            }
        }

        if (bits.length != 8) {
            System.out.println("Length " + bits.length + " is invalid, length must be 8 bits");
            return false;

        } else {
            return true;
        }
    }

    /**
     * Function used to check if the integer rule is valid by checking number is between 0 and 255
     * @param ruleInt rule in integer form
     * @return boolean that returns true if rule is valid and false if invalid
     */
    public static boolean checkRule(int ruleInt) {
        if (ruleInt >= 0 && ruleInt <= 255) {
            return true;
        } else {
            System.out.println("Invalid: please enter a number between 0 and 255");
            return false;
        }
    }

    /**
     * Function used to create the first generation of cells
     * @param initGenList empty list of cells
     * @param startState String of 0s and 1s to determine how the initial generation should be
     * @return filled first generation list of cells matching the start state
     */
    public static List<Generation.Cell> generateFirstGen(List<Generation.Cell> initGenList, String startState) {
        String[] bits = startState.split("");
        LinkedList<String> cellStates = new LinkedList<>(Arrays.asList(bits));
        for (String t : cellStates) {
            if (t.equals("1")) {
                initGenList.add(Generation.Cell.ALIVE);
            }
            if (t.equals("0")) {
                initGenList.add(Generation.Cell.DEAD);
            }
        }
        return initGenList;
    }


}
