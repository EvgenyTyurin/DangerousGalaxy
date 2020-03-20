package evgenyt.dangerousgalaxy.data;

import java.util.ArrayList;
import java.util.List;

import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class Star {

    private final SpaceMath.Point coords;
    private String name;
    private List<Planet> planets = new ArrayList<>();

    public Star(SpaceMath.Point coords) {
        this.coords = coords;
        name = coords.toString();
        planets.add(new Planet(name + " - 1"));
        planets.add(new Planet(name + " - 2"));
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
