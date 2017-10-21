package sise.puzzle.solver;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    /**
     * Reads board data from file.
     * 1st row: "{width} {height}"
     * 2nd - {height} row: space separated values of board row
     * @param path path to file with board
     * @return board object
     */
    public static Board readBoardFromFile(String path) {
        int width = 0, height = 0, zeroPos = 0;
        byte[] data = null;

        try {
            List<int[]> lines = Files.lines(Paths.get(path))
                    .map(line -> Arrays.stream(line.split(" ")).mapToInt(Byte::parseByte).toArray())
                    .collect(Collectors.toList());
            width = lines.get(0)[0];
            height = lines.get(0)[1];
            data = new byte[width * height];

            int index = 0;
            for (int i = 1; i < lines.size(); i++) {
                for (int j = 0; j < lines.get(i).length; j++) {
                    data[index++] = (byte) lines.get(i)[j];
                    if (lines.get(i)[j] == 0) {
                        zeroPos = (i - 1) * width + j;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Board(data, Character.MIN_VALUE, zeroPos, width, height);
    }

    /**
     * Writes solver solution to file.
     * 1st line: solution length or -1 if not found
     * 2nd line: solution path if found
     * @param solution calculated solution
     * @param path path where to save file
     */
    public static void writeSolution(Solution solution, String path) {
        try (FileWriter ostream = new FileWriter(path)) {
            if (solution.path.length() > 0) {
                ostream.write(solution.path.length() + "\n");
                ostream.write(solution.path);
            } else {
                ostream.write("-1");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes stats of solution to file (time as millis).
     * @param solution calculated solution
     * @param path path where to save file
     */
    public static void writeStats(Solution solution, String path) {
        try (FileWriter ostream = new FileWriter(path)) {
            if (solution.path.length() > 0) {
                ostream.write(solution.path.length() + "\n");
            } else {
                ostream.write("-1\n");
            }
            ostream.write(solution.visitedNum + "\n");
            ostream.write(solution.finishedNum + "\n");
            ostream.write(solution.maxDepth + "\n");

            double timeMillis = solution.timeNanos / 1000.0;
            DecimalFormat threeDigits = new DecimalFormat("#0.000");
            ostream.write(threeDigits.format(timeMillis) + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates goal array based on board size
     * @param len width * height of board
     * @return goal array - from one to len - 1, zero last
     */
    public static byte[] genGoal(int len) {
        byte[] goal = new byte[len];
        for (int i = 0; i < goal.length - 1; i++) {
            goal[i] = (byte) (i + 1);
        }
        goal[goal.length - 1] = 0;
        return goal;
    }

}
