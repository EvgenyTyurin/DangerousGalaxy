package evgenyt.dangerousgalaxy.universe;

import java.util.HashMap;
import java.util.Map;

import evgenyt.dangerousgalaxy.ui.PrefsWork;

public class Economy {

    public static final String PREFS_STOCK = "Planet.Stock.";
    public enum EconomyType {UNINHABITED, AGRICULTURE, EXTRACTION, INDUSTRIAL, POSTINDUSTRIAL}
    private EconomyType economyType;
    private Map<Commodity,Integer> commoditiesPrices = new HashMap<>();
    private Map<Commodity,Integer> commoditiesStock = new HashMap<>();
    public static Commodity FOODS = new Commodity(Commodity.CommodityType.FOODS);
    public static Commodity MANUFACTURES = new Commodity(Commodity.CommodityType.MANUFACTURES);
    public static Commodity HI_TECHS = new Commodity(Commodity.CommodityType.HI_TECHS);
    public static Commodity MINERALS = new Commodity(Commodity.CommodityType.MINERALS);
    public static Commodity ARTIFACTS = new Commodity(Commodity.CommodityType.ARTIFACTS);
    private boolean isPrefsLoaded = false;
    private Planet planet;

    public Economy(EconomyType economyType, Planet planet) {
        this.planet = planet;
        this.economyType = economyType;
        switch (economyType) {
            case AGRICULTURE:
                setMarketPosition(FOODS, 50, 10);
                setMarketPosition(MANUFACTURES, 500, 10);
                setMarketPosition(HI_TECHS, 1000, 10);
                break;
            case EXTRACTION:
                setMarketPosition(FOODS, 100, 10);
                setMarketPosition(MINERALS, 150, 10);
                setMarketPosition(MANUFACTURES, 500, 10);
                setMarketPosition(HI_TECHS, 1000, 10);
                break;
            case INDUSTRIAL:
                setMarketPosition(FOODS, 100, 10);
                setMarketPosition(MINERALS, 300, 10);
                setMarketPosition(MANUFACTURES, 300, 10);
                setMarketPosition(HI_TECHS, 1000, 10);
                break;
            case POSTINDUSTRIAL:
                setMarketPosition(FOODS, 100, 10);
                setMarketPosition(MINERALS, 200, 10);
                setMarketPosition(MANUFACTURES, 500, 10);
                setMarketPosition(HI_TECHS, 500, 10);
                setMarketPosition(ARTIFACTS, 2000, 10);
                break;
            case UNINHABITED:
                if (SpaceMath.getNextRandom() < 0.1)
                    setMarketPosition(ARTIFACTS, 0, 1);
                break;
            default:
                commoditiesPrices.put(FOODS, 100);
                commoditiesPrices.put(MINERALS, 200);
                commoditiesPrices.put(MANUFACTURES, 500);
                commoditiesPrices.put(HI_TECHS, 1000);
                commoditiesPrices.put(ARTIFACTS, 2000);

        }
    }

    private void loadPrefs() {
        for (Commodity commodity : commoditiesStock.keySet()) {
            String stockStr = PrefsWork.readSlot(PREFS_STOCK + planet + "." + commodity);
            if (!stockStr.equals("")) {
                commoditiesStock.put(commodity, Integer.valueOf(stockStr));
            }
        }
        isPrefsLoaded = true;
    }

    public void debStock(Commodity commodity, int tonnage) {
        int stock = commoditiesStock.get(commodity);
        if (stock < tonnage)
            return;
        stock -= tonnage;
        commoditiesStock.put(commodity, stock);
        PrefsWork.saveSlot(PREFS_STOCK + planet + "." + commodity, String.valueOf(stock));
    }

    private void setMarketPosition(Commodity commodity, int price, int stock) {
        commoditiesPrices.put(commodity, stock);
        commoditiesStock.put(commodity, stock);
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

    public Map<Commodity, Integer> getCommoditiesStock() {
        if (!isPrefsLoaded) {
            loadPrefs();
        }
        return commoditiesStock;
    }

    public boolean isPrefsLoaded() {
        return isPrefsLoaded;
    }

    public void setPrefsLoaded(boolean prefsLoaded) {
        isPrefsLoaded = prefsLoaded;
    }
}
