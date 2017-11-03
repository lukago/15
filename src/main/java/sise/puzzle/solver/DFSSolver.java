package sise.puzzle.solver;

public class DFSSolver extends Solver {

    public final int MAX_DEPTH = 22;

    @Override
    public Solution solve(Board board, String order) {
        long timeStart = System.nanoTime();
        init(board, order);

        hashNode(currNode);

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
                    hashNode(node.getLeftChild());
                } else if (c == 'R') {
                    hashNode(node.getRightChild());
                } else if (c == 'U') {
                    hashNode(node.getUpChild());
                } else if (c == 'D') {
                    hashNode(node.getDownChild());
                }
            }
            solution.finishedNum++;
        }
    }

    private void hashNode(Node node) {
        if (node != null && explored.add(node)) {
            solution.visitedNum++;
            frontier.push(node);
        }
    }
}
