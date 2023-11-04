package LangtonsLoopCA;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the information stored for each individual rule
 */
public record LangtonRule(String current, String neighborhood, String next) {

    public LangtonRule(final String current, final String neighborhood,final  String next) {
        this.current = current;
        this.neighborhood = neighborhood;
        this.next = next;
    }


    public final String getInitial() {
        return current;
    }

    public final String getNext() {
        return next;
    }

    /**
     *  looks at middle 4 characters of given rule as a string, and creates a list with the four possible neighborhood
     *  states for each rule example: [NESW, ESWN, SWNE, WNES]
     * @return List of four combinations of neighborhoods possible for given rule to be valid
     */
    public final List<String> getNeighborhoodVariations() {
        final List<String> Neighborhoods = new ArrayList<>();

        Neighborhoods.add(neighborhood);
        final Scanner scnr = new Scanner(neighborhood);

        scnr.useDelimiter("");
        final String N = scnr.next();
        final String E = scnr.next();
        final String S = scnr.next();
        final String W = scnr.next();

        Neighborhoods.add(E + S + W + N);
        Neighborhoods.add(S + W + N + E);
        Neighborhoods.add(W + N + E + S);

        return Neighborhoods;
    }
}