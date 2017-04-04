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
    }

    // Constructors
    public Board(){
        initializeHexagonArray();
    }
    // Coordinate (100, 100) is the center of the board.
    // Note: you must use this initializer if you want to use placeTile() to place more tiles.

    public Board(Board board){
        Hexagon [][] OldHexagonArray = board.getHexagonArray();

        hexagonArray = new Hexagon[200][200];

        for(int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                Hexagon OldHexagon = OldHexagonArray[i][j];
                Hexagon NewHexagon = new Hexagon(OldHexagon);
                hexagonArray[i][j] = NewHexagon;
            }
        }
    }

    private Hexagon[][] getHexagonArray(){
            return hexagonArray;
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
        lowerRight.changeTerrainTypeThoughExplosion(Terrain.GRASSLAND);
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

    public boolean expandSettlementWithCheck(Player player, Coordinate coordinate, Terrain terrain) {
        Queue<Coordinate> settlement = expandSettlementFloodFill(coordinate, terrain);
        if(settlement.size() <= player.getMeeplesCount()) {
            performFloodFill(player, settlement );
            return true;
        }
        return false;
    }

    private void performFloodFill(Player player, Queue<Coordinate> expansion ) {
        while( !expansion.isEmpty() ) {
            player.placeMeepleOnHexagon(expansion.remove(), this);
        }
    }

    private Queue<Coordinate> expandSettlementFloodFill(Coordinate coordinate, Terrain terrain) {
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Queue<Coordinate> expansion = new LinkedList<>();

        Coordinate currentCoordinate = coordinate;
        coordinateQueue.add(coordinate);

        while (!coordinateQueue.isEmpty()) {
            currentCoordinate = coordinateQueue.remove();
            map.put(currentCoordinate.hashCode(), true);

            for (HexagonNeighborDirection direction : HexagonNeighborDirection.values()) {
                currentCoordinate = coordinate.getNeighboringCoordinate(direction);
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