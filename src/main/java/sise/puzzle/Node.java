package sise.puzzle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {

    private byte[] board;
    private int zeroIndex;
    private char operator;
    private Node parent;

    public Node(Node parent, byte[] board, char operator, int zeroIndex) {
        this.board = board.clone();
        this.zeroIndex = zeroIndex;
        this.operator = operator;
        this.parent = parent;
    }

    public Node(Node parent, byte[] board, char operator) {
        this(parent, board, operator, -1);
        this.zeroIndex = findZeroIndex();
    }

    public Node getLeftChild() {
        if (zeroIndex % 4 != 0) {
            swapZero(zeroIndex - 1);
            Node child = new Node(this, board, 'L', zeroIndex);
            swapZero(zeroIndex + 1);
            return child;
        }
        return null;
    }

    public Node getRightChild() {
        if ((zeroIndex - 3) % 4 != 0) {
            swapZero(zeroIndex + 1);
            Node child = new Node(this, board, 'R', zeroIndex);
            swapZero(zeroIndex - 1);
            return child;
        }
        return null;
    }

    public Node getUpChild() {
        if (zeroIndex - 3 > 0) {
            swapZero(zeroIndex - 4);
            Node child = new Node(this, board, 'U', zeroIndex);
            swapZero(zeroIndex + 4);
            return child;
        }
        return null;
    }

    public Node getDownChild() {
        if (zeroIndex -12 < 0) {
            swapZero(zeroIndex + 4);
            Node child = new Node(this, board, 'D', zeroIndex);
            swapZero(zeroIndex - 4);
            return child;
        }
        return null;
    }

    public String getPath() {
        StringBuilder path = new StringBuilder("");
        for (Node node = this; node.getParent() != null; node = node.getParent()) {
            path.append(node.getOperator());
        }
        return path.reverse().toString();
    }

    public int getDepth() {
        int depth = 0;
        for (Node node = this; node.getParent() != null; node = node.getParent()) {
            depth++;
        }
        return depth;
    }

    private void swapZero(int index) {
        board[zeroIndex] = board[index];
        board[index] = 0;
        zeroIndex = index;
    }

    private int findZeroIndex() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}