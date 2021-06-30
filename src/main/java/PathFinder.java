import java.util.*;

public class PathFinder {

    private CaveData caveData;
    private String currentRoute;
    private Hashtable<String, Boolean> routesTraveled;
    private List<Integer> chamberVisitingSequence;
    private Position currentPosition;

    private List<PositionSnapshot> listOfPositionSnapshots;

    private int idOfStartingChamber;
    private Chamber currentChamber;
    private Random random;

    private int snapNumber = 1;
    private int numberOfSteps = 0;

    public PathFinder(String fileAddress, int idOfStartingChamber){
        this.caveData = MyFileReader.readFile(fileAddress);
        if(caveData == null){
            throw new RuntimeException("Error with reading cave data");
        }

        routesTraveled = new Hashtable<>();
        chamberVisitingSequence = new ArrayList<>();
        listOfPositionSnapshots = new ArrayList<>();
        random = new Random();

        currentChamber = doesStartingChamberExsists(idOfStartingChamber);
        this.idOfStartingChamber = idOfStartingChamber;

        findTheGreatestPath();
    }

    private Chamber doesStartingChamberExsists(int idOfStartingChamber){
        Iterator iterator = caveData.getSetOfChambers().iterator();
        while(iterator.hasNext()){
            Chamber chamber = (Chamber) iterator.next();
            if(chamber.getChamberID() == idOfStartingChamber){
                chamber.setVisited(true);
                currentRoute = idOfStartingChamber + "";
                chamberVisitingSequence.add(idOfStartingChamber);
                return chamber;
            }
        }
        throw new RuntimeException("Chamber does not exists");
    }

    private Position analyzeCurrentPosition(Chamber currentChamber){
        Position currentPosition = new Position();
        List<Chamber> setOfNeighbourChambers = findNonvisitedNeighbourChambers(currentChamber);
        List<Chamber> setOfChambersWithEasyPassageway = new ArrayList<>();
        List<Chamber> setOfChambersWithHardPassageway = new ArrayList<>();

        for (Chamber chamber :
                setOfNeighbourChambers) {
            if (!chamber.isVisited()) {
                for (Passageway passageway :
                        caveData.getListOfPassageways()) {
                    if (passageway.getStartingChamber().equals(currentChamber) && passageway.getEndingChamber().equals(chamber)) {
                        if (passageway.getLevelOfDifficulty() == 0) {
                            setOfChambersWithEasyPassageway.add(chamber);
                        } else {
                            setOfChambersWithHardPassageway.add(chamber);
                        }
                    }
                }
            }
        }
        currentPosition.setListOfNeighbourChambers(setOfNeighbourChambers);
        currentPosition.setListOfNeighbourChambersWithEasyPassageways(setOfChambersWithEasyPassageway);
        currentPosition.setListOfNeighbourChambersWithHardPassageways(setOfChambersWithHardPassageway);
        currentPosition.setCanMoveBackwards(checkIfCanMoveBackwardsFromCurrentPosition(currentChamber));

        return currentPosition;
    }

    private List<Chamber> findNonvisitedNeighbourChambers(Chamber currentChamber){
        List<Chamber> listOfNeighbourChambers = new ArrayList<>();
        Iterator iterator = caveData.getSetOfChambers().iterator();
        while(iterator.hasNext()){
            Chamber chamber = (Chamber) iterator.next();
            if(!chamber.isVisited()){
                for (Passageway passageway:
                        caveData.getListOfPassageways()) {
                    if(passageway.getStartingChamber().equals(currentChamber) && passageway.getEndingChamber().equals(chamber)){
                        listOfNeighbourChambers.add(chamber);
                    }
                }
            }
        }
        return listOfNeighbourChambers;
    }

    private boolean checkIfCanMoveBackwardsFromCurrentPosition(Chamber currentChamber){
        if(chamberVisitingSequence.indexOf(currentChamber.getChamberID()) == -1){
            throw new RuntimeException("Current Chamber does not exsist in Chamber Visiting Sequence");
        } else {
            return (chamberVisitingSequence.indexOf(currentChamber.getChamberID()) != 0);
        }
    }

    public void findTheGreatestPath(){

        boolean areAllChambersVisited = false;
        while(!areAllChambersVisited){
            currentPosition = analyzeCurrentPosition(currentChamber);

            if(currentPosition.getListOfNeighbourChambersWithEasyPassageways().size() != 0){
                Chamber destinationChamber = decideDestinationChamber(currentPosition, 1);
                if(destinationChamber != null){
                    makeAStepForward(destinationChamber);
                } else {
                    if(currentPosition.isCanMoveBackwards()){
                        makeAStepBackward();
                    } else {
                        System.out.println("Proba wykorzystania SNAPSHOT #" + snapNumber++);
                        if(listOfPositionSnapshots.size() != 0){
                            PositionSnapshot positionSnapshot = listOfPositionSnapshots.get(0);
                            currentChamber = positionSnapshot.getCurrentChamber();
                            currentPosition = positionSnapshot.getCurrentPosition();
                            currentRoute = positionSnapshot.getCurrentRoute();
                            chamberVisitingSequence = positionSnapshot.getChamberVisitingSequence();
                            caveData = positionSnapshot.getCaveData();
                            destinationChamber = decideDestinationChamber(currentPosition, 2);
                            if(destinationChamber != null){
                                makeAStepForward(destinationChamber);
                            } else {
                                makeAStepBackward();
                            }
                            listOfPositionSnapshots.remove(0);
                        }
                    }
                }
            } else if (currentPosition.getListOfNeighbourChambersWithHardPassageways().size() != 0){
                takeASnapshotOfCurrentPosition();
                makeAStepBackward();
            } else {
                makeAStepBackward();
            }
            areAllChambersVisited = areAllChambersVisited();
            if(areAllChambersVisited){
                if(checkIfCurrentChamberIsNeighbourOfStartingChamber()){
                    System.out.println("Rozwiązanie - " + currentRoute);
                    System.out.println("Rozwiązanie znalezione w " + numberOfSteps + " krokach");
                } else {
                    makeAStepBackward();
                    areAllChambersVisited = false;
                }
            }
        }
    }

    private boolean checkIfCurrentChamberIsNeighbourOfStartingChamber(){
        for (int i = 0; i < caveData.getListOfPassageways().size(); i++) {
            Passageway passageway = caveData.getListOfPassageways().get(i);
            if(passageway.getStartingChamber().getChamberID() == currentChamber.getChamberID() && passageway.getEndingChamber().getChamberID() == idOfStartingChamber){
                return true;
            }
        }
        return false;
    }

    // routeType 1 = normalny przebieg
    // routeType 2 = snapshot
    private Chamber decideDestinationChamber(Position currentPosition, int routeType){
        List<Chamber> listOfChambersWithEasyPassageways = new ArrayList<>(currentPosition.getListOfNeighbourChambersWithEasyPassageways());
        while(listOfChambersWithEasyPassageways.size() != 0){
            Chamber potencialChamber = listOfChambersWithEasyPassageways.get(random.nextInt(listOfChambersWithEasyPassageways.size()));
            String potencialRoute = currentRoute + "-" + potencialChamber.getChamberID();
            if(!routesTraveled.containsKey(potencialRoute)){
                routesTraveled.put(potencialRoute, true);
                return potencialChamber;
            } else {
                listOfChambersWithEasyPassageways.remove(potencialChamber);
            }
        }
        if(routeType == 2){
            List<Chamber> listOfChambersWithHardPassageways = new ArrayList<>(currentPosition.getListOfNeighbourChambersWithHardPassageways());
            while(listOfChambersWithHardPassageways.size() != 0){
                Chamber potencialChamber = listOfChambersWithHardPassageways.get(random.nextInt(listOfChambersWithHardPassageways.size()));
                String potencialRoute = currentRoute + "-" + potencialChamber.getChamberID();
                if(!routesTraveled.containsKey(potencialRoute)){
                    routesTraveled.put(potencialRoute, true);
                    return potencialChamber;
                } else {
                    listOfChambersWithHardPassageways.remove(potencialChamber);
                }
            }
        }
        return null;
    }

    private void makeAStepForward(Chamber destinationChamber){
        currentRoute += "-" + destinationChamber.getChamberID();
        routesTraveled.put(currentRoute, true);
        destinationChamber.setVisited(true);
        caveData.getSetOfChambers().remove(destinationChamber);
        caveData.getSetOfChambers().add(destinationChamber);
        chamberVisitingSequence.add(destinationChamber.getChamberID());
        currentChamber = destinationChamber;
        System.out.println(currentRoute + " ->");
        numberOfSteps++;
    }

    private void makeAStepBackward(){
        currentChamber.setVisited(false);
        chamberVisitingSequence.remove(chamberVisitingSequence.get(chamberVisitingSequence.size()-1));
        currentChamber = caveData.getChamberByID(chamberVisitingSequence.get(chamberVisitingSequence.size()-1));
        currentRoute = deleteLatestChamberFromRoute();
        System.out.println(currentRoute + " <-");
        numberOfSteps++;
    }

    private void takeASnapshotOfCurrentPosition(){
        PositionSnapshot positionSnapshot = new PositionSnapshot();
        positionSnapshot.setCurrentChamber(currentChamber);
        positionSnapshot.setCurrentPosition(currentPosition);
        positionSnapshot.setCurrentRoute(currentRoute);
        positionSnapshot.setChamberVisitingSequence(chamberVisitingSequence);
        positionSnapshot.setCaveData(caveData);
        listOfPositionSnapshots.add(positionSnapshot);
    }

    private String deleteLatestChamberFromRoute(){
        char[] currentRouteInCharArr = currentRoute.toCharArray();
        int numOfLettersToCut = 0;
        for (int i = currentRoute.length()-1; i >= 0; i--) {
            if(currentRouteInCharArr[i] == '-'){
                numOfLettersToCut++;
                break;
            } else {
                numOfLettersToCut++;
            }
        }
        currentRoute = currentRoute.substring(0, currentRoute.length()-numOfLettersToCut);
        return currentRoute;
    }

    private boolean areAllChambersVisited(){
        Iterator iterator = caveData.getSetOfChambers().iterator();
        while(iterator.hasNext()){
            Chamber chamber = (Chamber) iterator.next();
            if(!chamber.isVisited()){
                return false;
            }
        }
        return true;
    }
}
