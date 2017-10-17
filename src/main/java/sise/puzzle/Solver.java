package sise.puzzle;

import java.util.*;

public abstract class Solver {

    protected char[] order;
    protected byte[] goal;
    protected Node currNode;
    protected Set<Node> explored;
    protected Deque<Node> frontier;
    protected Solution solution;

    public abstract Solution solve(Board board, String order);

    protected void init(Board board, String order) {
        this.goal = Utils.genGoal(board.width * board.height);
        this.order = order.toCharArray();
        this.currNode = new Node(null, board);
        this.explored = new HashSet<>();
        this.frontier = new LinkedList<>();
        this.solution = new Solution();
    }

    protected boolean isSolved(Node node) {
        return Arrays.equals(node.board.data, goal);
    }
}
