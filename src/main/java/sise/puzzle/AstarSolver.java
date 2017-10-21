package sise.puzzle;

import java.util.Comparator;
import java.util.Objects;

public class AstarSolver extends Solver {

    @Override
    public Solution solve(Board board, String order) {
        long timeStart = System.currentTimeMillis();

        init(board, order);
        frontier.add(currNode);
        explored.add(currNode);

        while (!frontier.isEmpty()) {
            currNode = getNodeWithMinFscore();
            if (isSolved(currNode)) {
                solution.solved = true;
                solution.path = currNode.getPath();
                break;
            }

            frontier.remove(currNode);

            for (Node neighbour : currNode.getNeighbours()) {
                if (explored.contains(neighbour))
                    continue;

                if (!explored.contains(neighbour)) {
                    if (Objects.equals(order, "manh")) {
                        neighbour.board.fScore = neighbour.getDepth() + manhattanDist(neighbour);
                    } else if (Objects.equals(order, "hamm")){
                        neighbour.board.fScore = neighbour.getDepth() + hammingDist(neighbour);
                    }
                    frontier.add(neighbour);
                    explored.add(neighbour);
                }
            }
        }

        solution.timeMillis = System.currentTimeMillis() - timeStart;
        solution.finishedNum = explored.size();
        solution.visitedNum = explored.size() - frontier.size();

        return solution;
    }

    private Node getNodeWithMinFscore() {
        return frontier.stream()
                .min(Comparator.comparingInt(a -> a.board.fScore))
                .get();
    }

    private int hammingDist(Node node) {
        int dist = 0;
        for (int i = 0; i < node.board.data.length; i++) {
            if (node.board.data[i] != goal[i]) dist++;
        }
        return dist;
    }

    private int manhattanDist(Node node) {
        int dataX, dataY, goalX, goalY, dist = 0;

        for (int i = 0; i < node.board.data.length; i++) {
            if (node.board.data[i] != goal[i]) {
                int goalPos = findPosInGoal(node.board.data[i]);
                dataX = i % node.board.width;
                dataY = i / node.board.width;
                goalX = goalPos % node.board.width;
                goalY = goalPos / node.board.width;
                dist += Math.abs(dataX - goalX) + Math.abs(dataY - goalY);
            }
        }

        return dist;
    }

    private int findPosInGoal(int value) {
        for (int i = 0; i < goal.length; i++) {
            if (goal[i] == value) {
                return i;
            }
        }
        return -1;
    }


}
