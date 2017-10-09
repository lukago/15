package sise.puzzle.test;

import org.junit.Assert;
import org.junit.Test;
import sise.puzzle.*;

import java.util.Arrays;

public class PuzzleTest {
    final String testfilePath1 = getClass().getClassLoader().getResource("4x4_01_t1.txt").getPath();

    final char[] o1 = "LRUD".toCharArray();
    final char[] o2 = "ULRD".toCharArray();

    final int w = 4, h = 4;
    final byte[] goal4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
    final byte[] data1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15};
    final byte[] data2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 13, 14, 15, 12};
    final byte[] data3 = {1, 2, 3, 4, 5, 6, 11, 7, 14, 13, 10, 8, 9, 15, 0, 12};
    final byte[] data4 = Utils.readBoardFromFile(testfilePath1).getData();


    @Test
    public void bfsTest() {
        BFSSolver bfsSolver = new BFSSolver();
        Assert.assertEquals(Arrays.toString(follow(bfsSolver.solve(data1, w, h, o1), data1)), Arrays.toString(goal4));
        Assert.assertEquals(Arrays.toString(follow(bfsSolver.solve(data2, w, h, o2), data2)), Arrays.toString(goal4));
        Assert.assertEquals(Arrays.toString(follow(bfsSolver.solve(data3, w, h, o1), data3)), Arrays.toString(goal4));
        Assert.assertEquals(Arrays.toString(follow(bfsSolver.solve(data4, w, h, o2), data4)), Arrays.toString(goal4));
    }

    @Test
    public void dfsTest() {
        DFSSolver dfsSolver = new DFSSolver();
        Assert.assertEquals(Arrays.toString(follow(dfsSolver.solve(data1, w, h, o1), data1)), Arrays.toString(goal4));
        Assert.assertEquals(Arrays.toString(follow(dfsSolver.solve(data2, w, h, o2), data2)), Arrays.toString(goal4));
        Assert.assertEquals(Arrays.toString(follow(dfsSolver.solve(data3, w, h, o1), data3)), Arrays.toString(goal4));
        Assert.assertEquals(Arrays.toString(follow(dfsSolver.solve(data4, w, h, o2), data4)), Arrays.toString(goal4));
    }

    private byte[] follow(String res, byte[] data) {
        Node node = new Node(null, new Board(data, Character.MIN_VALUE, Utils.findZeroPos(data), w, h));
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
        return node.getBoard().getData();
    }
}
