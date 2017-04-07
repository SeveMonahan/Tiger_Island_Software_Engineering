package TigerIsland;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private Hexagon[][] hexagonArray;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    public int getMinXRange(){
        return minX - 2;
    }

    public int getMaxXRange(){
        return maxX + 2;
    }

    public int getMinYRange(){
        return minY - 2;
    }

    public int getMaxYRange(){
        return maxY + 2;
    }

    public static Board cloneBoard(Board board) {
        return new Board(board);
    }

    // Getters
    public Hexagon getHexagon(Coordinate coordinate){

        Hexagon hex = hexagonArray[coordinate.getX()][coordinate.getY()];

        if(hex == null){
            Hexagon new_hex = new Hexagon();
            hexagonArray[coordinate.getX()][coordinate.getY()] = new_hex;
            return new_hex;
        }

        return hex;
    }

    public Hexagon getNeighboringHexagon(Coordinate coordinate, HexagonNeighborDirection direction) {
        Coordinate neighborCoordinate = coordinate.getNeighboringCoordinate(direction);
        return getHexagon(neighborCoordinate);
    }
    public Hexagon[] getNeighboringHexagons(Coordinate coordinate) {
        int i = 0;
        Hexagon[] neighbors = new Hexagon[6];
        for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()) {
            neighbors[i] = getNeighboringHexagon(coordinate, direction);
            i++;
        }
        return neighbors;
    }

    // Setters
    public void setHexagon(Coordinate coordinate, Hexagon hexagon){
        hexagonArray[coordinate.getX()][coordinate.getY()] = hexagon;

        setMaxRange(coordinate);
    }

    private void setMaxRange(Coordinate coordinate){
        if(minX > coordinate.getX()){
            minX = coordinate.getX();
        }

        if (maxX < coordinate.getX()) {
            maxX = coordinate.getX();
        }

        if(minY > coordinate.getY()){
            minY = coordinate.getY();
        }

        if (maxY < coordinate.getY()) {
            maxY = coordinate.getY();
        }
    }
    // Constructors
    public Board(){
        initializeHexagonArray();
        minX = 98;
        maxX = 102;
        minY = 98;
        maxY = 102;

    }
    // Coordinate (100, 100) is the center of the board.

    Hexagon[][] getHexagonArray(){
        return hexagonArray;
    }
    private Board(Board original){
        Hexagon OldhexagonArray[][] = original.getHexagonArray();
        hexagonArray = new Hexagon[200][200];

        minX = original.minX;
        maxX = original.maxX;
        minY = original.minY;
        maxY = original.maxY;

        for(int i = minX; i < maxX; i++) {
            for (int j = minY; j < maxY; j++) {
                Hexagon OldHexagon = OldhexagonArray[i][j];
                Hexagon NewHexagon = Hexagon.cloneHexagon(OldHexagon);
                hexagonArray[i][j] = NewHexagon;
            }
        }

    }

    // Methods
    private void initializeHexagonArray() {
        hexagonArray = new Hexagon[200][200];
    }

    public void placeStartingTile() {
        Coordinate coordCenter = new Coordinate(100,100);

        Hexagon volcanoHex = this.getHexagon(coordCenter);
        Hexagon upperLeft = this.getNeighboringHexagon(coordCenter, HexagonNeighborDirection.UPPERLEFT);
        Hexagon upperRight = this.getNeighboringHexagon(coordCenter, HexagonNeighborDirection.UPPERRIGHT);
        Hexagon lowerRight = this.getNeighboringHexagon(coordCenter, HexagonNeighborDirection.LOWERRIGHT);
        Hexagon lowerLeft = this.getNeighboringHexagon(coordCenter, HexagonNeighborDirection.LOWERLEFT);

        volcanoHex.changeTerrainTypeThoughExplosion(Terrain.VOLCANO);
        upperLeft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        upperRight.changeTerrainTypeThoughExplosion(Terrain.LAKE);
        lowerRight.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        lowerLeft.changeTerrainTypeThoughExplosion(Terrain.ROCK);
    }

    public boolean placeTile(TileMove tileMove) {
        TileMoveChecker tileMoveChecker = new TileMoveChecker();
        boolean validTilePlacement = tileMoveChecker.checkForValidity(tileMove, this);
        if (validTilePlacement) {
            Tile tile = tileMove.getTile();
            HexagonNeighborDirection direction = tileMove.getDirection();
            Coordinate coordinate = tileMove.getCoordinate();

            hexagonArray[coordinate.getX()][coordinate.getY()].changeTerrainTypeThoughExplosion(Terrain.VOLCANO);

            Hexagon overwritten_2 = getNeighboringHexagon(coordinate, direction);
            Hexagon overwritten_3 = getNeighboringHexagon(coordinate, direction.getNextClockwise());

            overwritten_2.changeTerrainTypeThoughExplosion(tile.getTerrainsClockwiseFromVolcano()[1]);
            overwritten_3.changeTerrainTypeThoughExplosion(tile.getTerrainsClockwiseFromVolcano()[2]);

            // Setting the instance variables to be part of the same tile.
            overwritten_2.setTileHashCode(tile.hashCode());
            overwritten_3.setTileHashCode(tile.hashCode());
            hexagonArray[coordinate.getX()][coordinate.getY()].setTileHashCode(tile.hashCode());
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isEmpty() {
        Hexagon startingHexagon = this.getHexagon(new Coordinate(100,100));
        if (startingHexagon.getLevel() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public Settlement getSettlement(Coordinate coordinate) {
        Queue<Coordinate> settlementQueue = new LinkedList<>();
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Coordinate currentCoordinate = coordinate;
        Hexagon currentHexagon = getHexagon(currentCoordinate);
        if (currentHexagon.containsPieces()) {
            Color playerColor = currentHexagon.getOccupationColor();
            if (currentHexagon.getOccupationColor() == playerColor) {
                coordinateQueue.add(currentCoordinate);
            }
            while (!coordinateQueue.isEmpty()) {
                currentCoordinate = coordinateQueue.remove();
                currentHexagon = getHexagon(currentCoordinate);
                map.put(currentCoordinate,true);
                settlementQueue.add(currentCoordinate);
                Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
                for (Coordinate neighbor : neighbors) {
                    currentHexagon = getHexagon(neighbor);
                    if (!map.containsKey(neighbor)) {
                        map.put(neighbor, true);
                        if (currentHexagon.containsPieces()) {
                            if(currentHexagon.getOccupationColor() == playerColor) {
                                coordinateQueue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }

        Settlement settlement = new Settlement(settlementQueue);

        return settlement;
    }
}