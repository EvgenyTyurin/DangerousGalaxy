package evgenyt.dangerousgalaxy.data;

import android.graphics.Color;

import java.util.Objects;

import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class Planet {

    public enum PlanetType {
        GAS_GIANT(Color.valueOf(Color.CYAN)),
        BARREN(Color.valueOf(Color.GRAY)),
        ICY(Color.valueOf(Color.WHITE)),
        TOXIC(Color.valueOf(Color.MAGENTA)),
        EARTH_LIKE(Color.valueOf(Color.GREEN));

        private final Color color;

        PlanetType(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }

    private String name;
    private PlanetType planetType;

    public Planet(String name , Star star, int orbit) {
        this.name = name;
        double rand = SpaceMath.getNextRandom();
        if (rand <= 0.333) {
            planetType = PlanetType.GAS_GIANT;
        } else {
            // Water on planet?
            if (rand >= 0.666) {
                if (orbit >= star.getStarClass().getBoilOrbit() && orbit <= star.getStarClass().getFreezOrbit()) {
                    planetType = PlanetType.EARTH_LIKE;
                } else {
                    if (orbit < star.getStarClass().getBoilOrbit()) {
                        planetType = PlanetType.TOXIC;
                    } else {
                        planetType = PlanetType.ICY;
                    }
                }
            } else {
                planetType = PlanetType.BARREN;
            }
        }
    }

    public String getName() {
        return name;
    }

    public PlanetType getPlanetType() {
        return planetType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Planet planet = (Planet) o;
        return Objects.equals(name, planet.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
