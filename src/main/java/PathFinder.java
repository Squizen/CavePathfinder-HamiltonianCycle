import java.util.*;

public class PathFinder {

    private static CaveData dataOfCave;

    private static String currentRoute;
    private static Hashtable<String, Boolean> routesTraveled;
    private static List<Integer> chamberVisitingSequence;

    private boolean areAllChambersVisited = false;

    private Chamber currentChamber;
    private Random random;

    private static Chamber doesStartingChamberExsists(int idOfStartingChamber){
        Iterator iterator = dataOfCave.getSetOfChambers().iterator();
        while(iterator.hasNext()){
            Chamber chamber = (Chamber) iterator.next();
            if(chamber.getChamberID() == idOfStartingChamber){
                chamber.setVisited(true);
                currentRoute = idOfStartingChamber + "";
                chamberVisitingSequence.add(idOfStartingChamber);
                return chamber;
            }
        }
        throw new RuntimeException("Chamber is not exsisting");
    }

    private static List<Chamber> findNeighbourChambers(Chamber currentChamber){
        List<Chamber> listOfNeighbourChambers = new ArrayList<>();
        Iterator iterator = dataOfCave.getSetOfChambers().iterator();
        while(iterator.hasNext()){
            Chamber chamber = (Chamber) iterator.next();
            if(!chamber.isVisited()){
                for (Passageway passageway:
                        dataOfCave.getListOfPassageways()) {
                    if(passageway.getStartingChamber().equals(currentChamber) && passageway.getEndingChamber().equals(chamber)){
                        listOfNeighbourChambers.add(chamber);
                    }
                }
            }
        }
        return listOfNeighbourChambers;
    }

    public void findTheGreatestPath(CaveData caveData, int idOfStartingChamber){

        dataOfCave = caveData;
        routesTraveled = new Hashtable<>();
        chamberVisitingSequence = new ArrayList<>();
        random = new Random();

        currentChamber = doesStartingChamberExsists(idOfStartingChamber);

        while(!areAllChambersVisited){
            makeAMove(currentChamber);
        }
    }

    private void makeAMove(Chamber currentChamber){

        List<Chamber> listOfNeighbourChambers = findNeighbourChambers(currentChamber);
        List<Chamber> listOfChambersWithEasyPassageway = new ArrayList<>();
        List<Chamber> listOfChambersWithHardPassageway = new ArrayList<>();

        // Określenie sąsiadów
        for (Chamber chamber :
                listOfNeighbourChambers) {
            if (!chamber.isVisited()) {
                for (Passageway passageway :
                        dataOfCave.getListOfPassageways()) {
                    if (passageway.getStartingChamber().equals(currentChamber) && passageway.getEndingChamber().equals(chamber)) {
                        if (passageway.getLevelOfDifficulty() == 0) {
                            listOfChambersWithEasyPassageway.add(chamber);
                        } else {
                            listOfChambersWithHardPassageway.add(chamber);
                        }
                    }
                }
            }
        }

        // Priorytetowanie łatwych korytarzy
        for (int i = 0; i < listOfChambersWithEasyPassageway.size(); i++) {
            Chamber potencialChamber = listOfChambersWithEasyPassageway.get(random.nextInt(listOfChambersWithEasyPassageway.size()));
            String potencialRoute = currentRoute + " " + potencialChamber.getChamberID();
            if(!routesTraveled.containsKey(potencialRoute)){
                makeAStepForward(potencialChamber);
                return;
            }
        }

        // W przypadku braku - próba zawrotu i poszukiwanie lepszej drogi
        // Jesli taka jest niemozliwa, przejscie trudnym korytarzem
        for (int i = 0; i < listOfChambersWithHardPassageway.size(); i++) {
            Chamber potencialChamber = listOfChambersWithHardPassageway.get(random.nextInt(listOfChambersWithHardPassageway.size()));
            String potencialRoute = currentRoute + " " + potencialChamber.getChamberID();
            if(!routesTraveled.containsKey(potencialRoute)){
                makeAStepForward(potencialChamber);
                return;
            }
        }
        makeAStepBackward();
        return;
    }

    private void makeAStepForward(Chamber endingChamber){
        currentRoute += " " + endingChamber.getChamberID();
        routesTraveled.put(currentRoute, true);
        endingChamber.setVisited(true);
        dataOfCave.getSetOfChambers().add(endingChamber);
        chamberVisitingSequence.add(endingChamber.getChamberID());
        currentChamber = endingChamber;
    }
    private void makeAStepBackward(){
        currentChamber.setVisited(false);
        chamberVisitingSequence.remove(chamberVisitingSequence.get(chamberVisitingSequence.size()-1));
        currentChamber = dataOfCave.getChamberByID(chamberVisitingSequence.get(chamberVisitingSequence.size()-1));
        currentRoute = currentRoute.substring(0, currentRoute.length()-2);
    }
}
