package evgenyt.dangerousgalaxy.data;

import java.util.ArrayList;
import java.util.List;

import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class Galaxy {
    public static final int STARS_NUM = 100000;
    private static Galaxy galaxy;
    private List<Star> stars = new ArrayList<>(STARS_NUM);

    private Galaxy(){
        for (int i = 5; i <= STARS_NUM; i++) {
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
}
