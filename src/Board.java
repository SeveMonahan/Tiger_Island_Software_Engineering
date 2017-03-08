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

        if(areAll3SpotsEqualLevels(direction, x, y)){
            if(areAll3SpotsLevel0AndAdjacentToNonemptyBoard(direction, x, y)){
                placeTileNoRestrictions(tile, direction, x, y);
                return true;
            }
            else if(isHexagonGreaterThanLevel0AndAdjacentToEqualLevel(x, y)){
                if(hexagonArray[x][y].getTerrain() == Terrain.VOLCANO){
                    placeTileNoRestrictions(tile, direction, x, y);
                    return true;
                }
            }
        }


        return false;
    }

    //If all 3 spots are equal level there can be no overhang when placing a tile on these spots
    private boolean areAll3SpotsEqualLevels(DirectionsHex direction, int x, int y){
        Hexagon hexagonNeighbor1 = getHexagonNeighbor(x, y, direction);
        Hexagon hexagonNeighbor2 = getHexagonNeighbor(x, y, direction.getNextClockwise());
        if(hexagonArray[x][y].getLevel() == hexagonNeighbor1.getLevel()
                && hexagonArray[x][y].getLevel() == hexagonNeighbor2.getLevel()
                    && hexagonNeighbor1.getLevel() == hexagonNeighbor2.getLevel()
                )
            return true;

        else
            return false;
    }

    // Checks whether all 3 potential spots are level 0 and if one of them has an adjacent level 1+ Hexagon.
    private boolean areAll3SpotsLevel0AndAdjacentToNonemptyBoard(DirectionsHex direction, int x, int y) {
        Coordinate volcanoNeighbor1Coordinate = getHexagonNeighborCoordinate(x, y, direction);
        Coordinate volcanoNeighbor2Coordinate = getHexagonNeighborCoordinate(x, y, direction.getNextClockwise());
        boolean found_attach_point = false;
        if(hexagonArray[x][y].getLevel() == 0){
           for(Hexagon neighbor : getNeighbors(x, y)){
               if(neighbor.getLevel() != 0){
                   found_attach_point = true;
               }
           }
        }
        if(hexagonArray[volcanoNeighbor1Coordinate.getX()][volcanoNeighbor1Coordinate.getY()].getLevel() == 0){
            for(Hexagon neighbor : getNeighbors(volcanoNeighbor1Coordinate.getX(),volcanoNeighbor1Coordinate.getY())){
                if(neighbor.getLevel() != 0){
                    found_attach_point = true;
                }
            }
        }
        if(hexagonArray[volcanoNeighbor2Coordinate.getX()][volcanoNeighbor2Coordinate.getY()].getLevel() == 0){
            for(Hexagon neighbor : getNeighbors(volcanoNeighbor2Coordinate.getX(),volcanoNeighbor2Coordinate.getY())){
                if(neighbor.getLevel() != 0){
                    found_attach_point = true;
                }
            }
        }
        return found_attach_point;
    }

    private boolean isHexagonGreaterThanLevel0AndAdjacentToEqualLevel(int x, int y){
        boolean found_attach_point = false;
        if(hexagonArray[x][y].getLevel() > 0){
            for(Hexagon neighbor : getNeighbors(x,y)){
                if(neighbor.getLevel() == hexagonArray[x][y].getLevel()){
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
        Coordinate coordinate = getHexagonNeighborCoordinate(x, y, direction);
        return getHexagon(coordinate);
    }

    public Coordinate getHexagonNeighborCoordinate(int x, int y, DirectionsHex direction){
        switch(direction){

            case LEFT:
                return new Coordinate(x-1, y);
            case RIGHT:
               return new Coordinate(x+1, y);
            case UPPERLEFT:
                return new Coordinate(x-1 + offset(y), y+1);
            case UPPERRIGHT:
                return new Coordinate(x + offset(y), y+1);
            case LOWERLEFT:
                return new Coordinate(x-1 + offset(y), y-1);
            case LOWERRIGHT:
                return new Coordinate(x + offset(y), y-1);
        }

        //Effectively an error
        return new Coordinate(0, 0);
    }

    private int offset(int x){
        if(x % 2 == 1) {
            return 1;
        }else{
            return 0;
        }
    }

}
