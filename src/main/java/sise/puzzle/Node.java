package sise.puzzle;

import java.util.Arrays;

public class Node {

    Board board;
    Node parent;

    public Node(Node parent, Board board) {
        this.board = board;
        this.parent = parent;
    }

    public Node getLeftChild() {
        if (board.zeroIndex % board.width != 0) {
            board.swapZero(board.zeroIndex - 1);
            Board childBoard = new Board(board.data, 'L', board.zeroIndex, board.width, board.height);
            Node child = new Node(this, childBoard);
            board.swapZero(board.zeroIndex + 1);
            return child;
        }
        return null;
    }

    public Node getRightChild() {
        if ((board.zeroIndex - board.width + 1) % board.width != 0) {
            board.swapZero(board.zeroIndex + 1);
            Board childBoard = new Board(board.data, 'R', board.zeroIndex, board.width, board.height);
            Node child = new Node(this, childBoard);
            board.swapZero(board.zeroIndex - 1);
            return child;
        }
        return null;
    }

    public Node getUpChild() {
        if ((board.zeroIndex - board.width + 1) > 0) {
            board.swapZero(board.zeroIndex - board.width);
            Board childBoard = new Board(board.data, 'U', board.zeroIndex, board.width, board.height);
            Node child = new Node(this, childBoard);
            board.swapZero(board.zeroIndex + board.width);
            return child;
        }
        return null;
    }

    public Node getDownChild() {
        if (board.zeroIndex - (board.width * board.height - board.width) < 0) {
            board.swapZero(board.zeroIndex + board.width);
            Board childBoard = new Board(board.data, 'D', board.zeroIndex, board.width, board.height);
            Node child = new Node(this, childBoard);
            board.swapZero(board.zeroIndex - board.width);
            return child;
        }
        return null;
    }

    public String getPath() {
        StringBuilder path = new StringBuilder("");
        for (Node node = this; node.parent != null; node = node.parent) {
            path.append(node.board.prevMove);
        }
        return path.reverse().toString();
    }

    public int getDepth() {
        int depth = 0;
        for (Node node = this; node.parent != null; node = node.parent) {
            depth++;
        }
        return depth;
    }

    public Board getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return Arrays.equals(node.board.data, board.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(board.data);
    }
}