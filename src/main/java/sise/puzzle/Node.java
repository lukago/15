package sise.puzzle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {

    private byte[] puzzles;
    private int zeroIndex;
    private char operator;
    private Node parent;

    public Node( Node parent, byte[] board, char operator) {
        this.puzzles = board.clone();
        this.zeroIndex = findZeroIndex();
        this.operator = operator;
        this.parent = parent;
    }

    public boolean canMoveRight() {
        return (zeroIndex - 3) % 4 != 0;
    }

    public boolean canMoveLeft() {
        return (zeroIndex % 4 != 0);
    }

    public boolean canMoveUp() {
        return zeroIndex - 3 > 0;
    }

    public boolean canMoveDown() {
        return zeroIndex - 12 < 0;
    }

    public Node getLeftChild() {
        swapZero(zeroIndex - 1);
        Node child = new Node(this, puzzles, 'L');
        swapZero(zeroIndex + 1);
        return child;
    }

    public Node getRightChild() {
        swapZero(zeroIndex + 1);
        Node child = new Node(this, puzzles, 'R');
        swapZero(zeroIndex - 1);
        return child;
    }

    public Node getUpChild() {
        swapZero(zeroIndex - 4);
        Node child = new Node(this, puzzles, 'U');
        swapZero(zeroIndex + 4);
        return child;
    }

    public Node getDownChild() {
        swapZero(zeroIndex + 4);
        Node child = new Node(this, puzzles, 'D');
        swapZero(zeroIndex - 4);
        return child;
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
        puzzles[zeroIndex] = puzzles[index];
        puzzles[index] = 0;
        zeroIndex = index;
    }

    private int findZeroIndex() {
        for (int i = 0; i < puzzles.length; i++) {
            if (puzzles[i] == 0) {
                return i;
            }
        }
        return -1;
    }
}