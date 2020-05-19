package evgenyt.dangerousgalaxy.universe;

import java.util.Objects;

public class Commodity {

    public enum CommodityType{FOODS, MANUFACTURES, HI_TECHS, MINERALS, ARTIFACTS}

    private CommodityType type;

    public Commodity(CommodityType type) {
        this.type = type;
    }

    public String getName() {
        return type.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Commodity commodity = (Commodity) o;
        return Objects.equals(type, commodity.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }

    @Override
    public String toString() {
        return type.toString();
    }
}
