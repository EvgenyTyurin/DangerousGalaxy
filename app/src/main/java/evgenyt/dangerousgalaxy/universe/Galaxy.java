package evgenyt.dangerousgalaxy.universe;

import java.util.ArrayList;
import java.util.List;

public class Galaxy {
    private static final int STARS_NUM = 100000;
    private static Galaxy galaxy;
    private List<Star> stars = new ArrayList<>(STARS_NUM);
    private SpaceShip playerShip;

    // Sol and closest stars
    private static Star SOL = new Star(new SpaceMath.Point(0, 80000), "SOL", Star.StarClass.G);
    private static Star ALPHA_CENTAURI = new Star(new SpaceMath.Point(0.0005, 80050), "ALPHA CENTAURY", Star.StarClass.G);
    private static Star BARNARDS_STAR = new Star(new SpaceMath.Point(-0.0007, 79951), "BARNARD'S STAR", Star.StarClass.M);
    private static Star WOLF_359 = new Star(new SpaceMath.Point(0.0004, 79940), "WOLF 359", Star.StarClass.M);
    private static Star LALANDE_21185 = new Star(new SpaceMath.Point(0.0015, 79960), "LALANDE 21185", Star.StarClass.M);
    private static Star SIRIUS = new Star(new SpaceMath.Point(0.0001, 79920), "SIRIUS", Star.StarClass.A);
    private static Star LUYTEN_726_8 = new Star(new SpaceMath.Point(0.0027, 80020), "LUYTEN 726-8", Star.StarClass.M);
    private static Star ROSS_154 = new Star(new SpaceMath.Point(-0.0015, 80010), "ROSS 154", Star.StarClass.M);
    private static Star ROSS_248 = new Star(new SpaceMath.Point(-0.0015, 79900), "ROSS 248", Star.StarClass.M);
    private static Star EPSILON_ERIDANI = new Star(new SpaceMath.Point(-0.0003, 79850), "EPSILON_ERIDANI", Star.StarClass.K);
    private static Star LACAILLE_9352 = new Star(new SpaceMath.Point(-0.0003, 80150), "LACAILLE 9352", Star.StarClass.M);

    // Solar system planets
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
        SOL.addPlanets(MERCURY, VENUS, EARTH, MARS, JUPITER, SATURN, URANUS, NEPTUNE, PLUTO);
        stars.add(SOL);
        stars.add(ALPHA_CENTAURI);
        stars.add(BARNARDS_STAR);
        stars.add(WOLF_359);
        stars.add(LALANDE_21185);
        stars.add(SIRIUS);
        stars.add(LUYTEN_726_8);
        stars.add(ROSS_154);
        stars.add(ROSS_248);
        stars.add(EPSILON_ERIDANI);
        stars.add(LACAILLE_9352);
        playerShip = new SpaceShip(SOL);
        // Random stars generation
        for (int i = stars.size(); i < STARS_NUM; i++) {
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
