import java.awt.*;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public abstract class InformedSearch implements SearchAlgorithm {
    @Override
    public SearchData Run(Graph graph) {
        GraphCell start = graph.getStartGraphCell();
        GraphCell end = graph.getGoalGraphCell();

        long startTime = System.nanoTime();
        SearchData searchData = informedSearch(graph, start, end);
        long endTime = System.nanoTime();
        double timeTakenInMilliseconds = ((endTime - startTime)/1000000);
        searchData.setTimeTakenInNanoseconds(timeTakenInMilliseconds);

        return searchData;
    }

    protected SearchData informedSearch(Graph graph, GraphCell start, GraphCell end) {
        Set<GraphCell> fullyVisited = new HashSet<>();
        PriorityQueue<GraphCell> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(start);
        start.addToQueue();
        int maxPriorityQueueSize = priorityQueue.size();
        while(!priorityQueue.isEmpty()) {
            GraphCell top = priorityQueue.remove();
            if (top == end) {
                return new SearchData(0, end.getAlgorithmDistanceFromStart(), (maxPriorityQueueSize + 5) * 8 /*5 local vars plus queue, pointers are 8 bytes*/
                        , true);
            }
            if (top != start) {
                top.setColor(Color.MAGENTA);
            }
            top.removeFromQueue();
            fullyVisited.add(top);
            for (GraphCell neighbor : top.getNeighbors()) {
                if (!fullyVisited.contains(neighbor)) {
                    boolean costChanged = addCostsToNeighbor(top, neighbor, end);
                    if (costChanged) {
                        if (neighbor.isInQueue()) {
                            priorityQueue.remove(neighbor);
                        }
                        priorityQueue.add(neighbor);
                        neighbor.addToQueue();
                        neighbor.setParent(top);
                    }
                }
            }
            maxPriorityQueueSize = priorityQueue.size() > maxPriorityQueueSize ? priorityQueue.size() : maxPriorityQueueSize;
        }
        return new SearchData(0, end.getAlgorithmDistanceFromStart(), (maxPriorityQueueSize + 5) * 8 /*5 local vars plus queue, pointers are 8 bytes*/
                , false);
    }

    // returns true if cost less than it was before for the neighbor, false otherwise
    protected abstract boolean addCostsToNeighbor(GraphCell graphCell, GraphCell neighbor, GraphCell goal);

}
