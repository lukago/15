package sise.puzzle.solver;

public class BFSSolver extends Solver {

    public Solution solve(Board board, String order) {
        long timeStart = System.nanoTime();

        init(board, order);
        hashNode(currNode);

        while (!frontier.isEmpty() && !solution.solved) {
            currNode = frontier.remove();
            explorePaths(currNode);
        }

        solution.timeNanos = System.nanoTime() - timeStart;
        solution.finishedNum = explored.size();
        solution.visitedNum = explored.size() - frontier.size();

        return solution;
    }

    private void explorePaths(Node node) {
        solution.maxDepth = Math.max(solution.maxDepth, node.getDepth());

        if (isSolved(node)) {
            solution.solved = true;
            solution.path = node.getPath();
            return;
        }

        for (char c : order) {
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
    }

    private void hashNode(Node node) {
        if (node != null && explored.add(node)) {
            frontier.add(node);
        }
    }
}
