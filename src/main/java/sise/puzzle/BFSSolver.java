package sise.puzzle;

import java.util.*;

public class BFSSolver {

    private char[] order;
    private byte[] goal;
    private Node currNode;
    private Set<Node> explored;
    private Deque<Node> frontier;

    public String solve(byte[] data, int width, int length, char[] order) {
        init(data, width, length, order);
        hashNode(currNode);

        while (!frontier.isEmpty()) {
            currNode = frontier.remove();
            if (isSolved(currNode)) break;
            explorePaths(currNode);
        }

        return currNode.getPath();
    }

    private void explorePaths(Node node) {
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

    private void init(byte[] data, int width, int height, char[] order) {
        this.currNode = new Node(null, new Board(data, Character.MIN_VALUE, Utils.findZeroPos(data), width, height));
        this.explored = new HashSet<>();
        this.frontier = new LinkedList<>();
        this.goal = Utils.getGoalZeroLast(width * height);
        this.order = order.clone();
    }
}
