import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class DepthFirstSearch implements SearchAlgorithm {
    @Override
    public String getName() {
        return "DFS";
    }

    @Override
    public SearchData Run(Graph graph) {
        GraphCell start = graph.getStartGraphCell();
        GraphCell end = graph.getGoalGraphCell();
        Set<GraphCell> visited = new HashSet<>();

        long startTime = System.nanoTime();
        SearchData searchData = DFS(graph, start, end, 0, visited);
        long endTime = System.nanoTime();
        double timeTakenInMilliseconds = ((endTime - startTime));
        searchData.setTimeTakenInNanoseconds(timeTakenInMilliseconds);

        return searchData;
    }

    private SearchData DFS(Graph graph, GraphCell current, GraphCell end, int depth, Set<GraphCell> visited) {
        double memoryUsed = 0;
        visited.add(current);
        if (current.getAlgorithmDistanceFromStart() < depth) {
            current.setAlgorithmDistanceFromStart(depth);
        }
        if (current == end) {
            return new SearchData(0, depth, 52*depth, true);  // memory of stack
        }
        if (depth > 0) {
            current.setColor(Color.MAGENTA);
        }
        for (GraphCell neighbor : current.getNeighbors()) {
            if (visited.contains(neighbor)) {
                continue;
            }
            SearchData result = DFS(graph, neighbor, end, depth+1, visited);
            neighbor.setParent(current);
            if (result.getGoalFound()) {
                return result;
            }
            if (result.getMemoryUsed() > memoryUsed) {
                memoryUsed = result.getMemoryUsed();
            }
        }
        return new SearchData(0, Double.POSITIVE_INFINITY, memoryUsed, false);
    }

}
