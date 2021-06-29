import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

public class PositionSnapshot {

    private Gson gson;
    private CaveData caveData;
    private Chamber currentChamber;
    private Position currentPosition;
    private String currentRoute;
    private List<Integer> chamberVisitingSequence;

    public PositionSnapshot(Chamber currentChamber, Position currentPosition, String currentRoute, List<Integer> chamberVisitingSequence) {
        this.currentChamber = currentChamber;
        this.currentPosition = currentPosition;
        this.currentRoute = currentRoute;
        this.chamberVisitingSequence = chamberVisitingSequence;
    }

    public PositionSnapshot(){
        this.gson = new Gson();
    }

    public Chamber getCurrentChamber() {
        return currentChamber;
    }

    public void setCurrentChamber(Chamber currentChamber) {
        String currentChamberAsJSON = gson.toJson(currentChamber);
        Chamber copiedChamber = gson.fromJson(currentChamberAsJSON, Chamber.class);
        this.currentChamber = copiedChamber;
    }

    public Position getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(Position currentPosition) {
        String currentPositionAsJSON = gson.toJson(currentPosition);
        Position copiedPosition = gson.fromJson(currentPositionAsJSON, Position.class);
        this.currentPosition = copiedPosition;
    }

    public String getCurrentRoute() {
        return currentRoute;
    }

    public void setCurrentRoute(String currentRoute) {
        this.currentRoute = String.valueOf(currentRoute);
    }

    public List<Integer> getChamberVisitingSequence() {
        return chamberVisitingSequence;
    }

    public void setChamberVisitingSequence(List<Integer> chamberVisitingSequence) {
        String chamberVisitingSequenceJSON = gson.toJson(chamberVisitingSequence);
        List<Integer> copiedChamberVisitingSequence = gson.fromJson(chamberVisitingSequenceJSON, new TypeToken<List<Integer>>(){}.getType());
        this.chamberVisitingSequence = copiedChamberVisitingSequence;
    }

    public CaveData getCaveData() {
        return caveData;
    }

    public void setCaveData(CaveData caveData) {
        String caveDataJSON = gson.toJson(caveData);
        CaveData copiedCaveData = gson.fromJson(caveDataJSON, CaveData.class);
        this.caveData = copiedCaveData;
    }
}
