class Board {
    // This contains enough spaces for every hexagon on the board.
    // The hexagons are stored in a square array, where each odd y
    // value is offshifted to the right in order to replicate the six neighbors
    // for hexagonal tiling.
    // Higher y value is going "up" while higher x value is going "right."
    private Hexagon[][] hexagonArray;

    Hexagon getHexagon(int x, int y){
        return hexagonArray[x][y];
    }

    void setHexagon(int x, int y, Hexagon hex){
        hexagonArray[x][y] = hex;
    }

    Board(){
        initalizeHexagonArray();
    }

    // The first tile placement is arbitary, so we just put it in the center of the board.
    // Note that you must use this initalizer if you want to use placeTile() to place any
    // more tiles.
    Board(Tile first_tile){
        initalizeHexagonArray();
        placeTileNoRestrictions(first_tile, DirectionsHex.LEFT, 100, 100);
    }

    private void initalizeHexagonArray() {
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
    public void placeTileNoRestrictions(Tile tile, DirectionsHex direction, int x, int y){
        hexagonArray[x][y].changeTerrainTypeThoughExplosion(Terrain.VOLCANO);

        Hexagon overwritten_2 = getHexagonNeighbor(x, y, direction);

        Hexagon overwritten_3 = getHexagonNeighbor(x, y, direction.getNextClockwise());

        overwritten_2.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[1]);

        overwritten_3.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[2]);

    }

    // place a tile, abiding by all game rules.
    // Unfinished as of 3/7/2017
    public boolean placeTile(Tile tile, DirectionsHex direction, int x, int y) {
        if(hexagonIsLevel0AndAdjacentToNonemptyBoard(x, y)){
            placeTileNoRestrictions(tile, direction, x, y);
            return true;
        }

        return false;
    }

    // Checks whether a hexagon is level 0 and has an adjacent level 1+ Hexagon.
    private boolean hexagonIsLevel0AndAdjacentToNonemptyBoard(int x, int y) {
        boolean found_attach_point = false;
        if(hexagonArray[x][y].getLevel() == 0){
           for(Hexagon neighbor : getNeighbors(x,y)){
               if(neighbor.getLevel() != 0){
                   found_attach_point = true;
               }
           }
        }
        return found_attach_point;
    }

    Hexagon[] getNeighbors(int x, int y){
        Hexagon[] neighbors = new Hexagon[6];

        int i = 0;

        for(DirectionsHex direction : DirectionsHex.values()) {
            neighbors[i] = getHexagonNeighbor(x, y, direction);
            i++;
        }

        return neighbors;
    }

    private Hexagon getHexagonNeighbor(int x, int y, DirectionsHex direction){
        switch(direction){
            case LEFT:
                return hexagonArray[x-1][y];
            case RIGHT:
                return hexagonArray[x+1][y];
            case UPPERLEFT:
                return hexagonArray[x-1 + offset(y)][y+1];
            case UPPERRIGHT:
                return hexagonArray[x + offset(y)][y+1];
            case LOWERLEFT:
                return hexagonArray[x-1 + offset(y)][y-1];
            case LOWERRIGHT:
                return hexagonArray[x + offset(y)][y-1];
        }
        // Effectively an Error
        return hexagonArray[0][0];
    }

    private int offset(int x){
        if(x % 2 == 1) {
            return 1;
        }else{
            return 0;
        }
    }

}
