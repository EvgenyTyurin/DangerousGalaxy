package evgenyt.dangerousgalaxy.universe;

import java.util.HashMap;
import java.util.Map;

public class SpaceShip {

    enum Type {DOLPHIN(10, 10, 10);

        final int maxCargo;
        final int attack;
        final int speed;

        Type(int maxCargo, int attack, int speed) {
            this.maxCargo = maxCargo;
            this.attack = attack;
            this.speed = speed;
        }

    }

    private Star currentStar;
    private Planet currentPlanet;
    private Map<Commodity, Integer> cargoList = new HashMap<>();
    private Type type;

    public SpaceShip(Star currentStar, Planet currentPlanet, Type type) {
        this.currentStar = currentStar;
        this.currentPlanet = currentPlanet;
        this.type = type;
    }

    public int getCurrentCargoTonnage() {
        int tonnage = 0;
        for (Commodity commodity : cargoList.keySet())
            tonnage += cargoList.get(commodity);
        return tonnage;
    }

    public boolean moveToCargo(Commodity commodity, int quantity) {
        int tonnage = getCurrentCargoTonnage();
        if (getCurrentCargoTonnage() + quantity > type.maxCargo)
            return false;
        Integer commodityTonnage = cargoList.get(commodity);
        if (commodityTonnage == null)
            commodityTonnage = 0;
        cargoList.put(commodity, commodityTonnage + quantity);
        return true;
    }

    public boolean moveFromCargo(Commodity commodity, int quantity) {
        Integer commodityTonnage = cargoList.get(commodity);
        if (commodityTonnage < quantity)
            return false;
        cargoList.put(commodity, commodityTonnage - quantity);
        return true;
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
        return type.maxCargo;
    }

    public Map<Commodity, Integer> getCargoList() {
        return cargoList;
    }
}
