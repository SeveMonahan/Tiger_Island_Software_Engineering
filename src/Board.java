class Board {
    // This contains enough spaces for every hexagon on the board.
    // The hexagons are stored in a square array, where each odd y
    // value is offshifted to the right in order to replicate the six neighbors
    // for hexagonal tiling.
    // Higher y value is going "up" while higher x value is going "right."
    private Hexagon[][] hexagonArray;

    Hexagon getHexagon(Coordinate coordinate){
        return hexagonArray[coordinate.getX()][coordinate.getY()];
    }

    void setHexagon(Coordinate coordinate, Hexagon hex){
        hexagonArray[coordinate.getX()][coordinate.getY()] = hex;
    }

    Board(){
        initializeHexagonArray();
    }

    // The first tile placement is arbitary, so we just put it in the center of the board.
    // Note that you must use this initalizer if you want to use placeTile() to place any
    // more tiles.
    Board(Tile first_tile){
        initializeHexagonArray();
        placeTileNoRestrictions(first_tile, HexagonNeighborDirection.LEFT, new Coordinate(100,100));
    }

    private void initializeHexagonArray() {
        hexagonArray = new Hexagon[200][200];
        for(int i = 0; i < 200; i++) {
            for(int j = 0; j < 200; j++) {
                hexagonArray[i][j] = new Hexagon();
            }
        }
    }


    // Place a tile, ignoring all game rules such as the being adjacent to the rest of the board,
    // requiring a volcano to overlap if placing over older tiles, etc.
    // The parameter direction is where to place the first tile going clockwise on the tile,
    // relative the the volcano tile (which is placed by x and y). The last tile is placed
    // one direction more clockwise relative to the volcano then the first non-volcano one.
    public void placeTileNoRestrictions(Tile tile, HexagonNeighborDirection direction, Coordinate coordinate){
        hexagonArray[coordinate.getX()][coordinate.getY()].changeTerrainTypeThoughExplosion(Terrain.VOLCANO);

        Hexagon overwritten_2 = getHexagonNeighbor(coordinate, direction);

        Hexagon overwritten_3 = getHexagonNeighbor(coordinate, direction.getNextClockwise());

        overwritten_2.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[1]);
        overwritten_2.eliminatePieces();

        overwritten_3.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[2]);
        overwritten_3.eliminatePieces();

        // Setting the instance variables to all be the same
        overwritten_2.tileHashCode = tile.hashCode();
        overwritten_3.tileHashCode = tile.hashCode();
        hexagonArray[coordinate.getX()][coordinate.getY()].tileHashCode = tile.hashCode();

    }

    // place a tile, abiding by all game rules.
    public boolean placeTile(TileMove tileMove) {
        Hexagon volcanoHexagon = getHexagon(tileMove.getCoordinate());

        Hexagon overwritten_2 = getHexagonNeighbor(tileMove.getCoordinate(), tileMove.getDirection());

        Hexagon overwritten_3 = getHexagonNeighbor(tileMove.getCoordinate(), tileMove.getDirection().getNextClockwise());

        if (tileMove.isPlaceTileLegal(this, volcanoHexagon, overwritten_2, overwritten_3)) {
            placeTileNoRestrictions(tileMove.getTile(), tileMove.getDirection(), tileMove.getCoordinate());
            return true;
        }

        return false;
    }

    public Hexagon[] getNeighbors(Coordinate coordinate){
        Hexagon[] neighbors = new Hexagon[6];

        int i = 0;

        for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()) {
            neighbors[i] = getHexagonNeighbor(coordinate, direction);
            i++;
        }

        return neighbors;
    }

    public Hexagon getHexagonNeighbor(Coordinate coordinate, HexagonNeighborDirection direction){
        Coordinate neighborCoordinate = coordinate.getHexagonNeighborCoordinate(direction);
        return getHexagon(neighborCoordinate);
    }

    public boolean doesTileTotallyOverlapTileBelowIt(HexagonNeighborDirection hexagonNeighborDirection, Coordinate coordinate) {

        Hexagon overwritten_1 = hexagonArray[coordinate.getX()][coordinate.getY()];
        Hexagon overwritten_2 = getHexagonNeighbor(coordinate, hexagonNeighborDirection);
        Hexagon overwritten_3 = getHexagonNeighbor(coordinate, hexagonNeighborDirection.getNextClockwise());

        if( (overwritten_1.tileHashCode != overwritten_2.tileHashCode && overwritten_1.tileHashCode == overwritten_3.tileHashCode)
                || (overwritten_1.tileHashCode != overwritten_3.tileHashCode && overwritten_1.tileHashCode == overwritten_2.tileHashCode)
                || (overwritten_1.tileHashCode != overwritten_3.tileHashCode && overwritten_2.tileHashCode == overwritten_3.tileHashCode) ) {
            return false;
        } else { return true; }
    }

    boolean isAdjacentToNonemptyBoard(HexagonNeighborDirection direction, Coordinate coordinate, TileMove tileMove) {
        Coordinate volcanoNeighbor1Coordinate = coordinate.getHexagonNeighborCoordinate(direction);
        Coordinate volcanoNeighbor2Coordinate = coordinate.getHexagonNeighborCoordinate(direction.getNextClockwise());

        boolean found_attach_point = false;

        for(Hexagon neighbor : getNeighbors(coordinate)){
            if(neighbor.getLevel() != 0){
                found_attach_point = true;
            }
        }
        for(Hexagon neighbor : getNeighbors(volcanoNeighbor1Coordinate)){
            if(neighbor.getLevel() != 0){
                found_attach_point = true;
            }
        }
        for(Hexagon neighbor : getNeighbors(volcanoNeighbor2Coordinate)){
            if(neighbor.getLevel() != 0){
                found_attach_point = true;
            }
        }

        return found_attach_point;
    }
}