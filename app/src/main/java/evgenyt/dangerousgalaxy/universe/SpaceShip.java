package evgenyt.dangerousgalaxy.universe;

import java.util.HashMap;
import java.util.Map;

public class SpaceShip {

    private Star currentStar;
    private Planet currentPlanet;
    private Map<Commodity, Integer> cargoList = new HashMap<>();
    private int maxCargoTonnage = 10;


    public int getCurrentCargoTonnage() {
        int tonnage = 0;
        for (Commodity commodity : cargoList.keySet())
            tonnage += cargoList.get(commodity);
        return tonnage;
    }

    public boolean moveToCargo(Commodity commodity, int quantity) {
        int tonnage = getCurrentCargoTonnage();
        if (getCurrentCargoTonnage() + quantity > maxCargoTonnage)
            return false;
        Integer commodityTonnage = cargoList.get(commodity);
        if (commodityTonnage == null)
            commodityTonnage = 0;
        cargoList.put(commodity, commodityTonnage + quantity);
        return true;
    }

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

    public int getMaxCargoTonnage() {
        return maxCargoTonnage;
    }

    public Map<Commodity, Integer> getCargoList() {
        return cargoList;
    }
}
