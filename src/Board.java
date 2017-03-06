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
}
