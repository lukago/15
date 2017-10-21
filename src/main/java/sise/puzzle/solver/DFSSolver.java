package sise.puzzle.solver;

public class DFSSolver extends Solver {

    public final int MAX_DEPTH = 21;

    @Override
    public Solution solve(Board board, String order) {
        long timeStart = System.nanoTime();
        init(board, order);

        explored.add(currNode);
        frontier.push(currNode);
        solution.visitedNum++;

        while (!frontier.isEmpty() && !solution.solved) {
            currNode = frontier.pop();
            explorePaths(currNode);
        }

        solution.timeNanos = System.nanoTime() - timeStart;
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
            solution.finishedNum++;
        }
    }

    private void hashNodeAndExplore(Node node) {
        if (node != null && explored.add(node)) {
            solution.visitedNum++;
            frontier.push(node);
            explorePaths(node);
        }
    }
}
