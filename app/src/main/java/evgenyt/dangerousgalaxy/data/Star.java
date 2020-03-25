package evgenyt.dangerousgalaxy.data;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class Star {

    public enum StarClass {
        M(Color.valueOf(Color.RED), 0, 0.76),
        K(Color.valueOf(Color.RED), 0.7645, 0.89),
        G(Color.valueOf(Color.YELLOW), 0.89, 0.96),
        F(Color.valueOf(Color.YELLOW), 0.96, 0.99),
        A(Color.valueOf(Color.WHITE), 0.99, 0.996),
        B(Color.valueOf(Color.WHITE), 0.996, 0.999),
        O(Color.valueOf(Color.CYAN), 0.999, 1);

        private final Color color;
        private final double treshLow;
        private final double treshHi;

        StarClass(Color color, double treshLow, double treshHi) {
            this.color = color;
            this.treshLow = treshLow;
            this.treshHi = treshHi;
        }

        public Color getColor() {
            return color;
        }

        public double getTreshLow() {
            return treshLow;
        }

        public double getTreshHi() {
            return treshHi;
        }
    }


    private final SpaceMath.Point coords;
    private String name;
    private List<Planet> planets = new ArrayList<>();

    public Star(SpaceMath.Point coords) {
        this.coords = coords;
        name = coords.toString();
        int planetCount = (int) (SpaceMath.getNextRandom() * 10);
        for (int idx = 1; idx <= planetCount; idx++)
            planets.add(new Planet(name + " " + idx));
    }

    public SpaceMath.Point getCoords() {
        return coords;
    }

    public String getName() {
        return name;
    }

    public List<Planet> getPlanets() {
        return planets;
    }
}
