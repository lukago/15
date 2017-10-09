package sise.puzzle;

import java.util.*;

public class DFSSolver {

    private final byte[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
    private final int maxDepth = 11;

    private Node currNode;
    private Set<String> explored;
    private Stack<Node> frontier;

    public String solve(byte[] data) {
        init(data);
        hashState(currNode);

        while (!frontier.isEmpty()) {
            currNode = frontier.pop();
            if (isSolved(currNode)) break;
            explorePaths(currNode);
        }

        return currNode.getPath();
    }

    private void explorePaths(Node node) {
        if (node.getDepth() < maxDepth) {
            moveLeft(node);
            moveRight(node);
            moveUp(node);
            moveDown(node);
        }
    }

    private void hashState(Node node) {
        String nextStr = Arrays.toString(node.getBoard());
        if (!explored.contains(nextStr)) {
            explored.add(nextStr);
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
        return Arrays.equals(node.getBoard(), goal);
    }

    private void init(byte[] data) {
        this.explored = new HashSet<>();
        this.frontier = new Stack<>();
        this.currNode = new Node(null, data, Character.MIN_VALUE);
    }
}
