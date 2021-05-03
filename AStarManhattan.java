public class AStarManhattan extends InformedSearch {

    protected boolean addCostsToNeighbor(GraphCell graphCell, GraphCell neighbor, GraphCell goal) {
        int newCostFromStart = graphCell.getAlgorithmDistanceFromStart()+1;
        int newCostToGoal = Math.abs(neighbor.getRowNum() - goal.getRowNum()) + Math.abs(neighbor.getColumnNum() - goal.getColumnNum());
        if (neighbor.geTotalCost() <= newCostFromStart + newCostToGoal) {
            return false;
        }
        neighbor.setAlgorithmDistanceFromStart(newCostFromStart);
        neighbor.setAlgorithmDistanceToGoal(newCostToGoal);
        return true;
    }

    @Override
    public String getName() {
        return "AStarManhattan";
    }
}
