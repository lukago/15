package sise.puzzle;

import java.util.*;

public class DFSSolver implements Solver {

    private final int MAX_DEPTH = 25;

    private char[] order;
    private byte[] goal;
    private Node currNode;
    private Set<Node> explored;
    private Stack<Node> frontier;
    private boolean solved;
    private String path;
    private int maxDepthReached;

    public Solution solve(Board board, String order) {
        long time = System.currentTimeMillis();
        init(board, order);
        hashState(currNode);

        while (!frontier.isEmpty() || !solved) {
            currNode = frontier.pop();
            explorePaths(currNode);
        }

        return new Solution(
                path,
                System.currentTimeMillis() - time,
                maxDepthReached,
                explored.size(),
                explored.size() - frontier.size());
    }

    private void explorePaths(Node node) {
        checkMaxDepth(node);

        if (isSolved(node) && !solved) {
            solved = true;
            path = node.getPath();
        }

        if (node.getDepth() < MAX_DEPTH && !solved) {
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
        if (nodeDepth > maxDepthReached) {
            maxDepthReached = nodeDepth;
        }
    }

    private void init(Board board, String order) {
        this.currNode = new Node(null, board);
        this.explored = new HashSet<>();
        this.frontier = new Stack<>();
        this.goal = Utils.genGoal(board.width * board.height);
        this.order = order.toCharArray();
        this.solved = false;
        this.path = "";
        this.maxDepthReached = 0;
    }
}
