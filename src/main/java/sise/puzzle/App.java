package sise.puzzle;

public class App {

    public static void main(String[] args) {
        parseArgs(args);
    }

    private static void parseArgs(String[] args) {
        String alg = args[0];
        String odrer = args[1];
        String dataPath = args[2];
        String resPath = args[3];
        String statPath = args[4];

        Solver solver;
        switch (alg) {
            case "bfs":
                solver = new BFSSolver();
                break;
            case "dfs":
                solver = new DFSSolver();
                break;
            default:
                solver = new BFSSolver();
                break;
        }

        Board board = Utils.readBoardFromFile(dataPath);
        Solution solution = solver.solve(board, odrer);
        System.out.println("Writing results of " + dataPath);
        Utils.writeSolution(solution, resPath);
        Utils.writeStats(solution, statPath);
    }

}
