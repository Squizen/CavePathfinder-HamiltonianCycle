import java.util.List;

public class Position {

    private List<Chamber> listOfNeighbourChambers;
    private List<Chamber> listOfNeighbourChambersWithEasyPassageways;
    private List<Chamber> listOfNeighbourChambersWithHardPassageways;
    private boolean canMoveBackwards;

    public List<Chamber> getListOfNeighbourChambers() {
        return listOfNeighbourChambers;
    }

    public void setListOfNeighbourChambers(List<Chamber> listOfNeighbourChambers) {
        this.listOfNeighbourChambers = listOfNeighbourChambers;
    }

    public List<Chamber> getListOfNeighbourChambersWithEasyPassageways() {
        return listOfNeighbourChambersWithEasyPassageways;
    }

    public void setListOfNeighbourChambersWithEasyPassageways(List<Chamber> listOfNeighbourChambersWithEasyPassageways) {
        this.listOfNeighbourChambersWithEasyPassageways = listOfNeighbourChambersWithEasyPassageways;
    }

    public List<Chamber> getListOfNeighbourChambersWithHardPassageways() {
        return listOfNeighbourChambersWithHardPassageways;
    }

    public void setListOfNeighbourChambersWithHardPassageways(List<Chamber> listOfNeighbourChambersWithHardPassageways) {
        this.listOfNeighbourChambersWithHardPassageways = listOfNeighbourChambersWithHardPassageways;
    }

    public boolean isCanMoveBackwards() {
        return canMoveBackwards;
    }

    public void setCanMoveBackwards(boolean canMoveBackwards) {
        this.canMoveBackwards = canMoveBackwards;
    }


    public Position() {

    }
}
