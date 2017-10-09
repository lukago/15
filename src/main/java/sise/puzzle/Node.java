package sise.puzzle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {

    private byte[] board;
    private byte height;
    private byte width;
    private char operator;
    private int zeroIndex;
    private Node parent;

    public Node(Node parent, byte[] board, char operator, int zeroIndex, int width, int height) {
        this.board = board.clone();
        this.zeroIndex = zeroIndex;
        this.operator = operator;
        this.parent = parent;
        this.height = (byte) height;
        this.width = (byte) width;
    }

    public Node(Node parent, byte[] board, char operator, int width, int height) {
        this(parent, board, operator, -1, width, height);
        this.zeroIndex = findZeroPos();
    }

    public Node getLeftChild() {
        if (zeroIndex % width != 0) {
            swapZero(zeroIndex - 1);
            Node child = new Node(this, board, 'L', zeroIndex, this.width, this.height);
            swapZero(zeroIndex + 1);
            return child;
        }
        return null;
    }

    public Node getRightChild() {
        if ((zeroIndex - width + 1) % width != 0) {
            swapZero(zeroIndex + 1);
            Node child = new Node(this, board, 'R', zeroIndex, this.width, this.height);
            swapZero(zeroIndex - 1);
            return child;
        }
        return null;
    }

    public Node getUpChild() {
        if ((zeroIndex - width + 1) > 0) {
            swapZero(zeroIndex - width);
            Node child = new Node(this, board, 'U', zeroIndex, this.width, this.height);
            swapZero(zeroIndex + width);
            return child;
        }
        return null;
    }

    public Node getDownChild() {
        if (zeroIndex - (width * height - width) < 0) {
            swapZero(zeroIndex + width);
            Node child = new Node(this, board, 'D', zeroIndex, this.width, this.height);
            swapZero(zeroIndex - width);
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

    private int findZeroPos() {
        for (int i = 0; i < board.length; i++) {
            if (board[i] == 0) return i;
        }
        return -1;
    }
}