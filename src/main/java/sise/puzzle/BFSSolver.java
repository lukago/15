package sise.puzzle;

import java.util.*;

public class BFSSolver {

    private final byte[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 0};

    private Puzzles currPuzzles;
    private Set<String> explored;
    private Deque<Puzzles> frontier;

    public String solve(byte[] data) {
        init(data);
        hashState(currPuzzles);

        while (!frontier.isEmpty()) {
            currPuzzles = frontier.remove();
            if (isSolved()) break;
            explorePaths();
        }

        return currPuzzles.getMovesHist().toString();
    }

    private void explorePaths() {
        moveLeft(currPuzzles.clone());
        moveRight(currPuzzles.clone());
        moveUp(currPuzzles.clone());
        moveDown(currPuzzles.clone());
    }

    private void hashState(Puzzles puzzles) {
        String nextStr = Arrays.toString(puzzles.getPuzzles());
        if (!explored.contains(nextStr)) {
            explored.add(nextStr);
            frontier.add(puzzles);
        }
    }

    private void moveLeft(Puzzles puzzles) {
        if (puzzles.canMoveLeft()) {
            puzzles.moveLeft();
            hashState(puzzles);
        }
    }

    private void moveRight(Puzzles puzzles) {
        if (puzzles.canMoveRight()) {
            puzzles.moveRight();
            hashState(puzzles);
        }
    }

    private void moveUp(Puzzles puzzles) {
        if (puzzles.canMoveUp()) {
            puzzles.moveUp();
            hashState(puzzles);
        }
    }

    private void moveDown(Puzzles puzzles) {
        if (puzzles.canMoveDown()) {
            puzzles.moveDown();
            hashState(puzzles);
        }
    }

    private boolean isSolved() {
        return Arrays.equals(currPuzzles.getPuzzles(), goal);
    }

    private void init(byte[] data) {
        this.explored = new HashSet<>();
        this.frontier = new LinkedList<>();
        this.currPuzzles = new Puzzles(data);
    }
}
