package core.functional;

import java.util.*;

/**
 * Puzzle implements the field-moving puzzle game, where you have to bring the numbers 1-8 inside a 3x3 field
 * in the correct order:
 * 8 3       873              123
 * 275  -->  2 5  --> ... --> 456
 * 461       461              78
 *
 * The demo in the main-programm shows how to find the solution with a DFS (bepth-first-search)
 * and a BFS (breadth-first-search) algorithm implemented with functionals.
 */
public class Puzzle {
    public static final Puzzle SOLUTION = new Puzzle( new int[]{1,2,3,4,5,6,7,8,0} );

    private final int[] fields;
    private final int holePosition;
    private final int hashCode;

    public Puzzle(int[] fields) {
        this.fields = fields;
        int holePosition = 0;
        int hashCode = 0;
        for (int i=0; i < 9; i++) {
            if( fields[i]==0 ) holePosition = i;
            else hashCode += i<<((fields[i]-1)*3);
        }
        this.holePosition = holePosition;
        this.hashCode = hashCode;
//        this.hashCode = Long.valueOf( (long)fields[0] + (fields[1]<<4) + (fields[2]<<8) +
//                (fields[3]<<12) + (fields[4]<<16) + (fields[5]<<20) +
//                (fields[6]<<24) + (fields[7]<<28) + (fields[8]<<32)).hashCode();
    }

    public static Puzzle createRandom() {
        int[] fields = new int[9];

        Random r = new Random();
        for (int i = 1; i<=8; i++) {
            int tryPos = r.nextInt(9);
            while (fields[tryPos]!=0) {
                tryPos = r.nextInt(9);
            }
            fields[tryPos] = i;
        }

        return new Puzzle(fields);
    }


    public int getField(int x, int y) { return fields[y*3+x]; }
    public int getHolePosition() { return holePosition; }

    public static int positionToX(int position) { return position % 3; }
    public static int positionToY(int position) { return position / 3; }

    @Override
    public String toString() {
        return String.format("%s%s%s\n%s%s%s\n%s%s%s\n",
                fieldToString(fields[0]), fieldToString(fields[1]), fieldToString(fields[2]),
                fieldToString(fields[3]), fieldToString(fields[4]), fieldToString(fields[5]),
                fieldToString(fields[6]), fieldToString(fields[7]), fieldToString(fields[8])
                );
    }

    private static String fieldToString(int num) {
        return (num>0) ? String.valueOf(num) : " ";
    }

    public Optional<Puzzle> moveHoleUp() {
        if( positionToY(holePosition)>0 ) {
            int[] fields = Arrays.copyOf(this.fields,9);
            fields[holePosition] = fields[holePosition-3];
            fields[holePosition-3] = 0;
            return Optional.of(new Puzzle(fields));
        }
        return Optional.empty();
    }

    public Optional<Puzzle> moveHoleDown() {
        if( positionToY(holePosition)<2 ) {
            int[] fields = Arrays.copyOf(this.fields,9);
            fields[holePosition] = fields[holePosition+3];
            fields[holePosition+3] = 0;
            return Optional.of(new Puzzle(fields));
        }
        return Optional.empty();
    }

    public Optional<Puzzle> moveHoleLeft() {
        if( positionToX(holePosition)>0 ) {
            int[] fields = Arrays.copyOf(this.fields,9);
            fields[holePosition] = fields[holePosition-1];
            fields[holePosition-1] = 0;
            return Optional.of(new Puzzle(fields));
        }
        return Optional.empty();
    }

    public Optional<Puzzle> moveHoleRight() {
        if( positionToX(holePosition)<2 ) {
            int[] fields = Arrays.copyOf(this.fields,9);
            fields[holePosition] = fields[holePosition+1];
            fields[holePosition+1] = 0;
            return Optional.of(new Puzzle(fields));
        }
        return Optional.empty();
    }


    public List<Puzzle> getNextStates() {
        List<Puzzle> results = new ArrayList<>();
        Optional<Puzzle> result;
        if ( (result=moveHoleUp()).isPresent() ) results.add(result.get());
        if ( (result=moveHoleDown()).isPresent() ) results.add(result.get());
        if ( (result=moveHoleLeft()).isPresent() ) results.add(result.get());
        if ( (result=moveHoleRight()).isPresent() ) results.add(result.get());
        return results;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Puzzle puzzle = (Puzzle) o;
        return this.hashCode==puzzle.hashCode;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }

    public boolean isSolution() {
        return this.equals(SOLUTION);
    }

    /**
     * The demo in the main-programm shows how to find the solution with a DFS (bepth-first-search)
     * and a BFS (breadth-first-search) algorithm implemented with functionals.
     * @param args No arguments needed.
     */
    public static void main(String[] args) {
        System.out.println("The final puzzle:");
        System.out.println(SOLUTION);
        System.out.println("[123/456/78 ] equals SOLUTION: " + new Puzzle( new int[]{1,2,3,4,5,6,7,8,0} ).isSolution() );
        System.out.println("A Random puzzle:");
        var start = createRandom();
        System.out.println(start);
        System.out.println("... equals SOLUTION: " + start.isSolution());
        System.out.println("Possible next states:");
        var nextStates = start.getNextStates();
        for (var nextState : nextStates) {
            System.out.println(nextState);
            System.out.println();
        }

        // HINT: set verbose=true in the constructor to see the puzzle-moves.
        var search = new FunctionalSearch(false);
        System.out.println("Search the solution with the DepthFirstSearch...");
        var startMillis = System.currentTimeMillis();
        var solutionDFS = search.depthFirstSearch(
                p -> p.getNextStates(),
                p -> p.isSolution(),
                start
        );
        double duration = (System.currentTimeMillis() - startMillis) / 1000.0;
        System.out.printf("...needed %d steps in %.3f seconds (rate=%.1f/second).\n", search.getStepCount(), duration, search.getStepCount()/duration);

        System.out.println("Search the solution with the BreadthFirstSearch...");
        startMillis = System.currentTimeMillis();
        var solutionBFS = search.breadthFirstSearch(
                p -> p.getNextStates(),
                p -> p.isSolution(),
                start
        );
        duration = (System.currentTimeMillis() - startMillis) / 1000.0;
        System.out.printf("...needed %d steps in %.3f seconds (rate=%.1f/second).\n", search.getStepCount(), duration, search.getStepCount()/duration);

    }
}
