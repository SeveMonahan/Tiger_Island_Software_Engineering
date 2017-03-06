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
        /* TODO: Make this work */
    }

    private Hexagon getHexagonNeighbor(int x, int y, DirectionsHex direction){
        switch(direction){
            case LEFT:
                return hexagonArray[x-1][y];
            case RIGHT:
                return hexagonArray[x+1][y];
            case UPPERLEFT:
                return hexagonArray[x-1 + offset(x)][y+1];
            case UPPERRIGHT:
                return hexagonArray[x + offset(x)][y+1];
            case LOWERLEFT:
                return hexagonArray[x-1 + offset(x)][y-1];
            case LOWERRIGHT:
                return hexagonArray[x + offset(x)][y-1];
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
}
