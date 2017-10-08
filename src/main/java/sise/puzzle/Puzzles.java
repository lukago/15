package sise.puzzle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Puzzles {

    private byte[] puzzles;
    private int zeroIndex;

    public Puzzles(byte[] board) {
        this.puzzles = board.clone();
        this.zeroIndex = findZeroIndex();
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

    public void moveLeft() {
        swapZero(zeroIndex - 1);
    }

    public void moveRight() {
        swapZero(zeroIndex + 1);
    }

    public void moveUp() {
        swapZero(zeroIndex - 4);
    }

    public void moveDown() {
        swapZero(zeroIndex + 4);
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