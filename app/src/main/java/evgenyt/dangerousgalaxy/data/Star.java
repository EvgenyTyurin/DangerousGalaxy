package evgenyt.dangerousgalaxy.data;

import evgenyt.dangerousgalaxy.utils.SpaceMath;

public class Star {

    private final SpaceMath.Point coords;
    private String name;

    public Star(SpaceMath.Point coords) {
        this.coords = coords;
        name = coords.toString();
    }

    public SpaceMath.Point getCoords() {
        return coords;
    }

    public String getName() {
        return name;
    }
}
