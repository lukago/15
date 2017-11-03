package sise.puzzle.solver;

public class DFSSolver extends Solver {

    public final int MAX_DEPTH = 22;

    @Override
    public Solution solve(Board board, String order) {
        long timeStart = System.nanoTime();
        init(board, order);

        checkAndHash(currNode);

        while (!frontier.isEmpty() && !solution.solved) {
            currNode = frontier.pop();
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

        if (node.getDepth() <= MAX_DEPTH) {
            for (int i = 0; i < order.length && !solution.solved; i++) {
                char c = order[i];
                if (c == 'L') {
                    checkAndHash(node.getLeftChild());
                } else if (c == 'R') {
                    checkAndHash(node.getRightChild());
                } else if (c == 'U') {
                    checkAndHash(node.getUpChild());
                } else if (c == 'D') {
                    checkAndHash(node.getDownChild());
                }
            }
        }

        solution.finishedNum++;
    }

    private void checkAndHash(Node node) {
        if (node != null && explored.add(node)) {
            solution.visitedNum++;
            frontier.push(node);
        }
    }
}
