package sise.puzzle.test;

import org.junit.Assert;
import org.junit.Test;
import sise.puzzle.BFSSolver;
import sise.puzzle.DFSSolver;
import sise.puzzle.Node;

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
        Node node = new Node(null, data, Character.MIN_VALUE);
        for (char direction : res.toCharArray()) {
            if (direction == 'R') {
                node = node.getRightChild();
            } else if (direction == 'L') {
                node = node.getLeftChild();
            } else if (direction == 'U') {
                node = node.getUpChild();
            } else if (direction == 'D') {
                node = node.getDownChild();
            }
        }

        return node.getBoard();
    }
}
