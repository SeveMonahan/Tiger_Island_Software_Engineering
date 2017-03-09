import java.util.ArrayList;

public class Settlement {
    private ArrayList<Hexagon> hexagons;

    ArrayList<Hexagon> getHexagons() { return hexagons; }

    Settlement(Hexagon sourceHexagon) {
        hexagons = new ArrayList<Hexagon>();
        hexagons.add(sourceHexagon);
        // TODO: Count all hexagons that are adjacent to the source and count all hexagons adjacent to those.
    }
}
