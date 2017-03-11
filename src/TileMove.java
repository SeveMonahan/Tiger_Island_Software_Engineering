public class TileMove {
    private final Tile tile;
    private final HexagonNeighborDirection direction;
    private final Coordinate coordinate;
    private Board board;
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

    public boolean isPlaceTileLegal(Board board, Hexagon volcanoHexagon, Hexagon hexagon1, Hexagon hexagon2) {
        this.board = board;
        this.volcanoHexagon = volcanoHexagon;
        this.neighborHexagon1 = hexagon1;
        this.neighborHexagon2 = hexagon2;

        if(!areAll3SpotsEqualLevels(getDirection(), getCoordinate())){
            return false;
        }

        if(isVolcanoHexagonLevel0AndAHexagonAdjacentToNonemptyBoard(getDirection(), getCoordinate())){
            return true;
        }

        if(totoroIsInTheWay(getTile(), getDirection(), getCoordinate())) {
            return false;
        }

        if(board.doesTileTotallyOverlapTileBelowIt(getDirection(), getCoordinate())){
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

    // Checks whether all 3 potential spots are level 0 and if one of them has an adjacent level 1+ Hexagon.
    private boolean isVolcanoHexagonLevel0AndAHexagonAdjacentToNonemptyBoard(HexagonNeighborDirection direction, Coordinate coordinate) {
        if(volcanoHexagon.getLevel() != 0){
            return false;
        }

        return board.isAdjacentToNonemptyBoard(direction, coordinate, this);
    }

    // TODO: Here we shouldn't be checking if a Totoro is in the way... instead we should have a
    // function that checks "isPieceInWay" which refers to the Piece canThisBeKilled function call
    private boolean totoroIsInTheWay(Tile tile, HexagonNeighborDirection direction, Coordinate coordinate) {
        if (neighborHexagon1.containsTotoro() || neighborHexagon2.containsTotoro()) {
            return true;
        }

        return false;
    }

}
