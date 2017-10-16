package sise.puzzle;

import java.util.*;

public class DFSSolver {

    private final int MAX_DEPTH = 15;

    private char[] order;
    private byte[] goal;
    private Node currNode;
    private Set<Node> explored;
    private Stack<Node> frontier;
    private boolean solved;
    private String solution;

    public String solve(byte[] data, int width, int length, char[] order) {
        init(data, width, length, order);
        hashState(currNode);

        while (!frontier.isEmpty() || !solved) {
            currNode = frontier.pop();
            explorePaths(currNode);
        }

        return solution;
    }

    private void explorePaths(Node node) {
        if (isSolved(node) && !solved) {
            solved = true;
            solution = node.getPath();
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

    private void init(byte[] data, int width, int height, char[] order) {
        this.currNode = new Node(null, new Board(data, Character.MIN_VALUE, Utils.findZeroPos(data), width, height));
        this.explored = new HashSet<>();
        this.frontier = new Stack<>();
        this.goal = Utils.getGoalZeroLast(width * height);
        this.order = order.clone();
        this.solved = false;
        this.solution = "-1";
    }
}
