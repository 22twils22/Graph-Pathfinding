public class AStarEuclidean extends InformedSearch {
    protected boolean addCostsToNeighbor(GraphCell graphCell, GraphCell neighbor, GraphCell goal) {
        int newCostFromStart = graphCell.getAlgorithmDistanceFromStart()+1;
        int newCostToGoal = (int)Math.sqrt(Math.pow(neighbor.getRowNum() - goal.getRowNum(), 2) + Math.pow(neighbor.getColumnNum() - goal.getColumnNum(), 2));
        if (neighbor.geTotalCost() <= newCostFromStart + newCostToGoal) {
            return false;
        }
        neighbor.setAlgorithmDistanceFromStart(newCostFromStart);
        neighbor.setAlgorithmDistanceToGoal(newCostToGoal);
        return true;
    }

    @Override
    public String getName() {
        return "AStarEuclidean";
    }

}
