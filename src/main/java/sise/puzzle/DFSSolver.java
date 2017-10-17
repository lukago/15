package sise.puzzle;

import java.util.*;

public class DFSSolver implements Solver {

    public final int maxDepth;
    private char[] order;
    private byte[] goal;
    private Node currNode;
    private Set<Node> explored;
    private Stack<Node> frontier;
    private Solution solution;

    public DFSSolver(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public Solution solve(Board board, String order) {
        long timeStart = System.currentTimeMillis();
        init(board, order);
        hashState(currNode);

        while (!frontier.isEmpty() && !solution.solved) {
            currNode = frontier.pop();
            explorePaths(currNode);
        }

        solution.timeMillis = System.currentTimeMillis() - timeStart;
        solution.finishedNum = explored.size();
        solution.visitedNum = explored.size() - frontier.size();

        return solution;
    }

    private void explorePaths(Node node) {
        checkMaxDepth(node);

        if (isSolved(node) && !solution.solved) {
            solution.solved = true;
            solution.path = node.getPath();
        }

        if (node.getDepth() < maxDepth && !solution.solved) {
            for (char c : order) {
                if (c == 'L') {
                    moveLeft(node);
                } else if (c == 'R') {
                    moveRight(node);
                } else if (c == 'U') {
                    moveUp(node);
                } else if (c == 'D') {
                    moveDown(node);
                } else {
                    moveLeft(node);
                }
            }
        }
    }

    private void hashState(Node node) {
        if (!explored.contains(node)) {
            explored.add(node);
            frontier.push(node);
        }
    }

    private void moveLeft(Node node) {
        node = node.getLeftChild();
        if (node != null) {
            hashState(node);
            explorePaths(node);
        }
    }

    private void moveRight(Node node) {
        node = node.getRightChild();
        if (node != null) {
            hashState(node);
            explorePaths(node);
        }
    }

    private void moveUp(Node node) {
        node = node.getUpChild();
        if (node != null) {
            hashState(node);
            explorePaths(node);
        }
    }

    private void moveDown(Node node) {
        node = node.getDownChild();
        if (node != null) {
            hashState(node);
            explorePaths(node);
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
        this.frontier = new Stack<>();
        this.solution = new Solution();
    }
}
