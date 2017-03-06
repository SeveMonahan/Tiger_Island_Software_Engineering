class Board {
    private Hexagon[][] hexagonArray;

    Board() {
        hexagonArray = new Hexagon[200][200];
        for(int i = 0; i < 200; i++) {
            for(int j = 0; j < 200; j++) {
                hexagonArray[i][j] = new Hexagon();
            }
        }
    }

    Hexagon getHexagonUsingPrivateIndexingForTest(int x, int y){
        return hexagonArray[x][y];
    }

    void setHexagonUsingPrivateIndexingForTest(int x, int y, Hexagon hex){
        hexagonArray[x][y] = hex;
    }

    Hexagon[] getNeighbors(int x, int y){
        Hexagon[] neighbors = new Hexagon[6];

        neighbors[0] = hexagonArray[x+1][y];
        neighbors[1] = hexagonArray[x-1][y];
        neighbors[2] = hexagonArray[x][y+1];
        neighbors[3] = hexagonArray[x][y-1];

        int offset;

        if(x % 2 == 1) {
            offset = 1;
        }else{
            offset = -1;
        }

        neighbors[4] = hexagonArray[x+offset][y+1];
        neighbors[5] = hexagonArray[x+offset][y-1];

        return neighbors;
    }
}
