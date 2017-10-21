package sise.puzzle.solver;

public class DFSSolver extends Solver {

    public final int MAX_DEPTH;

    public DFSSolver(int maxDepth) {
        this.MAX_DEPTH = maxDepth;
    }

    @Override
    public Solution solve(Board board, String order) {
        long timeStart = System.nanoTime();
        init(board, order);
        explored.add(currNode);
        frontier.push(currNode);

        while (!frontier.isEmpty() && !solution.solved) {
            currNode = frontier.pop();
            explorePaths(currNode);
        }

        solution.timeNanos = System.nanoTime() - timeStart;
        solution.finishedNum = explored.size();
        solution.visitedNum = explored.size() - frontier.size();

        return solution;
    }

    private void explorePaths(Node node) {
        solution.maxDepth = Math.max(solution.maxDepth, node.getDepth());

        if (isSolved(node) && !solution.solved) {
            solution.solved = true;
            solution.path = node.getPath();
        }

        if (node.getDepth() < MAX_DEPTH) {
            for (int i = 0; i < order.length && !solution.solved; i++) {
                if (order[i] == 'L') {
                    hashNodeAndExplore(node.getLeftChild());
                } else if (order[i] == 'R') {
                    hashNodeAndExplore(node.getRightChild());
                } else if (order[i] == 'U') {
                    hashNodeAndExplore(node.getUpChild());
                } else if (order[i] == 'D') {
                    hashNodeAndExplore(node.getDownChild());
                }
            }
        }
    }

    private void hashNodeAndExplore(Node node) {
        if (node != null && explored.add(node)) {
            frontier.push(node);
            explorePaths(node);
        }
    }
}
