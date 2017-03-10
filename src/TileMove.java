public class TileMove {
    private final Tile tile;
    private final HexagonNeighborDirection direction;
    private final Coordinate coordinate;

    public TileMove(Tile tile, HexagonNeighborDirection direction, Coordinate coordinate) {
        this.tile = tile;
        this.direction = direction;
        this.coordinate = coordinate;
    }

    public Tile getTile() {
        return tile;
    }

    public HexagonNeighborDirection getDirection() {
        return direction;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }
}
