package sise.puzzle;

public class Solution {

    private String path;
    private long timeMillis;
    private int maxDepth;
    private int visitedNum;
    private int finishedNum;

    public Solution(String solution, long timeMillis, int maxDepth, int visitedNum, int finishedNum) {
        this.path = solution;
        this.timeMillis = timeMillis;
        this.maxDepth = maxDepth;
        this.visitedNum = visitedNum;
        this.finishedNum = finishedNum;
    }

    public String getPath() {
        return path;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public int getVisitedNum() {
        return visitedNum;
    }

    public int getFinishedNum() {
        return finishedNum;
    }
}
