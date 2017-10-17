package sise.puzzle;

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

        Solver solver;
        switch (alg) {
            case "bfs":
                solver = new BFSSolver();
                break;
            case "dfs":
                solver = new DFSSolver(21);
                break;
            default:
                solver = new BFSSolver();
                break;
        }

        Board board = Utils.readBoardFromFile(dataPath);
        Solution solution = solver.solve(board, order);
        System.out.println(String.format("Solving %s %s %s...", alg, order, dataPath));
        Utils.writeSolution(solution, resPath);
        Utils.writeStats(solution, statPath);
    }

}
