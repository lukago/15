package sise.puzzle.solver;

import java.util.HashMap;
import java.util.Map;

public class DFSSolver extends Solver {

    public final int MAX_DEPTH = 20;

    private Map<Node, Node> explored;

    @Override
    protected void init(Board board, String order) {
        super.init(board, order);
        this.explored = new HashMap<>();
    }

    @Override
    public Solution solve(Board board, String order) {
        long timeStart = System.nanoTime();
        init(board, order);

        hashMinCostNode(currNode);

        while (!frontier.isEmpty() && !solution.solved) {
            currNode = frontier.pop();
            explorePaths(currNode);
        }

        solution.timeNanos = System.nanoTime() - timeStart;
        return solution;
    }

    private void explorePaths(Node node) {
        int depth = node.getDepth();
        solution.maxDepth = Math.max(solution.maxDepth, depth);

        if (isSolved(node)) {
            solution.solved = true;
            solution.path = node.getPath();
            return;
        }

        if (depth < MAX_DEPTH) {
            for (int i = 0; i < order.length && !solution.solved; i++) {
                char c = order[i];
                if (c == 'L') {
                    hashMinCostNode(node.getLeftChild());
                } else if (c == 'R') {
                    hashMinCostNode(node.getRightChild());
                } else if (c == 'U') {
                    hashMinCostNode(node.getUpChild());
                } else if (c == 'D') {
                    hashMinCostNode(node.getDownChild());
                }
            }
            solution.maxDepth = Math.max(solution.maxDepth, depth + 1);
            solution.finishedNum++;
        }
    }

    private void hashMinCostNode(Node node) {
        if (node == null) return;

        if (isSolved(node)) {
            solution.visitedNum++;
            solution.solved = true;
            solution.path = node.getPath();
            return;
        }

        Node prevNode = explored.get(node);
        if (prevNode == null || prevNode.getDepth() > node.getDepth()) {
            solution.visitedNum++;
            explored.put(node, node);
            frontier.push(node);
        }
    }
}
