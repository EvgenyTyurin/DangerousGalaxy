package evgenyt.dangerousgalaxy.universe;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Star
 */

public class Star {

    // Class of star
    public enum StarClass {
        M(Color.valueOf(Color.RED), 0, 0.76, 0, 0),
        K(Color.valueOf(Color.RED), 0.7645, 0.89, 1, 1),
        G(Color.valueOf(Color.YELLOW), 0.89, 0.96, 2, 4),
        F(Color.valueOf(Color.YELLOW), 0.96, 0.99, 4, 5),
        A(Color.valueOf(Color.WHITE), 0.99, 0.996, 5, 6),
        B(Color.valueOf(Color.WHITE), 0.996, 0.999, 7, 8),
        O(Color.valueOf(Color.CYAN), 0.999, 1, 9, 10);

        private final Color color;
        private final double treshLow;
        private final double treshHi;
        private int boilOrbit;
        private int freezOrbit;

        StarClass(Color color, double treshLow, double treshHi, int boilOrbit, int freezOrbit) {
            this.color = color;
            this.treshLow = treshLow;
            this.treshHi = treshHi;
            this.boilOrbit = boilOrbit;
            this.freezOrbit = freezOrbit;
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

        public int getBoilOrbit() {
            return boilOrbit;
        }

        public int getFreezOrbit() {
            return freezOrbit;
        }
    }

    private final SpaceMath.Point coords;
    private String name;
    private StarClass starClass;
    private List<Planet> planets = new ArrayList<>();
    private final static String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public Star (SpaceMath.Point coords, String name, StarClass starClass) {
        this.coords = coords;
        this.name = name;
        this.starClass = starClass;
    }

    public Star(SpaceMath.Point coords) {
        this.coords = coords;
        double random = SpaceMath.getNextRandom();
        starClass = StarClass.M;
        for (StarClass starCl : StarClass.values()) {
            if (random >= starCl.getTreshLow() && random <= starCl.getTreshHi()) {
                starClass = starCl;
                break;
            }
        }
        name = generateName();
        int planetCount = (int) (SpaceMath.getNextRandom() * 10);
        for (int idx = 1; idx <= planetCount; idx++)
            planets.add(new Planet(name + " " + idx, this, idx));
    }

    // Add some planets to star
    void addPlanets(Planet... planets) {
        for (Planet planet : planets)
            this.planets.add(planet);
    }

    // Star name generator AB-XXXX-YYYY
    private String generateName() {
        int length = alphabet.length();
        int idxA = (int) (coords.getRadius() * 0.0001 * (length - 1));
        if (idxA > length - 1)
            idxA = length -1;
        int idxB = (int) ((coords.getAngle() / (Math.PI * 2)) * (length - 1));
        return new StringBuilder().append(alphabet.substring(idxA, idxA + 1))
                .append(alphabet.substring(idxB, idxB + 1))
                .append("-")
                .append(Math.abs(coords.getX()))
                .append("-")
                .append(Math.abs(coords.getY())).toString();
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

    public StarClass getStarClass() {
        return starClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Star star = (Star) o;
        return Objects.equals(name, star.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStarClass(StarClass starClass) {
        this.starClass = starClass;
    }
}
