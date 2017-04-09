package TigerIsland;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    // Members
    private Hexagon[][] hexagonArray;
    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    // Getters
    public Hexagon[][] getHexagonArray(){
        return hexagonArray;
    }
    public int getMinXRange(){
        return minX - 2;
    }
    public int getMaxXRange(){
        return maxX + 3;
    }
    public int getMinYRange(){
        return minY - 2;
    }
    public int getMaxYRange(){
        return maxY + 3;
    }

    public static Board cloneBoard(Board board) {
        return new Board(board);
    }

    public Hexagon getHexagonAt(Coordinate coordinate){
        Hexagon hexagon = hexagonArray[coordinate.getX()][coordinate.getY()];
        if(hexagon == null) {
            Hexagon newHexagon = new Hexagon();
            hexagonArray[coordinate.getX()][coordinate.getY()] = newHexagon;
            return newHexagon;
        }
        return hexagon;
    }
    public Hexagon getNeighboringHexagon(Coordinate coordinate, HexagonNeighborDirection direction) {
        Coordinate neighborCoordinate = coordinate.getNeighboringCoordinateAt(direction);
        return getHexagonAt(neighborCoordinate);
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
    public void setHexagonAt(Coordinate coordinate, Hexagon hexagon){
        hexagonArray[coordinate.getX()][coordinate.getY()] = hexagon;

        updateCoordinateRange(coordinate);
    }

    private void updateCoordinateRange(Coordinate coordinate){
        if(minX > coordinate.getX()) {
            minX = coordinate.getX();
        }

        if (maxX < coordinate.getX()) {
            maxX = coordinate.getX();
        }

        if(minY > coordinate.getY()) {
            minY = coordinate.getY();
        }

        if (maxY < coordinate.getY()) {
            maxY = coordinate.getY();
        }
    }

    // Constructors
    // Coordinate (100, 100) is the center of the board.
    public Board(){
        initializeHexagonArray();
        minX = 98;
        maxX = 102;
        minY = 98;
        maxY = 102;
    }

    private Board(Board originalBoard) {
        Hexagon oldHexagonArray[][] = originalBoard.getHexagonArray();
        hexagonArray = new Hexagon[200][200];

        minX = originalBoard.minX;
        maxX = originalBoard.maxX;
        minY = originalBoard.minY;
        maxY = originalBoard.maxY;

        for(int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                Hexagon oldHexagon = oldHexagonArray[i][j];
                Hexagon newHexagon = Hexagon.cloneHexagon(oldHexagon);
                hexagonArray[i][j] = newHexagon;
            }
        }
    }

    // Methods
    private void initializeHexagonArray() {
        hexagonArray = new Hexagon[200][200];
    }

    public boolean isEmpty() {
        Hexagon startingHexagon = this.getHexagonAt(new Coordinate(100,100));
        if (startingHexagon.getLevel() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void placeStartingTile() {
        Coordinate centerCoordinate = new Coordinate(100,100);

        Hexagon volcanoHex = this.getHexagonAt(centerCoordinate);
        Hexagon upperLeft = this.getNeighboringHexagon(centerCoordinate, HexagonNeighborDirection.UPPERLEFT);
        Hexagon upperRight = this.getNeighboringHexagon(centerCoordinate, HexagonNeighborDirection.UPPERRIGHT);
        Hexagon lowerRight = this.getNeighboringHexagon(centerCoordinate, HexagonNeighborDirection.LOWERRIGHT);
        Hexagon lowerLeft = this.getNeighboringHexagon(centerCoordinate, HexagonNeighborDirection.LOWERLEFT);

        volcanoHex.changeTerrainTypeThoughExplosion(Terrain.VOLCANO);
        upperLeft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        upperRight.changeTerrainTypeThoughExplosion(Terrain.LAKE);
        lowerRight.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        lowerLeft.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        int startingTileHashCode = 0;

        volcanoHex.setTileHashCode(startingTileHashCode);
        upperLeft.setTileHashCode(startingTileHashCode);
        upperRight.setTileHashCode(startingTileHashCode);
        lowerRight.setTileHashCode(startingTileHashCode);
        lowerLeft.setTileHashCode(startingTileHashCode);
    }

    public boolean placeTile(TileMove tileMove) {
        TileMoveChecker tileMoveChecker = new TileMoveChecker();
        boolean validTilePlacement = tileMoveChecker.checkForValidity(tileMove, this);
        if (validTilePlacement) {
            Tile tile = tileMove.getTile();
            HexagonNeighborDirection direction = tileMove.getDirection();
            Coordinate coordinate = tileMove.getCoordinate();

            hexagonArray[coordinate.getX()][coordinate.getY()].changeTerrainTypeThoughExplosion(Terrain.VOLCANO);

            updateCoordinateRange(coordinate);
            updateCoordinateRange(coordinate.getNeighboringCoordinateAt(direction));
            updateCoordinateRange(coordinate.getNeighboringCoordinateAt(direction.getNextClockwise()));

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

    public Settlement getSettlement(Coordinate coordinate) {
        Queue<Coordinate> settlementQueue = new LinkedList<>();
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Coordinate currentCoordinate = coordinate;
        Hexagon currentHexagon = getHexagonAt(currentCoordinate);
        if (currentHexagon.containsPieces()) {
            Color playerColor = currentHexagon.getOccupationColor();
            if (currentHexagon.getOccupationColor() == playerColor) {
                coordinateQueue.add(currentCoordinate);
            }
            while (!coordinateQueue.isEmpty()) {
                currentCoordinate = coordinateQueue.remove();
                currentHexagon = getHexagonAt(currentCoordinate);
                map.put(currentCoordinate,true);
                settlementQueue.add(currentCoordinate);
                Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
                for (Coordinate neighbor : neighbors) {
                    currentHexagon = getHexagonAt(neighbor);
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