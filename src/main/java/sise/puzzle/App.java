package sise.puzzle;

import sise.puzzle.solver.*;

public class App {

    public static void main(String[] args) {
        parseArgs(args);
    }

    private static void parseArgs(String[] args) {
        String alg = args[0];
        String order = args[1];
        String dataPath = args[2];
        String resPath = args[3];
        String statPath = args[4];

        Solver solver = parseSolver(alg);
        Board board = Utils.readBoardFromFile(dataPath);
        System.out.println(String.format("Solving %s %s %s...", alg, order, dataPath));
        Solution solution = solver.solve(board, order);
        Utils.writeSolution(solution, resPath);
        Utils.writeStats(solution, statPath);
    }

    private static Solver parseSolver(String arg) {
        switch (arg) {
            case "bfs":
                return new BFSSolver();
            case "dfs":
                return new DFSSolver(20);
            case "astr":
                throw new IllegalArgumentException();
            default:
                throw new IllegalArgumentException();
        }
    }
}