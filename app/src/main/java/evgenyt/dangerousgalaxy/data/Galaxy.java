package evgenyt.dangerousgalaxy.data;

import java.util.ArrayList;
import java.util.List;

import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class Galaxy {
    public static final int STARS_NUM = 100000;
    public static final int RADIUS = STARS_NUM;
    private static Galaxy galaxy;
    private List<Star> stars = new ArrayList<>(STARS_NUM);
    private SpaceShip playerShip;

    private Galaxy(){
        for (int i = 5; i <= STARS_NUM; i++) {
            stars.add(new Star(SpaceMath.getRandomPoint(i)));
        }
        playerShip = new SpaceShip(stars.get(50000));
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
