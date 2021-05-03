import javax.swing.*;
import java.util.Scanner;

public class Main {
    private static final int NUM_ITERATIONS = 50;
    private static final int NUM_ROWS = 70;
    private static final int NUM_COLUMNS = 70;
    private static final int START_ROW = 0;
    private static final int START_COLUMN = 0;
    private static final int GOAL_ROW = NUM_ROWS - 1;
    private static final int GOAL_COLUMN = NUM_COLUMNS - 1;
    private static final double[] PERCENT_UNTRAVERSABLES = {0.1, 0.15, 0.2, 0.25, 0.3};

    public static void main(String[] args) {
        System.out.println("Hello! Welcome to Tyler Wilson's final project for CSCI 4511W.");
        System.out.println("Type 'i' if you would like to run a single instance of a search algorithm. To run the experiment, type anything else.");
        Scanner scanner = new Scanner(System.in);

        SearchAlgorithm[] searchAlgorithms = {
                new BreadthFirstSearch(),
                new DepthFirstSearch(),
                new UCS(),
                new AStarEuclidean(),
                new AStarManhattan(),
        };

        String userInput = scanner.nextLine();  // Read user input
        if (userInput.equals("i" + System.lineSeparator()) || userInput.equals("i")) {
            System.out.println("Running single instance of simulation and generating visual representation:");
            System.out.println("Please select which algorithm you would like to run:" + System.lineSeparator() +
                    "Enter 0 for BFS" + System.lineSeparator() +
                    "Enter 1 for DFS" + System.lineSeparator() +
                    "Enter 2 for UCS" + System.lineSeparator() +
                    "Enter 3 for Euclidean A*" + System.lineSeparator() +
                    "Enter 4 for Manhattan A*" + System.lineSeparator()
            );

            int intInput = scanner.nextInt();
            assert(intInput >=0);
            assert(intInput < 5);
            SearchAlgorithm algorithm = searchAlgorithms[intInput];

            System.out.println("Please enter a number between 0.0 and 0.3 for the proportion of cells to be untraversable in the graph:");
            double percentUntraversable = scanner.nextDouble();

            assert(percentUntraversable >= 0.0);
            assert(percentUntraversable <= 0.3);

            Graph graph = new Graph(NUM_ROWS, NUM_COLUMNS, percentUntraversable, START_ROW, START_COLUMN, GOAL_ROW, GOAL_COLUMN);

            SearchData searchData = algorithm.Run(graph);
            graph.colorSolutionPath();
            System.out.println("results for " + algorithm.getName() +":");
            System.out.println(searchData);
            System.out.println("Displaying visual representation of search...");
            System.out.println("Color scheme of graph:" + System.lineSeparator() +
                    " default = gray\n" +
                    " blocked off = orange\n" +
                    " start = red\n" +
                    " goal = blue\n" +
                    " visited = magenta\n" +
                    " part of solution path = cyan");

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new GraphDrawer(graph);
                }
            });
            return;
        }

        System.out.println(NUM_ROWS + " by " + NUM_COLUMNS + " results:");

        for (double percentUntraversable : PERCENT_UNTRAVERSABLES) {

            SearchData[] cumSearchDatas = new SearchData[searchAlgorithms.length];
            for (int i = 0; i < cumSearchDatas.length; i++) {
                cumSearchDatas[i] = new SearchData(0, 0, 0, true);
            }

            int[] numGoalsFound = new int[searchAlgorithms.length];
            for (int i = 0; i < numGoalsFound.length; i++) {
                numGoalsFound[i] = 0;
            }

            for (int iter = 0; iter < NUM_ITERATIONS; iter++) {

                Graph graph = new Graph(NUM_ROWS, NUM_COLUMNS, percentUntraversable, START_ROW, START_COLUMN, GOAL_ROW, GOAL_COLUMN);

                for (int index = 0; index < searchAlgorithms.length; index++) {
                    SearchAlgorithm algorithm = searchAlgorithms[index];
                    SearchData cumSearchData = cumSearchDatas[index];
                    SearchData searchData = algorithm.Run(graph);
                    if (searchData.getGoalFound()) {
                        cumSearchData.consumeSearchData(searchData);
                        numGoalsFound[index]++;
                    }
                    graph.ResetGraph();
                }
            }

            System.out.println("Cumulative results for " + percentUntraversable + " percent untraversable grid:");
            for (int index = 0; index < searchAlgorithms.length; index++) {
                cumSearchDatas[index].divideDataByNum(numGoalsFound[index]);
                System.out.println(searchAlgorithms[index].getName() + ":");
                System.out.println(cumSearchDatas[index]);
                System.out.println("number of goals found: " + numGoalsFound[index] + " out of " + NUM_ITERATIONS);
                System.out.println();
            }

            System.out.println();
            System.out.println();

        }  // iteration over percent untraversables
    }
}
