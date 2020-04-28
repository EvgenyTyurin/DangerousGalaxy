package evgenyt.dangerousgalaxy.data;

import java.util.ArrayList;
import java.util.List;

import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class Galaxy {
    private static final int STARS_NUM = 100000;
    private static Galaxy galaxy;
    private List<Star> stars = new ArrayList<>(STARS_NUM);
    private SpaceShip playerShip;

    // Reality
    private static Star SOL = new Star(SpaceMath.getRandomPoint(70000));
    private static Planet MERCURY = new Planet("Mercury", SOL, 0);

    private Galaxy(){
        // Reality injector
        SOL.setName("Sol");
        SOL.setStarClass(Star.StarClass.G);
        SOL.getPlanets().clear();
        SOL.getPlanets().add(MERCURY);

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
