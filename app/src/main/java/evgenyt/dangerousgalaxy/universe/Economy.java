package evgenyt.dangerousgalaxy.universe;

import java.util.HashMap;
import java.util.Map;

public class Economy {
    public enum EconomyType {UNINHABITED, AGRICULTURE, EXTRACTION, INDUSTRIAL, POSTINDUSTRIAL}
    private EconomyType economyType;
    private Map<Commodity,Integer> commoditiesPrices = new HashMap<>();

    public Economy(EconomyType economyType) {
        this.economyType = economyType;
        switch (economyType) {
            case AGRICULTURE:
                commoditiesPrices.put(new Commodity("Foods"), 50);
                commoditiesPrices.put(new Commodity("Manufactures"), 500);
                commoditiesPrices.put(new Commodity("Hi-techs"), 1000);
                break;
            case EXTRACTION:
                commoditiesPrices.put(new Commodity("Foods"), 100);
                commoditiesPrices.put(new Commodity("Minerals"), 150);
                commoditiesPrices.put(new Commodity("Manufactures"), 500);
                commoditiesPrices.put(new Commodity("Hi-techs"), 1000);
                break;
            case INDUSTRIAL:
                commoditiesPrices.put(new Commodity("Foods"), 100);
                commoditiesPrices.put(new Commodity("Minerals"), 300);
                commoditiesPrices.put(new Commodity("Manufactures"), 300);
                commoditiesPrices.put(new Commodity("Hi-techs"), 1000);
                break;
            case POSTINDUSTRIAL:
                commoditiesPrices.put(new Commodity("Foods"), 100);
                commoditiesPrices.put(new Commodity("Minerals"), 200);
                commoditiesPrices.put(new Commodity("Manufactures"), 500);
                commoditiesPrices.put(new Commodity("Hi-techs"), 500);
                commoditiesPrices.put(new Commodity("Artifacts"), 2000);
                break;
            case UNINHABITED:
                break;
            default:
                commoditiesPrices.put(new Commodity("Foods"), 100);
                commoditiesPrices.put(new Commodity("Minerals"), 200);
                commoditiesPrices.put(new Commodity("Manufactures"), 500);
                commoditiesPrices.put(new Commodity("Hi-techs"), 1000);
                commoditiesPrices.put(new Commodity("Artifacts"), 2000);
        }
    }

    public EconomyType getEconomyType() {
        return economyType;
    }

    public Map<Commodity, Integer> getCommoditiesPrices() {
        return commoditiesPrices;
    }

    @Override
    public String toString() {
        return economyType.toString();
    }

}
