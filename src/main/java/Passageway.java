public class Passageway {

    private Chamber startingChamber;
    private Chamber endingChamber;
    private int levelOfDifficulty;

    public Passageway(Chamber startingChamber, Chamber endingChamber, int levelOfDifficulty) {
        this.startingChamber = startingChamber;
        this.endingChamber = endingChamber;
        this.levelOfDifficulty = levelOfDifficulty;
    }

    public Chamber getStartingChamber() {
        return startingChamber;
    }

    public void setStartingChamber(Chamber startingChamber) {
        this.startingChamber = startingChamber;
    }

    public Chamber getEndingChamber() {
        return endingChamber;
    }

    public void setEndingChamber(Chamber endingChamber) {
        this.endingChamber = endingChamber;
    }

    public int getLevelOfDifficulty() {
        return levelOfDifficulty;
    }

    public void setLevelOfDifficulty(int levelOfDifficulty) {
        this.levelOfDifficulty = levelOfDifficulty;
    }
}
