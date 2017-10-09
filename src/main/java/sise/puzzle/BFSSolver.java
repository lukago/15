package sise.puzzle;

import java.util.*;

public class BFSSolver {

    private final byte[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    private Node currNode;
    private Set<String> explored;
    private Deque<Node> frontier;

    public String solve(byte[] data) {
        init(data);
        hashNode(currNode);

        while (!frontier.isEmpty()) {
            currNode = frontier.remove();
            if (isSolved(currNode)) break;
            explorePaths(currNode);
        }

        return currNode.getPath();
    }

    private void explorePaths(Node node) {
        hashNode(node.getLeftChild());
        hashNode(node.getRightChild());
        hashNode(node.getUpChild());
        hashNode(node.getDownChild());
    }

    private void hashNode(Node node) {
        if (node != null) {
            String nextStr = Arrays.toString(node.getBoard());
            if (!explored.contains(nextStr)) {
                explored.add(nextStr);
                frontier.add(node);
            }
        }
    }

    private boolean isSolved(Node node) {
        return Arrays.equals(node.getBoard(), goal);
    }

    private void init(byte[] data) {
        this.explored = new HashSet<>();
        this.frontier = new LinkedList<>();
        this.currNode = new Node(null, data, Character.MIN_VALUE);
    }
}
