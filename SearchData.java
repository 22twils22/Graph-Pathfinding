public class SearchData {
    protected double timeTakenInNanoseconds;
    protected double pathLength;
    protected double memoryUsed;  // in bytes
    protected boolean goalFound;

    public SearchData(
            double timeTakenInNanoseconds,
            double pathLength,
            double memoryUsed,
            boolean goalFound
    ) {
        this.timeTakenInNanoseconds = timeTakenInNanoseconds;
        this.pathLength = pathLength;
        this.memoryUsed = memoryUsed;
        this.goalFound = goalFound;
    }

    public double getTimeTakenInNanoseconds() {
        return timeTakenInNanoseconds;
    }

    public double getPathLength() {
        return pathLength;
    }

    public double getMemoryUsed() {
        return memoryUsed;
    }

    public void setTimeTakenInNanoseconds(double timeTakenInNanoseconds) {
        this.timeTakenInNanoseconds = timeTakenInNanoseconds;
    }

    public void consumeSearchData(SearchData other) {
        this.timeTakenInNanoseconds += other.timeTakenInNanoseconds;
        this.pathLength += other.pathLength;
        this.memoryUsed += other.memoryUsed;
    }

    public void divideDataByNum(double num) {
        this.timeTakenInNanoseconds /= num;
        this.pathLength /= num;
        this.memoryUsed /= num;
    }

    @Override
    public String toString() {
        return "Goal found: " + goalFound
                + System.lineSeparator() + "Time taken (ns): " + timeTakenInNanoseconds
                + System.lineSeparator() + "Memory Used (bytes):" + memoryUsed
                + System.lineSeparator() + "Path length (not valid if goal not found):" + pathLength;
    }

    public boolean getGoalFound() {
        return goalFound;
    }
}
