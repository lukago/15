package sise.puzzle.solver;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class PuzzleTest {

    final String o1 = "LRUD";
    final String o2 = "ULRD";
    final String h1 = "hamm";
    final String h2 = "manh";
    final byte[] goal3 = Utils.genGoal(9);
    final byte[] goal4 = Utils.genGoal(16);

    final Board board1 = boardOf(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15}, 4, 4);
    final Board board2 = boardOf(new byte[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 13, 14, 15, 12}, 4, 4);
    final Board board3 = boardOf(new byte[]{1, 2, 3, 4, 5, 6, 11, 7, 14, 13, 10, 8, 9, 15, 0, 12}, 4, 4);
    final Board board5 = boardOf(new byte[]{1, 2, 3, 4, 5, 6, 7, 0, 8}, 3, 3);
    final Board board4 = Utils.readBoardFromFile(Paths.get("src/test/resources/4x4_01_t1.txt").toString());

    @Test
    public void bfsTest() {
        System.out.println("bfs");
        BFSSolver bfsSolver = new BFSSolver();
        Assert.assertArrayEquals(follow(bfsSolver.solve(board1, o1).path, board1), goal4);
        Assert.assertArrayEquals(follow(bfsSolver.solve(board2, o2).path, board2), goal4);
        Assert.assertArrayEquals(follow(bfsSolver.solve(board3, o1).path, board3), goal4);
        Assert.assertArrayEquals(follow(bfsSolver.solve(board4, o2).path, board4), goal4);
        Assert.assertArrayEquals(follow(bfsSolver.solve(board5, o2).path, board5), goal3);
    }

    @Test
    public void dfsTest() {
        System.out.println("dfs");
        DFSSolver dfsSolver = new DFSSolver();
        Assert.assertArrayEquals(follow(dfsSolver.solve(board1, o1).path, board1), goal4);
        Assert.assertArrayEquals(follow(dfsSolver.solve(board2, o2).path, board2), goal4);
        Assert.assertArrayEquals(follow(dfsSolver.solve(board3, o1).path, board3), goal4);
        Assert.assertArrayEquals(follow(dfsSolver.solve(board4, o2).path, board4), goal4);
        Assert.assertArrayEquals(follow(dfsSolver.solve(board5, o2).path, board5), goal3);
    }

    @Test
    public void astrTestHamm() {
        System.out.println("astar hamm");
        AstarSolver astarSolver = new AstarSolver();
        Assert.assertArrayEquals(follow(astarSolver.solve(board1, h1).path, board1), goal4);
        Assert.assertArrayEquals(follow(astarSolver.solve(board2, h1).path, board2), goal4);
        Assert.assertArrayEquals(follow(astarSolver.solve(board3, h1).path, board3), goal4);
        Assert.assertArrayEquals(follow(astarSolver.solve(board4, h1).path, board4), goal4);
        Assert.assertArrayEquals(follow(astarSolver.solve(board5, h1).path, board5), goal3);
    }

    @Test
    public void astrTestManh() {
        System.out.println("astar manh");
        AstarSolver astarSolver = new AstarSolver();
        Assert.assertArrayEquals(follow(astarSolver.solve(board1, h2).path, board1), goal4);
        Assert.assertArrayEquals(follow(astarSolver.solve(board2, h2).path, board2), goal4);
        Assert.assertArrayEquals(follow(astarSolver.solve(board3, h2).path, board3), goal4);
        Assert.assertArrayEquals(follow(astarSolver.solve(board4, h2).path, board4), goal4);
        Assert.assertArrayEquals(follow(astarSolver.solve(board5, h2).path, board5), goal3);
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

    private Board boardOf(byte[] data, int width, int height) {
        int zeroPos = 0;
        for (int i = 0; i < data.length; i++)
            if (data[i] == 0) zeroPos = i;

        return new Board(data, Character.MIN_VALUE, zeroPos, width, height);
    }
}
