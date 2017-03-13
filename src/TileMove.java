public class TileMove {
    private final Tile tile;
    private final HexagonNeighborDirection direction;
    private final Coordinate coordinate;
    private Hexagon volcanoHexagon;
    private Hexagon neighborHexagon1;
    private Hexagon neighborHexagon2;

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

    public boolean isPlaceTileLegal(Hexagon volcanoHexagon, Hexagon hexagon1, Hexagon hexagon2, boolean adjacentToCurrentBoard) {
        this.volcanoHexagon = volcanoHexagon;
        this.neighborHexagon1 = hexagon1;
        this.neighborHexagon2 = hexagon2;

        if(!areAll3SpotsEqualLevels(getDirection(), getCoordinate())){
            return false;
        }

        if(this.volcanoHexagon.getLevel() == 0 && adjacentToCurrentBoard){
            return true;
        }

        if(totoroIsInTheWay(getTile(), getDirection(), getCoordinate())) {
            return false;
        }

        if(doesTileTotallyOverlapTileBelowIt()){
            return false;
        }

        if(volcanoHexagon.getTerrain() == Terrain.VOLCANO){
            return true;
        }

        return false;
    }

    //If all 3 spots are equal level there can be no overhang when placing a tile on these spots
    private boolean areAll3SpotsEqualLevels(HexagonNeighborDirection direction, Coordinate coordinate){
        final int volcano_hexagon_level = volcanoHexagon.getLevel();

        return volcano_hexagon_level == neighborHexagon1.getLevel() && volcano_hexagon_level == neighborHexagon2.getLevel();
    }

    public boolean doesTileTotallyOverlapTileBelowIt() {
        int volcanoTileHashCode = volcanoHexagon.tileHashCode;

        return volcanoTileHashCode == neighborHexagon1.tileHashCode && volcanoTileHashCode == neighborHexagon2.tileHashCode;
    }


    // TODO: We already have a function that does this in Hexagon.containsUnKillablePiece()
    private boolean totoroIsInTheWay(Tile tile, HexagonNeighborDirection direction, Coordinate coordinate) {
        if (neighborHexagon1.containsUnKillablePiece() || neighborHexagon2.containsUnKillablePiece()) {
            return true;
        }

        return false;
    }

}
