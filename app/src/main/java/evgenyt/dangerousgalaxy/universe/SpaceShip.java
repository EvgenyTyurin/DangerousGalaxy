package evgenyt.dangerousgalaxy.universe;

import java.util.HashMap;
import java.util.Map;

public class SpaceShip {

    public enum Type {
        // DEFAULT
        DOLPHIN(10, 10, 10, 3, 10000),
        // CLASS I
        WANDERER(12, 12, 12, 4, 15000),
        TRUMP(15, 10, 10, 3, 15000),
        EAGLE(10, 15, 10, 3, 15000),
        BLAZE(10, 10, 15, 3, 15000),
        JOURNEY(10, 10, 10, 5, 15000),
        // CLASS II
        WANDERER2(17, 17, 17, 6, 20000),
        TRUMP2(20, 15, 15, 5, 20000),
        EAGLE2(15, 20, 15, 5, 20000),
        BLAZE2(15, 15, 20, 5, 20000),
        JOURNEY2(15, 15, 15, 7, 20000),
        // CLASS III
        WANDERER3(20, 20, 20, 8, 30000),
        TRUMP3(22, 17, 17, 7, 30000),
        EAGLE3(17, 22, 17, 7, 30000),
        BLAZE3(17, 17, 22, 7, 30000),
        JOURNEY3(17, 17, 17, 9, 30000),
        // CLASS IV
        WANDERER4(22, 22, 22, 10, 50000),
        TRUMP4(25, 20, 20, 9, 50000),
        EAGLE4(20, 25, 20, 9, 50000),
        BLAZE4(20, 20, 25, 9, 50000),
        JOURNEY4(20, 20, 20, 11, 50000);

        // Real max fuel of ship ujusted due gameplay and universe size
        private static final int JUMP_MULTI = 20;

        public final int maxCargo;
        public final int attack;
        public final int speed;
        public final int maxFuel;
        public final int price;

        Type(int maxCargo, int attack, int speed, int maxFuel, int price) {
            this.maxCargo = maxCargo;
            this.attack = attack;
            this.speed = speed;
            this.maxFuel = maxFuel * JUMP_MULTI;
            this.price = price;
        }

    }

    Star currentStar;
    Planet currentPlanet;
    Map<Commodity, Integer> cargoList = new HashMap<>();
    Type type;
    int fuel;
    int health = 100;

    public SpaceShip(Star currentStar, Planet currentPlanet, Type type) {
        this.currentStar = currentStar;
        this.currentPlanet = currentPlanet;
        this.type = type;
        fuel = type.maxFuel;
    }

    public SpaceShip() {
    }

    public boolean getDamage(int damage) {
        if (health > damage) {
            setHealth(health - damage);
            return true;
        } else {
            return false;
        }
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getFuel() {
        return fuel;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public boolean debFuel(int burn) {
        if (burn <= fuel) {
            setFuel(fuel - burn);
            return true;
        }
        return false;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
