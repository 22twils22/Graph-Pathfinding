

import java.util.ArrayList;
import java.lang.Iterable;
import java.awt.Color;
public class GraphCell implements Comparable<GraphCell> {
    // position of the GraphCell
    private int row;
    private int col;

    // properties of the cell in relation to pathfinding algorithms
    private boolean isTraversable;
    private boolean isInQueue;
    private boolean hasBeenVisited;
    private boolean isStart;
    private boolean isGoal;
    private int algorithmDistanceFromStart = Integer.MAX_VALUE / 2;
    private int algorithmDistanceToGoal = Integer.MAX_VALUE / 2;
    private Color color;
    private GraphCell parent;

    private ArrayList<GraphCell> neighbors;

    /*
     * GraphCell Colors:
     * default = gray
     * blocked off = orange
     * start = red
     * goal = blue
     * visited = magenta
     * part of solution path = cyan
     */

    public GraphCell(int row, int col, boolean isStart, boolean isGoal) {
        this.row = row;
        this.col = col;
        this.isStart = isStart;
        if (isStart) {
            this.algorithmDistanceFromStart = 0;
        }
        this.isGoal = isGoal;
        // cells are by default traversable
        isTraversable = true;
        isInQueue = false;
        hasBeenVisited = false;
        neighbors = new ArrayList<>();
        if (isStart) {
            color = Color.RED;
        } else if (isGoal) {
            color = Color.BLUE;
        } else {
            color = Color.GRAY;
        }
    }

    public void reset() {
        algorithmDistanceFromStart = Integer.MAX_VALUE / 2;
        algorithmDistanceToGoal = Integer.MAX_VALUE / 2;
        if (this.isStart) {
            this.algorithmDistanceFromStart = 0;
            color = Color.RED;
        } else if (isGoal) {
            color = Color.BLUE;
        } else {
            color = Color.GRAY;
        }
        isInQueue = false;
        hasBeenVisited = false;

    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public GraphCell getParent() {
        return parent;
    }

    public void setParent(GraphCell parent) {
        this.parent = parent;
    }

    public void addNeighbor(GraphCell neighbor) {
        neighbors.add(neighbor);
    }

    @Override
    public int compareTo(GraphCell other) {
        return this.geTotalCost() - other.geTotalCost();
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof GraphCell) {
            return this == other;
        }
        return false;
    }

    public int geTotalCost() {
        return algorithmDistanceFromStart + algorithmDistanceToGoal;
    }

    public void setAlgorithmDistanceToGoal(int dist) {
        this.algorithmDistanceToGoal = dist;
    }

    public void addToQueue() {
        isInQueue = true;
    }

    public void removeFromQueue() {
        isInQueue = false;
    }

    public void Visit() {
        hasBeenVisited = true;
    }

    public int getRowNum() {
        return this.row;
    }

    public boolean isTraversable() {
        return isTraversable;
    }

    public boolean isInQueue() {
        return isInQueue;
    }

    public boolean isHasBeenVisited() {
        return hasBeenVisited;
    }

    public boolean isStart() {
        return isStart;
    }

    public boolean isGoal() {
        return isGoal;
    }

    public Iterable<GraphCell> getNeighbors() {
        return neighbors;
    }

    public int getColumnNum() {
        return this.col;
    }

    public void setTraversability(boolean traversable) {
        this.isTraversable = traversable;
        if (!this.isTraversable) {
            this.color = Color.orange;
        }
    }

    public void setAlgorithmDistanceFromStart(int distance) {
        this.algorithmDistanceFromStart = distance;
    }

    public int getAlgorithmDistanceFromStart() {
        return algorithmDistanceFromStart;
    }
}
