import java.awt.*;
import javax.swing.*;

/*
This code was inspired by code from the following url:
https://www3.ntu.edu.sg/home/ehchua/programming/java/J4b_CustomGraphics.html
 */

public class GraphDrawer extends JFrame {
    public static final int CANVAS_WIDTH  = 1000;
    public static final int CANVAS_HEIGHT = 1000;

    private GraphCanvas canvas;

    public GraphDrawer(Graph graph) {
        canvas = new GraphCanvas();
        canvas.setGraph(graph);
        canvas.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        Container cp = getContentPane();
        cp.add(canvas);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setTitle("Pathfinding Algorithm Graph Results");
        setVisible(true);
        setSize(1000,1000);
    }

    private class GraphCanvas extends JPanel {

        private Graph graph = null;
        private int gridSideLength = 10;

        public void setGraph(Graph graph) {
            this.graph = graph;
        }

        // Override paintComponent to perform your own painting
        @Override
        public void paintComponent(Graphics g) {
            if (graph == null) {
                throw new NullPointerException("Can't draw a null graph");
            }
            setBackground(Color.WHITE);
            super.paintComponent(g);
            for (int r = 0; r < graph.getNumRows(); r++) {
                for (int c = 0; c < graph.getNumCols(); c++) {
                    GraphCell graphCell = graph.getGraphCell(r,c);
                    g.setColor(graphCell.getColor());
                    g.fillRect(r*gridSideLength, c*gridSideLength, gridSideLength, gridSideLength);
                    g.setColor(Color.BLACK);
                    g.drawRect(r*gridSideLength, c*gridSideLength, gridSideLength, gridSideLength);
                }
            }
        }
    }
}
