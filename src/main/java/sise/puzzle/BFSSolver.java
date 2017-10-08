package sise.puzzle;

import java.util.*;

public class BFSSolver {

    private final byte[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    private Node currNode;
    private Set<String> explored;
    private Deque<Node> frontier;

    public String solve(byte[] data) {
        init(data);
        hashState(currNode);

        while (!frontier.isEmpty()) {
            currNode = frontier.remove();
            if (isSolved()) break;
            explorePaths(currNode);
        }

        return currNode.getPath();
    }

    private void explorePaths(Node node) {
        moveLeft(node);
        moveRight(node);
        moveUp(node);
        moveDown(node);
    }

    private void hashState(Node node) {
        String nextStr = Arrays.toString(node.getPuzzles());
        if (!explored.contains(nextStr)) {
            explored.add(nextStr);
            frontier.add(node);
        }
    }

    private void moveLeft(Node node) {
        if (node.canMoveLeft()) {
            hashState(node.getLeftChild());
        }
    }

    private void moveRight(Node node) {
        if (node.canMoveRight()) {
            hashState(node.getRightChild());
        }
    }

    private void moveUp(Node node) {
        if (node.canMoveUp()) {
            hashState(node.getUpChild());
        }
    }

    private void moveDown(Node node) {
        if (node.canMoveDown()) {
            hashState(node.getDownChild());
        }
    }

    private boolean isSolved() {
        return Arrays.equals(currNode.getPuzzles(), goal);
    }

    private void init(byte[] data) {
        this.explored = new HashSet<>();
        this.frontier = new LinkedList<>();
        this.currNode = new Node(null, data, Character.MIN_VALUE);
    }
}
