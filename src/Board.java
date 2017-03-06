class Board {
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
            return -1;
        }
    }

    Hexagon[] getNeighbors(int x, int y){
        Hexagon[] neighbors = new Hexagon[6];

        neighbors[0] = hexagonArray[x+1][y];
        neighbors[1] = hexagonArray[x-1][y];
        neighbors[2] = hexagonArray[x][y+1];
        neighbors[3] = hexagonArray[x][y-1];

        neighbors[4] = hexagonArray[x+offset(x)][y+1];
        neighbors[5] = hexagonArray[x+offset(x)][y-1];

        return neighbors;
    }
}
