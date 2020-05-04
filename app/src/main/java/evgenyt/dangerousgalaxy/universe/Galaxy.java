package evgenyt.dangerousgalaxy.universe;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {
    private static final int STARS_NUM = 100000;
    private static Galaxy galaxy;
    private List<Star> stars = new ArrayList<>(STARS_NUM);
    private SpaceShip playerShip;

    // Reality
    private static Star SOL = new Star(SpaceMath.getRandomPoint(70000));
    private static Planet MERCURY = SpaceMath.generatePlanet("Mercury", Planet.PlanetType.BARREN);
    private static Planet VENUS = SpaceMath.generatePlanet("Venus", Planet.PlanetType.TOXIC);
    private static Planet EARTH = SpaceMath.generatePlanet("Earth", Planet.PlanetType.EARTH_LIKE);
    private static Planet MARS = SpaceMath.generatePlanet("Mars", Planet.PlanetType.BARREN);
    private static Planet JUPITER = SpaceMath.generatePlanet("Jupiter", Planet.PlanetType.GAS_GIANT);;
    private static Planet SATURN = SpaceMath.generatePlanet("Saturn", Planet.PlanetType.GAS_GIANT);;;
    private static Planet URANUS = SpaceMath.generatePlanet("Uranus", Planet.PlanetType.GAS_GIANT);;;
    private static Planet NEPTUNE = SpaceMath.generatePlanet("Neptune", Planet.PlanetType.GAS_GIANT);;;
    private static Planet PLUTO = SpaceMath.generatePlanet("Pluto", Planet.PlanetType.BARREN);;;

    private Galaxy(){
        // Reality injector
        SOL.setName("Sol");
        SOL.setStarClass(Star.StarClass.G);
        SOL.getPlanets().clear();
        SOL.addPlanets(MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO);
        stars.add(SOL);
        playerShip = new SpaceShip(SOL);
        // Random stars generation
        for (int i = 1; i < STARS_NUM; i++) {
            stars.add(new Star(SpaceMath.getRandomPoint(i)));
        }
    }

    public static Galaxy getInstance() {
        if (galaxy == null) {
            galaxy = new Galaxy();
        }
        return galaxy;
    }

    public List<Star> getStars() {
        return stars;
    }

    public SpaceShip getPlayerShip() {
        return playerShip;
    }
}
