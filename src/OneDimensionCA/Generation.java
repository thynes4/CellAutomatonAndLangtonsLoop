package OneDimensionCA;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class contains the information stored for each list of cells and the list of rules and logic to evolve the
 * of cells
 */
public class Generation implements Iterable<Neighborhood> {
    private final List<String> rule;
    private final List<Cell> generation;
    public Generation(final List<Cell> generation, List<String> rule) {
        this.generation = generation;
        this.rule = rule;
    }

    /**
     * Evolve this generation using the given rule and previous generation
     * @return New generation evolved from old generation
     */
    public Generation evolve() {
        final List<Cell> newGen = new ArrayList<>();

        for (final Neighborhood neighborhood : this) {
            if (neighborhood.equals('1', '1', '1')) {
                if (rule.get(0).equals("0")) {
                    newGen.add(Cell.DEAD);
                } else {
                    newGen.add(Cell.ALIVE);
                }
            } else if (neighborhood.equals('1', '1', '0')) {
                if (rule.get(1).equals("0")) {
                    newGen.add(Cell.DEAD);
                } else {
                    newGen.add(Cell.ALIVE);
                }
            } else if (neighborhood.equals('1', '0', '1')) {
                if (rule.get(2).equals("0")) {
                    newGen.add(Cell.DEAD);
                } else {
                    newGen.add(Cell.ALIVE);
                }
            } else if (neighborhood.equals('1', '0', '0')) {
                if (rule.get(3).equals("0")) {
                    newGen.add(Cell.DEAD);
                } else {
                    newGen.add(Cell.ALIVE);
                }
            } else if (neighborhood.equals('0', '1', '1')) {
                if (rule.get(4).equals("0")) {
                    newGen.add(Cell.DEAD);
                } else {
                    newGen.add(Cell.ALIVE);
                }
            } else if (neighborhood.equals('0', '1', '0')) {
                if (rule.get(5).equals("0")) {
                    newGen.add(Cell.DEAD);
                } else {
                    newGen.add(Cell.ALIVE);
                }
            } else if (neighborhood.equals('0', '0', '1')) {
                if (rule.get(6).equals("0")) {
                    newGen.add(Cell.DEAD);
                } else {
                    newGen.add(Cell.ALIVE);
                }
            } else {
                if (rule.get(7).equals("0")) {
                    newGen.add(Cell.DEAD);
                } else {
                    newGen.add(Cell.ALIVE);
                }
            }
        }

        return new Generation(newGen, rule);
    }

    public int size() {
        return generation.size();
    }

    private Cell get(final int index) {
        return generation.get(Math.floorMod(index, generation.size()));
    }

    @Override
    public Iterator<Neighborhood> iterator() {
        return new Iterator<>() {
            private int curIndex = 0;

            @Override
            public boolean hasNext() {
                return curIndex < generation.size();
            }

            @Override
            public Neighborhood next() {
                // Make generation.get mistake first
                final Cell left = Generation.this.get(curIndex - 1);
                final Cell middle = Generation.this.get(curIndex);
                final Cell right = Generation.this.get(curIndex + 1);

                curIndex++;

                return new Neighborhood(left, middle, right);
            }
        };
    }

    @Override
    public String toString() {
        return "GameOfLifeGen{" +
                "generation=" + generation +
                '}';
    }


    public enum Cell {
        ALIVE {
            @Override
            public String toString() {
                return "1";
            }
        }, DEAD {
            @Override
            public String toString() {
                return "0";
            }
        };

        public static Cell fromChar(final char c) {
            if (c == '1') {
                return ALIVE;
            } else if (c == '0') {
                return DEAD;
            }
            throw new IllegalArgumentException("Argument, " + c + ", must be either 1 or 0");
        }
    }
}
