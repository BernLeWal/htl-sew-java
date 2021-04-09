package core.functional;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 *  FunctionalSearch implements a DFS (bepth-first-search) and a BFS (breadth-first-search) algorithm with functionals.
 */
public class FunctionalSearch {
    private boolean verbose = false;
    private int stepCount = 0;

    public FunctionalSearch() {
    }

    public FunctionalSearch(boolean verbose) {
        this.verbose = verbose;
    }

    public int getStepCount() {
        return stepCount;
    }

    public <N> Optional<N> depthFirstSearch(Function<N, List<N>> succFn, Predicate<N> goalFn, N start ) {
        stepCount = 0;
        Set<N> visited = new HashSet<>();
        Deque<N> stack = new ArrayDeque<>();
        stack.push(start);
        while (!stack.isEmpty()) {
            N node = stack.pop();
            stepCount++;
            if (verbose)
                System.out.println(node + "\n");
            if (goalFn.test(node)) {
                if (verbose)
                    System.out.printf("depthFirstSearch needed %d steps until solution found.\n", stepCount);
                return Optional.of(node);
            } else {
                visited.add(node);
                List<N> succs = succFn.apply(node);
                succs.removeAll(visited);
                for (N n : succs) {
                    if (stack.contains(n)) stack.remove(n);
                    stack.push(n);
                }
            }
        }
        return Optional.empty();
    }

    public <N> Optional<N> breadthFirstSearch(Function<N, List<N>> succFn, Predicate<N> goalFn, N start ) {
        stepCount = 0;
        Set<N> visited = new HashSet<>();
        Queue<N> queue = new ArrayDeque<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            N node = queue.remove();
            stepCount++;
            if (verbose)
                System.out.println(node + "\n");
            if (goalFn.test(node)) {
                if (verbose)
                    System.out.printf("breadthFirstSearch needed %d steps until solution found.\n", stepCount);
                return Optional.of(node);
            } else {
                visited.add(node);
                List<N> succs = succFn.apply(node);
                succs.removeAll(visited);
                for (N n : succs) {
                    if (!queue.contains(n))
                        queue.add(n);
                }
            }
        }
        return Optional.empty();
    }

}
