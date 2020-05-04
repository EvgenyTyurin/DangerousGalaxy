package evgenyt.dangerousgalaxy.universe;

public class SpaceShip {

    private Star currentStar;
    private Planet currentPlanet;

    public SpaceShip(Star currentStar) {
        this.currentStar = currentStar;
    }

    public Star getCurrentStar() {
        return currentStar;
    }

    public void setCurrentStar(Star currentStar) {
        this.currentStar = currentStar;
    }

    public Planet getCurrentPlanet() {
        return currentPlanet;
    }

    public void setCurrentPlanet(Planet currentPlanet) {
        this.currentPlanet = currentPlanet;
    }
}
