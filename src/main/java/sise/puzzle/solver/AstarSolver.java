package sise.puzzle.solver;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AstarSolver extends Solver {

    private boolean useHamm;
    private Set<Node> frontier;

    @Override
    protected void init(Board board, String order) {
        super.init(board, order);
        this.useHamm = order.equals("hamm");
        this.frontier = new HashSet<>();
    }

    @Override
    public Solution solve(Board board, String order) {
        long timeStart = System.nanoTime();
        init(board, order);

        explored.add(currNode);
        frontier.add(currNode);

        while (!frontier.isEmpty() && !solution.solved) {
            currNode = Collections.min(frontier, (a, b) -> a.board.heuristicScore - b.board.heuristicScore);
            frontier.remove(currNode);
            explorePaths(currNode);
        }

        solution.timeNanos = System.nanoTime() - timeStart;
        return solution;
    }

    private void explorePaths(Node node) {
        solution.maxDepth = Math.max(solution.maxDepth, node.getDepth());

        if (isSolved(node)) {
            solution.solved = true;
            solution.path = node.getPath();
            return;
        }

        Node[] neighbours = node.getNeighbours();
        for (Node neighbour : neighbours) {
            if (neighbour != null && explored.add(neighbour)) {
                neighbour.board.heuristicScore = neighbour.getDepth();
                neighbour.board.heuristicScore += useHamm ? hammingDist(neighbour) : manhattanDist(neighbour);
                frontier.add(neighbour);
                solution.visitedNum++;
            }
        }

        solution.finishedNum++;
    }

    private int hammingDist(Node node) {
        int dist = 0;
        for (int i = 0; i < node.board.data.length; i++) {
            if (node.board.data[i] != goal[i])
                dist++;
        }
        return dist;
    }

    private int manhattanDist(Node node) {
        int dataX, dataY, goalX, goalY, dist = 0;

        for (int i = 0; i < node.board.data.length; i++) {
            if (node.board.data[i] != goal[i]) {
                int goalPos = Utils.indexOf(goal, node.board.data[i]);
                dataX = i % node.board.width;
                dataY = i / node.board.width;
                goalX = goalPos % node.board.width;
                goalY = goalPos / node.board.width;
                dist += Math.abs(dataX - goalX) + Math.abs(dataY - goalY);
            }
        }

        return dist;
    }
}
