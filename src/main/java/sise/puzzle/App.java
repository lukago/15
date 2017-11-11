package sise.puzzle;

import sise.puzzle.solver.*;

public class App {

    /**
     * Runs the solver with given args.
     *
     * example runcmd:
     * java -jar solver.jar bfs RLUD data.txt res.txt stat.txt;
     * java -jar solver.jar astr hamm dir/data.txt dir/res.txt dir/stat.txt
     *
     * @param args {astr,bfs,dfs} {permutation of LRDU for bfs/dfs, manh/hamm for astr},
     *             {path to file with board data}, {path to save results}, {path to save stats}
     */
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
        System.out.printf("Solving %s %s %s...\n", alg, order, dataPath);
        Solution solution = solver.solve(board, order);
        Utils.writeSolution(solution, resPath);
        Utils.writeStats(solution, statPath);
    }

    private static Solver parseSolver(String arg) {
        switch (arg) {
            case "bfs":
                return new BFSSolver();
            case "dfs":
                return new DFSSolver();
            case "astr":
                return new AstarSolver();
            default:
                throw new IllegalArgumentException();
        }
    }
}