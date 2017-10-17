package sise.puzzle;

import org.junit.Assert;
import org.junit.Test;

public class PuzzleTest {
    final String testfilePath1 = getClass().getClassLoader().getResource("4x4_01_t1.txt").getPath();

    final String o1 = "LRUD";
    final String o2 = "ULRD";
    final byte[] goal3 = {1, 2, 3, 4, 5, 6, 7, 8, 0};
    final byte[] goal4 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    final Board board1 = Utils.boardOf(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15}, 4, 4);
    final Board board2 = Utils.boardOf(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 13, 14, 15, 12}, 4, 4);
    final Board board3 = Utils.boardOf(new byte[]{1, 2, 3, 4, 5, 6, 11, 7, 14, 13, 10, 8, 9, 15, 0, 12}, 4, 4);
    final Board board5 = Utils.boardOf(new byte[]{1, 2, 3, 4, 5, 6, 7, 0, 8}, 3, 3);
    final Board board4 = Utils.readBoardFromFile(testfilePath1);


    @Test
    public void bfsTest() {
        BFSSolver bfsSolver = new BFSSolver();
        Assert.assertArrayEquals(follow(bfsSolver.solve(board1, o1).path, board1), goal4);
        Assert.assertArrayEquals(follow(bfsSolver.solve(board2, o2).path, board2), goal4);
        Assert.assertArrayEquals(follow(bfsSolver.solve(board3, o1).path, board3), goal4);
        Assert.assertArrayEquals(follow(bfsSolver.solve(board4, o2).path, board4), goal4);
        Assert.assertArrayEquals(follow(bfsSolver.solve(board5, o2).path, board5), goal3);
    }

    @Test
    public void dfsTest() {
        DFSSolver dfsSolver = new DFSSolver(20);
        if (dfsSolver.maxDepth > 10) {
            Assert.assertArrayEquals(follow(dfsSolver.solve(board1, o1).path, board1), goal4);
            Assert.assertArrayEquals(follow(dfsSolver.solve(board2, o2).path, board2), goal4);
            Assert.assertArrayEquals(follow(dfsSolver.solve(board3, o1).path, board3), goal4);
            Assert.assertArrayEquals(follow(dfsSolver.solve(board4, o2).path, board4), goal4);
            Assert.assertArrayEquals(follow(dfsSolver.solve(board5, o2).path, board5), goal3);
        }
    }

    private byte[] follow(String res, Board board) {
        System.out.println(res);
        Node node = new Node(null, board);
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

        return node.board.data;
    }
}
