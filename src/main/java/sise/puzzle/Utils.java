package sise.puzzle;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {

    public static int findZeroPos(byte[] data) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] == 0) return i;
        }
        return -1;
    }

    public static byte[] genGoal(int len) {
        byte[] goal = new byte[len];
        for (int i = 0; i < goal.length - 1; i++) {
            goal[i] = (byte) (i + 1);
        }
        goal[goal.length - 1] = 0;
        return goal;
    }

    public static Board readBoardFromFile(String path) {
        int width = 0, height = 0;
        byte[] data = new byte[0];

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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return boardOf(data, width, height);
    }

    public static void writeSolution(Solution solution, String path) {
        try (FileWriter ostream = new FileWriter(path)) {
            if (solution.path.length() > 0) {
                ostream.write(solution.path.length() + "\n");
            } else {
                ostream.write("-1");
            }
            ostream.write(solution.path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            ostream.write(solution.timeMillis + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Board boardOf(byte[] data, int width, int height) {
        return new Board(data, Character.MIN_VALUE, Utils.findZeroPos(data), width, height);
    }

}
