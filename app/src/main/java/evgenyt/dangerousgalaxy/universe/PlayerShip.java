package evgenyt.dangerousgalaxy.universe;

import evgenyt.dangerousgalaxy.ui.PrefsWork;

public class PlayerShip extends SpaceShip {

    private static final String PREF_CURRENT_STAR = "PlayerShip.CurrentStar";
    private static final String PREF_CURRENT_PLANET = "PlayerShip.CurrentPlanet";
    private static final String PREF_TYPE = "PlayerShip.Type";
    private static final String PREF_CARGO = "PlayerShip.CargoList";
    private static final String PREF_FUEL = "PlayerShip.Fuel";

    Galaxy galaxy = Galaxy.getInstance();

    public PlayerShip(Star currentStar, Planet currentPlanet, Type type) {
        super(currentStar, currentPlanet, type);
        PrefsWork.saveSlot(PREF_CURRENT_STAR, currentStar.getName());
        PrefsWork.saveSlot(PREF_CURRENT_PLANET, currentPlanet.getName());
        PrefsWork.saveSlot(PREF_TYPE, type.toString());
    }

    public PlayerShip() {
        super();
        String starStr = PrefsWork.readSlot(PREF_CURRENT_STAR);
        String planetStr = PrefsWork.readSlot(PREF_CURRENT_PLANET);
        String typeStr = PrefsWork.readSlot(PREF_TYPE);
        String fuelStr = PrefsWork.readSlot(PREF_FUEL);
        if (starStr.equals("")) {
            setCurrentStar(Galaxy.SOL);
            setCurrentPlanet(Galaxy.EARTH);
            setType(Type.DOLPHIN);
            setFuel(type.maxFuel);
        } else {
            currentStar = galaxy.getStar(starStr);
            if (currentStar == null)
                setCurrentStar(Galaxy.SOL);
            else
                currentPlanet = currentStar.getPlanet(planetStr);
            type = Type.valueOf(typeStr);
            if (fuelStr.equals(""))
                fuel = type.maxFuel;
            else
                fuel = Integer.valueOf(fuelStr);
            String cargoStr = PrefsWork.readSlot(PREF_CARGO);
            if (!cargoStr.equals("")) {
                String[] cargoSlots = cargoStr.split(",");
                for (String cargoSlot : cargoSlots) {
                    String[] cargoInfo = cargoSlot.split(":");
                    cargoList.put(new Commodity(Commodity.CommodityType.valueOf(cargoInfo[0])), Integer.valueOf(cargoInfo[1]));
                }
            }
        }
    }

    // Save cargo to prefs
    private void saveCargo() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Commodity commodity : cargoList.keySet())
            stringBuilder.append(commodity + ":" + cargoList.get(commodity) + ",");
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        PrefsWork.saveSlot(PREF_CARGO, stringBuilder.toString());
    }

    @Override
    public boolean moveToCargo(Commodity commodity, int quantity) {
        boolean result = super.moveToCargo(commodity, quantity);
        if (result)
            saveCargo();
        return result;
    }

    @Override
    public boolean moveFromCargo(Commodity commodity, int quantity) {
        boolean result = super.moveFromCargo(commodity, quantity);
        if (result)
            saveCargo();
        return result;
    }

    @Override
    public void setCurrentStar(Star currentStar) {
        super.setCurrentStar(currentStar);
        PrefsWork.saveSlot(PREF_CURRENT_STAR, currentStar.getName());
    }

    @Override
    public void setCurrentPlanet(Planet currentPlanet) {
        super.setCurrentPlanet(currentPlanet);
        if (currentPlanet != null)
            PrefsWork.saveSlot(PREF_CURRENT_PLANET, currentPlanet.getName());
        else
            PrefsWork.saveSlot(PREF_CURRENT_PLANET, "");
    }

    @Override
    public void setType(Type type) {
        super.setType(type);
        PrefsWork.saveSlot(PREF_TYPE, type.toString());
    }

    @Override
    public void setFuel(int fuel) {
        super.setFuel(fuel);
        PrefsWork.saveSlot(PREF_FUEL, String.valueOf(fuel));
    }
}
