package sise.puzzle;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Puzzles implements Cloneable {

    private byte[] puzzles;
    private int zeroIndex;
    private StringBuilder movesHist;

    public Puzzles(byte[] board) {
        this.puzzles = board.clone();
        this.zeroIndex = findZeroIndex();
        this.movesHist = new StringBuilder("");
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
        movesHist.append('L');
    }

    public void moveRight() {
        swapZero(zeroIndex + 1);
        movesHist.append('R');
    }

    public void moveUp() {
        swapZero(zeroIndex - 4);
        movesHist.append('U');
    }

    public void moveDown() {
        swapZero(zeroIndex + 4);
        movesHist.append('D');
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

    @Override
    public Puzzles clone() {
        Puzzles newPuzzles = new Puzzles(getPuzzles());
        newPuzzles.setMovesHist(new StringBuilder(movesHist.toString()));
        return newPuzzles;
    }
}