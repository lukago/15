package sise.puzzle;

import java.util.*;

public class BFSSolver {

    private byte[] goal;
    private Node currNode;
    private Set<String> explored;
    private Deque<Node> frontier;

    public String solve(byte[] data, int width, int length) {
        init(data, width, length);
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

    private void init(byte[] data, int width, int height) {
        this.explored = new HashSet<>();
        this.frontier = new LinkedList<>();
        this.currNode = new Node(null, data, Character.MIN_VALUE, width, height);

        this.goal = new byte[width * height];
        for (int i = 0; i<goal.length - 1; i++) {
            goal[i] = (byte) (i + 1);
        }
        goal[goal.length - 1] = 0;
    }
}
