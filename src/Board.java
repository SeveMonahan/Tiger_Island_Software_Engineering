class Board {
    // This contains enough spaces for every hexagon on the board.
    // The hexagons are stored in a square array, where each odd y
    // value is offshifted to the right in order to replicate the six neighbors
    // for hexagonal tiling.
    // Higher y value is going "up" while higher x value is going "right."
    private Hexagon[][] hexagonArray;

    private void initalizeHexagonArray() {
        hexagonArray = new Hexagon[200][200];
        for(int i = 0; i < 200; i++) {
            for(int j = 0; j < 200; j++) {
                hexagonArray[i][j] = new Hexagon();
            }
        }
    }

    Board(){
        initalizeHexagonArray();
    }

    Board(Tile first_tile){
        initalizeHexagonArray();
        placeTileNoRestrictions(first_tile, DirectionsHex.LEFT, 100, 100);
    }

    public void placeTileNoRestrictions(Tile tile, DirectionsHex direction, int x, int y){
        hexagonArray[x][y].changeTerrainTypeThoughExplosion(Terrain.VOLCANO);

        Hexagon overwritten_2 = getHexagonNeighbor(x, y, direction);

        Hexagon overwritten_3 = getHexagonNeighbor(x, y, direction.getNextClockwise());

        overwritten_2.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[1]);

        overwritten_3.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[2]);

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

    Hexagon getHexagon(int x, int y){
        return hexagonArray[x][y];
    }

    void setHexagon(int x, int y, Hexagon hex){
        hexagonArray[x][y] = hex;
    }

    int offset(int x){
        if(x % 2 == 1) {
            return 1;
        }else{
            return 0;
        }
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

    public boolean placeTile(Tile tile, DirectionsHex direction, int x, int y) {
        if(canPlaceOutsideCurrentBoard(x, y)){
            placeTileNoRestrictions(tile, direction, x, y);
            return true;
        }

        return false;
    }

    private boolean canPlaceOutsideCurrentBoard(int x, int y) {
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
}
