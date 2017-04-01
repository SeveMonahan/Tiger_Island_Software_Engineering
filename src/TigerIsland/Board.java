package TigerIsland;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private Hexagon[][] hexagonArray;

    // Getters
    public Hexagon getHexagon(Coordinate coordinate){
        return hexagonArray[coordinate.getX()][coordinate.getY()];
    }
    public Coordinate getNeighboringCoordinate(Coordinate coordinate, HexagonNeighborDirection direction) {
        int x = coordinate.getX();
        int y = coordinate.getY();
        switch(direction){
            case LEFT:
                return new Coordinate(x - 1, y);
            case RIGHT:
                return new Coordinate(x + 1, y);
            case UPPERLEFT:
                return new Coordinate(x - 1 + coordinate.offset(y), y + 1);
            case UPPERRIGHT:
                return new Coordinate(x + coordinate.offset(y), y + 1);
            case LOWERLEFT:
                return new Coordinate(x - 1 + coordinate.offset(y), y - 1);
            case LOWERRIGHT:
                return new Coordinate(x + coordinate.offset(y), y - 1);
            default:
                return new Coordinate(0, 0);
        }
    }
    public Hexagon getNeighboringHexagon(Coordinate coordinate, HexagonNeighborDirection direction) {
        Coordinate neighborCoordinate = getNeighboringCoordinate(coordinate, direction);
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
    }

    // Constructors
    public Board(){
        initializeHexagonArray();
    }
    // Coordinate (100, 100) is the center of the board.
    // Note: you must use this initializer if you want to use placeTile() to place more tiles.
    public Board(Tile startingTile){
        initializeHexagonArray();
        TileMove startingTileMove = new TileMove(startingTile, HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        placeTileNoRestrictions(startingTileMove);
    }

    // Methods
    private void initializeHexagonArray() {
        hexagonArray = new Hexagon[200][200];
        for(int i = 0; i < 200; i++) {
            for(int j = 0; j < 200; j++) {
                hexagonArray[i][j] = new Hexagon();
            }
        }
    }

    public boolean placeTile(TileMove tileMove) {
        TileMoveChecker tileMoveChecker = new TileMoveChecker();
        boolean validTilePlacement = tileMoveChecker.checkForValidity(tileMove, this);
        if (validTilePlacement) {
            placeTileNoRestrictions(tileMove);
            return true;
        }
        else {
            return false;
        }
    }
    public void placeTileNoRestrictions(TileMove tileMove) {
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
    }

    public int getSettlementSize(Coordinate sourceCoordinate) {
        int size = 0;
        HashMap map = new HashMap();
        Queue<Hexagon> hexagonQueue = new LinkedList<>();

        Coordinate currentCoordinate = sourceCoordinate;
        Hexagon currentHexagon = getHexagon(currentCoordinate);

        if (currentHexagon.isOccupied()) {
            Color playerColor = currentHexagon.getOccupationColor();
            if (currentHexagon.getOccupationColor() == playerColor) {
                hexagonQueue.add(currentHexagon);
            }
            while (!hexagonQueue.isEmpty()) {
                currentHexagon = hexagonQueue.remove();
                map.put(currentHexagon.hashCode(),true);
                size++;
                Hexagon[] neighbors = getNeighboringHexagons(sourceCoordinate);
                for (Hexagon neighbor : neighbors) {
                    currentHexagon = neighbor;
                    if (!map.containsKey(currentHexagon.hashCode())) {
                        map.put(currentHexagon.hashCode(), true);
                        if (currentHexagon.isOccupied()) {
                            if(currentHexagon.getOccupationColor() == playerColor) {
                                hexagonQueue.add(currentHexagon);
                            }
                        }
                    }
                }
            }
        }
        return size;
    }

    public boolean expandSettlementCheck(Player player, Coordinate coordinate, Terrain terrain) {
        Queue<Coordinate> settlement = expandSettlementFloodFill(coordinate, terrain);
        if(settlement.size() <= player.getMeeplesCount()) {
            return true;
        }
        return false;
    }

    public Queue<Coordinate> expandSettlementFloodFill(Coordinate coordinate, Terrain terrain) {
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Queue<Coordinate> expansion = new LinkedList<>();

        Coordinate currentCoordinate = coordinate;
        coordinateQueue.add(coordinate);

        while (!coordinateQueue.isEmpty()) {
            currentCoordinate = coordinateQueue.remove();
            map.put(currentCoordinate.hashCode(), true);

            for (HexagonNeighborDirection direction : HexagonNeighborDirection.values()) {
                currentCoordinate = getNeighboringCoordinate(coordinate, direction);
                if(!map.containsKey(currentCoordinate.hashCode())) {
                    map.put(currentCoordinate.hashCode(), true);
                    Hexagon hexagon = this.getHexagon(currentCoordinate);
                    if(hexagon.isEmpty() && hexagon.getTerrain() == terrain) {
                        expansion.add(currentCoordinate);
                    }
                }
            }
        }
        return expansion;
    }
}