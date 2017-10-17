package sise.puzzle;

import java.util.*;

public class BFSSolver implements Solver {

    private char[] order;
    private byte[] goal;
    private Node currNode;
    private Set<Node> explored;
    private Deque<Node> frontier;
    private Solution solution;

    public Solution solve(Board board, String order) {
        long timeStart = System.currentTimeMillis();
        init(board, order);
        hashNode(currNode);

        while (!frontier.isEmpty() && !solution.solved) {
            currNode = frontier.remove();
            explorePaths(currNode);
        }

        solution.timeMillis = System.currentTimeMillis() - timeStart;
        solution.finishedNum = explored.size();
        solution.visitedNum = explored.size() - frontier.size();

        return solution;
    }

    private void explorePaths(Node node) {
        checkMaxDepth(node);

        if (isSolved(node)) {
            solution.solved = true;
            solution.path = node.getPath();
            return;
        }

        for (char c : order) {
            if (c == 'L') {
                hashNode(node.getLeftChild());
            } else if (c == 'R') {
                hashNode(node.getRightChild());
            } else if (c == 'U') {
                hashNode(node.getUpChild());
            } else if (c == 'D') {
                hashNode(node.getDownChild());
            }
        }
    }

    private void hashNode(Node node) {
        if (node != null) {
            if (!explored.contains(node)) {
                explored.add(node);
                frontier.add(node);
            }
        }
    }

    private boolean isSolved(Node node) {
        return Arrays.equals(node.board.data, goal);
    }

    private void checkMaxDepth(Node node) {
        int nodeDepth = node.getDepth();
        if (nodeDepth > solution.maxDepth) {
            solution.maxDepth = nodeDepth;
        }
    }

    private void init(Board board, String order) {
        this.goal = Utils.genGoal(board.width * board.height);
        this.order = order.toCharArray();
        this.currNode = new Node(null, board);
        this.explored = new HashSet<>();
        this.frontier = new LinkedList<>();
        this.solution = new Solution();
    }
}
