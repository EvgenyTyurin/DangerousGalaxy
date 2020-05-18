package evgenyt.dangerousgalaxy.universe;

import evgenyt.dangerousgalaxy.ui.PrefsWork;

public class PlayerShip extends SpaceShip {

    private static final String PREF_CURRENT_STAR = "PlayerShip.CurrentStar";
    private static final String PREF_CURRENT_PLANET = "PlayerShip.CurrentPlanet";
    private static final String PREF_TYPE = "PlayerShip.Type";

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
        if (starStr.equals("")) {
            setCurrentStar(Galaxy.SOL);
            setCurrentPlanet(Galaxy.EARTH);
            setType(Type.DOLPHIN);
        } else {
            currentStar = galaxy.getStar(starStr);
            if (currentStar == null)
                setCurrentStar(Galaxy.SOL);
            else
                currentPlanet = currentStar.getPlanet(planetStr);
            type = Type.valueOf(typeStr);
        }
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
}
