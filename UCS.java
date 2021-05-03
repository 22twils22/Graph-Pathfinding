public class UCS extends InformedSearch implements SearchAlgorithm {

    @Override
    public String getName() {
        return "UCS";
    }

    protected boolean addCostsToNeighbor(GraphCell graphCell, GraphCell neighbor, GraphCell goal) {
        if (neighbor.geTotalCost() <= graphCell.geTotalCost()) {
            return false;
        }
        neighbor.setAlgorithmDistanceFromStart(graphCell.getAlgorithmDistanceFromStart() + 1);
        return true;
    }

}
