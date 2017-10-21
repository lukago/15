package sise.puzzle;

public class Board {

    byte[] data;
    byte height;
    byte width;
    char prevMove;
    int zeroIndex;
    int fScore;

    public Board(byte[] data, char prevMove, int zeroIndex, int width, int height) {
        this.data = data.clone();
        this.prevMove = prevMove;
        this.zeroIndex = zeroIndex;
        this.height = (byte) height;
        this.width = (byte) width;
        this.fScore = 0;
    }
}
