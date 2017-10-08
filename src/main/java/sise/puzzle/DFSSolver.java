package sise.puzzle;

import java.util.*;

public class DFSSolver {

    private final byte[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};
    private final int maxDepth = 11;

    private Puzzles currPuzzles;
    private Set<String> explored;
    private Stack<Puzzles> frontier;
    private int depth;

    public String solve(byte[] data) {
        init(data);
        hashState(currPuzzles);

        while (!frontier.isEmpty()) {
            currPuzzles = frontier.pop();
            if (isSolved()) break;
            explorePaths(currPuzzles);
        }

        return currPuzzles.getMovesHist().toString();
    }

    private void explorePaths(Puzzles puzzles) {
        if (puzzles.getMovesHist().length() < maxDepth) {
            moveLeft(puzzles.clone());
            moveRight(puzzles.clone());
            moveUp(puzzles.clone());
            moveDown(puzzles.clone());
        }
    }

    private void hashState(Puzzles puzzles) {
        String nextStr = Arrays.toString(puzzles.getPuzzles());
        if (!explored.contains(nextStr)) {
            explored.add(nextStr);
            frontier.push(puzzles);
        }
    }

    private void moveLeft(Puzzles puzzles) {
        if (puzzles.canMoveLeft()) {
            puzzles.moveLeft();
            hashState(puzzles);
            explorePaths(puzzles);
        }
    }

    private void moveRight(Puzzles puzzles) {
        if (puzzles.canMoveRight()) {
            puzzles.moveRight();
            hashState(puzzles);
            explorePaths(puzzles);
        }
    }

    private void moveUp(Puzzles puzzles) {
        if (puzzles.canMoveUp()) {
            puzzles.moveUp();
            hashState(puzzles);
            explorePaths(puzzles);
        }
    }

    private void moveDown(Puzzles puzzles) {
        if (puzzles.canMoveDown()) {
            puzzles.moveDown();
            hashState(puzzles);
            explorePaths(puzzles);
        }
    }

    private boolean isSolved() {
        return Arrays.equals(currPuzzles.getPuzzles(), goal);
    }

    private void init(byte[] data) {
        this.explored = new HashSet<>();
        this.frontier = new Stack<>();
        this.currPuzzles = new Puzzles(data);
        this.depth = 0;
    }
}
