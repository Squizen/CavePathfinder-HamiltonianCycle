import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class CaveData {

    private int numberOfPassageways;
    private int numberOfInnerPassageways;
    private int numberOfOuterPassageways;
    private List<Passageway> listOfPassageways;
    private Set<Chamber> setOfChambers;

    public CaveData(int numberOfPassageways, int numberOfInnerPassageways, int numberOfOuterPassageways, List<Passageway> listOfPassageways, Set<Chamber> setOfChambers) {
        this.numberOfPassageways = numberOfPassageways;
        this.numberOfInnerPassageways = numberOfInnerPassageways;
        this.numberOfOuterPassageways = numberOfOuterPassageways;
        this.listOfPassageways = listOfPassageways;
        this.setOfChambers = setOfChambers;
    }

    public int getNumberOfPassageways() {
        return numberOfPassageways;
    }

    public void setNumberOfPassageways(int numberOfPassageways) {
        this.numberOfPassageways = numberOfPassageways;
    }

    public int getNumberOfInnerPassageways() {
        return numberOfInnerPassageways;
    }

    public void setNumberOfInnerPassageways(int numberOfInnerPassageways) {
        this.numberOfInnerPassageways = numberOfInnerPassageways;
    }

    public int getNumberOfOuterPassageways() {
        return numberOfOuterPassageways;
    }

    public void setNumberOfOuterPassageways(int numberOfOuterPassageways) {
        this.numberOfOuterPassageways = numberOfOuterPassageways;
    }

    public List<Passageway> getListOfPassageways() {
        return listOfPassageways;
    }

    public void setListOfPassageways(List<Passageway> listOfPassageways) {
        this.listOfPassageways = listOfPassageways;
    }

    public Set<Chamber> getSetOfChambers() {
        return setOfChambers;
    }

    public void setSetOfChambers(Set<Chamber> setOfChambers) {
        this.setOfChambers = setOfChambers;
    }
    public Chamber getChamberByID(int ID){
        Iterator iterator = setOfChambers.iterator();
        while(iterator.hasNext()){
            Chamber chamber = (Chamber) iterator.next();
            if(chamber.getChamberID() == ID){
                return chamber;
            }
        }
        throw new RuntimeException("There is no Chamber with ID = " + ID);
    }
}
