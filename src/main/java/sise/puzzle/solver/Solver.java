package sise.puzzle.solver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * Abstract common class for solvers.
 */
public abstract class Solver {

    /** order e.g LRDR */
    protected char[] order;

    /** goal array */
    protected byte[] goal;

    /** currently tested node */
    protected Node currNode;

    /** hashed explored and frontier nodes */
    protected Set<Node> explored;

    /** nodes that are visited but not explored i.e kids are visited yet */
    protected LinkedList<Node> frontier;

    /** solution returned from Solver::solve */
    protected Solution solution;

    /**
     * Calculate the solution.
     *
     * @param board initial board state
     * @param order permutatiom of LRDU for bfs/dfs or hamm/manh for astr
     * @return calculated solution
     */
    public abstract Solution solve(Board board, String order);

    /**
     * Initializes the data, run at start of Solver::solve.
     *
     * @param board initial board state
     * @param order permutatiom of LRDU for bfs/dfs or hamm/manh for astr
     */
    protected void init(Board board, String order) {
        this.goal = Utils.genGoal(board.width * board.height);
        this.order = order.toCharArray();
        this.currNode = new Node(null, board);
        this.explored = new HashSet<>();
        this.frontier = new LinkedList<>();
        this.solution = new Solution();
    }

    /**
     * Check if goal state is reached.
     *
     * @param node node to check
     * @return true if goal reached
     */
    protected boolean isSolved(Node node) {
        return Arrays.equals(node.board.data, goal);
    }
}
