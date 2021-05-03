import java.awt.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class BreadthFirstSearch implements SearchAlgorithm {
    @Override
    public String getName() {
        return "BFS";
    }

    @Override
    public SearchData Run(Graph graph) {
        GraphCell start = graph.getStartGraphCell();
        GraphCell end = graph.getGoalGraphCell();

        long startTime = System.nanoTime();
        SearchData searchData = BFS(graph, start, end);
        long endTime = System.nanoTime();
        double timeTakenInMilliseconds = ((endTime - startTime));
        searchData.setTimeTakenInNanoseconds(timeTakenInMilliseconds);

        return searchData;
    }

    public SearchData BFS(Graph graph, GraphCell start, GraphCell end) {
        Queue<GraphCell> graphCellQueue = new LinkedList<>();
        Set<GraphCell> visited = new HashSet<>();
        start.setAlgorithmDistanceFromStart(0);
        graphCellQueue.add(start);
        double memoryUsed = 0;

        while (!graphCellQueue.isEmpty()) {
            GraphCell currGraphCell = graphCellQueue.remove();
            if (currGraphCell == end) {
                return new SearchData(0, end.getAlgorithmDistanceFromStart(), memoryUsed, true);
            }
            if (currGraphCell != start) {
                currGraphCell.setColor(Color.MAGENTA);
            }
            for (GraphCell neighbor : currGraphCell.getNeighbors()) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    graphCellQueue.add(neighbor);
                    neighbor.setAlgorithmDistanceFromStart(currGraphCell.getAlgorithmDistanceFromStart() + 1);
                    neighbor.setParent(currGraphCell);
                }
            }
            double currMemory = visited.size() * 8 + graphCellQueue.size() * 8;
            if (currMemory > memoryUsed) {
                memoryUsed = currMemory;
            }
        }
        return new SearchData(0, Double.POSITIVE_INFINITY, memoryUsed, false);
    }
}
