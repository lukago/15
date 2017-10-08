package sise.puzzle.test;

import org.junit.Assert;
import org.junit.Test;
import sise.puzzle.BFSSolver;
import sise.puzzle.DFSSolver;
import sise.puzzle.Puzzles;

import java.util.Arrays;

public class PuzzleTest {

    final byte[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
    final byte[] data1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15};
    final byte[] data2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 13, 14, 15, 12};
    final byte[] data3 = {1, 2, 3, 4, 5, 6, 11, 7, 14, 13, 10, 8, 9, 15, 0, 12};


    @Test
    public void bfsTest() {
        BFSSolver bfsSolver = new BFSSolver();
        Assert.assertEquals(Arrays.toString(moveByResponse(bfsSolver.solve(data1), data1)), Arrays.toString(goal));
        Assert.assertEquals(Arrays.toString(moveByResponse(bfsSolver.solve(data2), data2)), Arrays.toString(goal));
        Assert.assertEquals(Arrays.toString(moveByResponse(bfsSolver.solve(data3), data3)), Arrays.toString(goal));
    }

    @Test
    public void dfsTest() {
        DFSSolver dfsSolver = new DFSSolver();
        Assert.assertEquals(Arrays.toString(moveByResponse(dfsSolver.solve(data1), data1)), Arrays.toString(goal));
        Assert.assertEquals(Arrays.toString(moveByResponse(dfsSolver.solve(data2), data2)), Arrays.toString(goal));
        Assert.assertEquals(Arrays.toString(moveByResponse(dfsSolver.solve(data3), data3)), Arrays.toString(goal));
    }

    private byte[] moveByResponse(String res, byte[] data) {
        Puzzles puzzles = new Puzzles(data);
        for (char direction : res.toCharArray()) {
            if (direction == 'R') {
                puzzles.moveRight();
            } else if (direction == 'L') {
                puzzles.moveLeft();
            } else if (direction == 'U') {
                puzzles.moveUp();
            } else if (direction == 'D') {
                puzzles.moveDown();
            }
        }

        return puzzles.getPuzzles();
    }
}
