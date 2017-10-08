package sise.puzzle;

public class App {

    public static void main(String[] args) {
        byte[] data1 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0, 15};
        byte[] data2 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0, 13, 14, 15, 12};
        byte[] data3 = {1, 2, 3, 4, 5, 6, 11, 7, 14, 13, 10, 8, 9, 15, 0, 12};

        BFSSolver bfsSolver = new BFSSolver();
        DFSSolver dfsSolver = new DFSSolver();

        System.out.println(bfsSolver.solve(data1));
        System.out.println(bfsSolver.solve(data2));
        System.out.println(bfsSolver.solve(data3));

        System.out.println(dfsSolver.solve(data1));
        //System.out.println(dfsSolver.solve(data2));
        //System.out.println(dfsSolver.solve(data3));

    }

}
