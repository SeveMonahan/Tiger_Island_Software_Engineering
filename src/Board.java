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
        placeTileNoRestrictions(first_tile, DirectionsHex.LEFT, new Coordinate(100,100));
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
    public void placeTileNoRestrictions(Tile tile, DirectionsHex direction, Coordinate coordinate){
        hexagonArray[coordinate.getX()][coordinate.getY()].changeTerrainTypeThoughExplosion(Terrain.VOLCANO);

        Hexagon overwritten_2 = getHexagonNeighbor(coordinate, direction);

        Hexagon overwritten_3 = getHexagonNeighbor(coordinate, direction.getNextClockwise());

        overwritten_2.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[1]);

        overwritten_3.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[2]);

    }

    // place a tile, abiding by all game rules.
    // Unfinished as of 3/7/2017
    public boolean placeTile(Tile tile, DirectionsHex direction, Coordinate coordinate) {

        if(areAll3SpotsEqualLevels(direction, coordinate)){
            if(areAll3SpotsLevel0AndAdjacentToNonemptyBoard(direction, coordinate)){
                placeTileNoRestrictions(tile, direction, coordinate);
                return true;
            }
            else if(isHexagonGreaterThanLevel0AndAdjacentToEqualLevel(coordinate)){
                if(hexagonArray[coordinate.getX()][coordinate.getY()].getTerrain() == Terrain.VOLCANO){
                    placeTileNoRestrictions(tile, direction, coordinate);
                    return true;
                }
            }
        }


        return false;
    }

    //If all 3 spots are equal level there can be no overhang when placing a tile on these spots
    private boolean areAll3SpotsEqualLevels(DirectionsHex direction, Coordinate coordinate){
        Hexagon hexagonNeighbor1 = getHexagonNeighbor(coordinate, direction);
        Hexagon hexagonNeighbor2 = getHexagonNeighbor(coordinate, direction.getNextClockwise());
        if(hexagonArray[coordinate.getX()][coordinate.getY()].getLevel() == hexagonNeighbor1.getLevel()
                && hexagonArray[coordinate.getX()][coordinate.getY()].getLevel() == hexagonNeighbor2.getLevel()
                    && hexagonNeighbor1.getLevel() == hexagonNeighbor2.getLevel()
                )
            return true;

        else
            return false;
    }

    // Checks whether all 3 potential spots are level 0 and if one of them has an adjacent level 1+ Hexagon.
    private boolean areAll3SpotsLevel0AndAdjacentToNonemptyBoard(DirectionsHex direction, Coordinate coordinate) {
        Coordinate volcanoNeighbor1Coordinate = getHexagonNeighborCoordinate(coordinate, direction);
        Coordinate volcanoNeighbor2Coordinate = getHexagonNeighborCoordinate(coordinate, direction.getNextClockwise());
        boolean found_attach_point = false;
        if(hexagonArray[coordinate.getX()][coordinate.getY()].getLevel() == 0){
           for(Hexagon neighbor : getNeighbors(coordinate)){
               if(neighbor.getLevel() != 0){
                   found_attach_point = true;
               }
           }
        }
        if(hexagonArray[volcanoNeighbor1Coordinate.getX()][volcanoNeighbor1Coordinate.getY()].getLevel() == 0){
            for(Hexagon neighbor : getNeighbors(volcanoNeighbor1Coordinate)){
                if(neighbor.getLevel() != 0){
                    found_attach_point = true;
                }
            }
        }
        if(hexagonArray[volcanoNeighbor2Coordinate.getX()][volcanoNeighbor2Coordinate.getY()].getLevel() == 0){
            for(Hexagon neighbor : getNeighbors(volcanoNeighbor2Coordinate)){
                if(neighbor.getLevel() != 0){
                    found_attach_point = true;
                }
            }
        }
        return found_attach_point;
    }

    private boolean isHexagonGreaterThanLevel0AndAdjacentToEqualLevel(Coordinate coordinate){
        boolean found_attach_point = false;
        if(hexagonArray[coordinate.getX()][coordinate.getY()].getLevel() > 0){
            for(Hexagon neighbor : getNeighbors(coordinate)){
                if(neighbor.getLevel() == hexagonArray[coordinate.getX()][coordinate.getY()].getLevel()){
                    found_attach_point = true;
                }
            }
        }
        return found_attach_point;
    }

    Hexagon[] getNeighbors(Coordinate coordinate){
        Hexagon[] neighbors = new Hexagon[6];

        int i = 0;

        for(DirectionsHex direction : DirectionsHex.values()) {
            neighbors[i] = getHexagonNeighbor(coordinate, direction);
            i++;
        }

        return neighbors;
    }

    private Hexagon getHexagonNeighbor(Coordinate coordinate, DirectionsHex direction){
        Coordinate neighborCoordinate = getHexagonNeighborCoordinate(coordinate, direction);
        return getHexagon(neighborCoordinate);
    }

    public Coordinate getHexagonNeighborCoordinate(Coordinate coordinate, DirectionsHex direction){
        switch(direction){

            case LEFT:
                return new Coordinate(coordinate.getX()-1, coordinate.getY());
            case RIGHT:
               return new Coordinate(coordinate.getX()+1, coordinate.getY());
            case UPPERLEFT:
                return new Coordinate(coordinate.getX()-1 + offset(coordinate.getY()), coordinate.getY()+1);
            case UPPERRIGHT:
                return new Coordinate(coordinate.getX() + offset(coordinate.getY()), coordinate.getY()+1);
            case LOWERLEFT:
                return new Coordinate(coordinate.getX()-1 + offset(coordinate.getY()), coordinate.getY()-1);
            case LOWERRIGHT:
                return new Coordinate(coordinate.getX() + offset(coordinate.getY()), coordinate.getY()-1);
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
