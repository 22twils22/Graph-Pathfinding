import java.awt.*;

public class Graph {
    protected int nRows, nCols, rowStart, columnStart, rowEnd, columnEnd;
    double percentUntraversable;

    protected GraphCell[][] graphCells;

    public Graph(int nRows, int nCols, double percentUntraversable, int rowStart, int columnStart, int rowEnd, int columnEnd) {


        assert(nRows > 0);
        assert(nCols > 0);
        assert(percentUntraversable > 0);
        assert(percentUntraversable < 0.5);

        assert (0 <= rowStart);
        assert (rowStart < nRows);
        assert (0 <= columnStart);
        assert (columnStart < nCols);

        assert (0 <= rowEnd);
        assert (rowEnd < nRows);
        assert (0 <= columnEnd);
        assert (columnEnd < nCols);
        
        graphCells = new GraphCell[nRows][nCols];

        this.nRows = nRows;
        this.nCols = nCols;
        this.rowStart = rowStart;
        this.columnStart = columnStart;
        this.rowEnd = rowEnd;
        this.columnEnd = columnEnd;
        this.percentUntraversable = percentUntraversable;
        
        GenerategraphCells();
        MakeUntraversableCells();
        SetCellNeighbors();
    }

    public void ResetGraph() {
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                graphCells[r][c].reset();
            }
        }
    }

    public GraphCell getGraphCell(int row, int column) {
        return graphCells[row][column];
    }

    public GraphCell getStartGraphCell() {
        return graphCells[rowStart][columnStart];
    }

    public GraphCell getGoalGraphCell() {
        return graphCells[rowEnd][columnEnd];
    }

    public int getNumRows() {
        return nRows;
    }

    public int getNumCols() {
        return nCols;
    }

    public void colorSolutionPath() {
        GraphCell endCell = getGoalGraphCell();
        GraphCell currCell = endCell.getParent();
        GraphCell startCell = getStartGraphCell();
        while (currCell != null && currCell != startCell) {
            currCell.setColor(Color.CYAN);
            currCell = currCell.getParent();
        }
        endCell.setColor(Color.BLUE);
        getStartGraphCell().setColor(Color.RED);
    }



    private void GenerategraphCells() {
        boolean start = false;
        boolean end = false;
        // create all cells of the graph
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                start = r == rowStart && c == columnStart;
                end = r == rowEnd && c == columnEnd;
                graphCells[r][c] = new GraphCell(r, c, start, end);
                if (start) {
                    graphCells[r][c].setAlgorithmDistanceFromStart(0);
                }
            }
        }
    }

    private void MakeUntraversableCells() {
        RandomBooleanGenerator randomBooleanGenerator = new RandomBooleanGenerator(percentUntraversable);
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                boolean untraversable = randomBooleanGenerator.getRandomBool();
                graphCells[r][c].setTraversability(!untraversable);
            }
        }
    }

    private void SetCellNeighbors() {
        // set all neighbors of all cells
        GraphCell graphCell;
        for (int r = 0; r < nRows; r++) {
            for (int c = 0; c < nCols; c++) {
                graphCell = graphCells[r][c];
                if (r > 0 && graphCells[r-1][c].isTraversable()) {
                    graphCell.addNeighbor(graphCells[r-1][c]);
                }
                if (r < nRows - 1 && graphCells[r+1][c].isTraversable()) {
                    graphCell.addNeighbor(graphCells[r+1][c]);
                }
                if (c > 0 && graphCells[r][c-1].isTraversable()) {
                    graphCell.addNeighbor(graphCells[r][c-1]);
                }
                if (c < nCols - 1 && graphCells[r][c+1].isTraversable()) {
                    graphCell.addNeighbor(graphCells[r][c+1]);
                }
            }
        }
    }
    
}
