package evgenyt.dangerousgalaxy.data;

public class SpaceShip {
    private Star currentStar;

    public SpaceShip(Star currentStar) {
        this.currentStar = currentStar;
    }

    public Star getCurrentStar() {
        return currentStar;
    }

    public void setCurrentStar(Star currentStar) {
        this.currentStar = currentStar;
    }
}
