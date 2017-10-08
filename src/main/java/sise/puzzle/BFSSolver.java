package sise.puzzle;

import java.util.*;

public class BFSSolver {

    private final byte[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    private Puzzles puzzles;
    private Set<String> explored;
    private Deque<byte[]> frontier;
    private StringBuilder solution;

    public String solve(byte[] data) {
        init(data);
        hashState(puzzles.getPuzzles().clone());

        while (!frontier.isEmpty()) {
            puzzles.setPuzzles(frontier.remove());
            moveRight(puzzles);
            if (isSolved()) break;
            moveLeft(puzzles);
            if (isSolved()) break;
            moveUp(puzzles);
            if (isSolved()) break;
            moveDown(puzzles);
            if (isSolved()) break;
        }

        return solution.toString();
    }


    private void hashState(byte[] next) {
        String nextStr = Arrays.toString(next);
        if (!explored.contains(nextStr)) {
            explored.add(nextStr);
            frontier.add(next);
        }
    }

    private void moveLeft(Puzzles puzzles) {
        if (puzzles.canMoveLeft()) {
            puzzles.moveLeft();
            hashState(puzzles.getPuzzles().clone());
            solution.append("L");
        }
    }

    private void moveRight(Puzzles puzzles) {
        if (puzzles.canMoveRight()) {
            puzzles.moveRight();
            hashState(puzzles.getPuzzles().clone());
            solution.append("R");
        }
    }

    private void moveUp(Puzzles puzzles) {
        if (puzzles.canMoveUp()) {
            puzzles.moveUp();
            hashState(puzzles.getPuzzles().clone());
            solution.append("U");
        }
    }

    private void moveDown(Puzzles puzzles) {
        if (puzzles.canMoveDown()) {
            puzzles.moveDown();
            hashState(puzzles.getPuzzles().clone());
            solution.append("D");
        }
    }

    private boolean isSolved() {
        return Arrays.equals(puzzles.getPuzzles(), goal);
    }

    private void init(byte[] data) {
        this.explored = new HashSet<>();
        this.frontier = new LinkedList<>();
        this.solution = new StringBuilder("");
        this.puzzles = new Puzzles(data);
    }
}
