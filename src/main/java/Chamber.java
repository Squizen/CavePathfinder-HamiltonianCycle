import java.util.Objects;

public class Chamber {

    private int chamberID;
    private boolean isVisited;

    public Chamber(int chamberID, boolean isVisited) {
        this.chamberID = chamberID;
        this.isVisited = isVisited;
    }
    public Chamber(Chamber chamber){
        this.chamberID = chamber.getChamberID();
        this.isVisited = chamber.isVisited();
    }

    public int getChamberID() {
        return chamberID;
    }

    public void setChamberID(int chamberID) {
        this.chamberID = chamberID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chamber chamber = (Chamber) o;
        return chamberID == chamber.chamberID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chamberID);
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }
}
