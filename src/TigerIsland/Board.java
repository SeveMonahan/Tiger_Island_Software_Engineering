package TigerIsland;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    private Hexagon[][] hexagonArray;

    public static Board cloneBoard(Board board) {
        return new Board(board);
    }

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

    private Board(Board board){
        Hexagon [][] OldHexagonArray = board.getHexagonArray();

        hexagonArray = new Hexagon[200][200];

        for(int i = 0; i < 200; i++) {
            for (int j = 0; j < 200; j++) {
                Hexagon OldHexagon = OldHexagonArray[i][j];
                Hexagon NewHexagon = Hexagon.cloneHexagon(OldHexagon);
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

    public int getSettlementSize(Coordinate sourceCoordinate) {
        int size = 0;
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Coordinate currentCoordinate = sourceCoordinate;
        Hexagon currentHexagon = getHexagon(currentCoordinate);
        if (currentHexagon.isOccupied()) {
            Color playerColor = currentHexagon.getOccupationColor();
            if (currentHexagon.getOccupationColor() == playerColor) {
                coordinateQueue.add(currentCoordinate);
            }
            while (!coordinateQueue.isEmpty()) {
                currentCoordinate = coordinateQueue.remove();
                currentHexagon = getHexagon(currentCoordinate);
                map.put(currentCoordinate,true);
                size++;
                Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
                for (Coordinate neighbor : neighbors) {
                    currentHexagon = getHexagon(neighbor);
                    if (!map.containsKey(neighbor)) {
                        map.put(neighbor, true);
                        if (currentHexagon.isOccupied()) {
                            if(currentHexagon.getOccupationColor() == playerColor) {
                                coordinateQueue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        return size;
    }



    public boolean getSettlementContainsTotoro(Coordinate sourceCoordinate) {
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Coordinate currentCoordinate = sourceCoordinate;
        Hexagon currentHexagon = getHexagon(currentCoordinate);
        if (currentHexagon.isOccupied()) {
            Color playerColor = currentHexagon.getOccupationColor();
            if (currentHexagon.getOccupationColor() == playerColor) {
                coordinateQueue.add(currentCoordinate);
            }
            while (!coordinateQueue.isEmpty()) {
                currentCoordinate = coordinateQueue.remove();
                currentHexagon = getHexagon(currentCoordinate);
                map.put(currentCoordinate,true);

                if(currentHexagon.getOccupationStatus() == HexagonOccupationStatus.TOTORO){
                    return true;
                }

                Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
                for (Coordinate neighbor : neighbors) {
                    currentHexagon = getHexagon(neighbor);
                    if (!map.containsKey(neighbor)) {
                        map.put(neighbor, true);
                        if (currentHexagon.isOccupied()) {
                            if(currentHexagon.getOccupationColor() == playerColor) {
                                coordinateQueue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean getSettlementContainsTiger(Coordinate sourceCoordinate) {
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Coordinate currentCoordinate = sourceCoordinate;
        Hexagon currentHexagon = getHexagon(currentCoordinate);
        if (currentHexagon.isOccupied()) {
            Color playerColor = currentHexagon.getOccupationColor();
            if (currentHexagon.getOccupationColor() == playerColor) {
                coordinateQueue.add(currentCoordinate);
            }
            while (!coordinateQueue.isEmpty()) {
                currentCoordinate = coordinateQueue.remove();
                currentHexagon = getHexagon(currentCoordinate);
                map.put(currentCoordinate,true);

                if(currentHexagon.getOccupationStatus() == HexagonOccupationStatus.TIGER){
                    return true;
                }

                Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
                for (Coordinate neighbor : neighbors) {
                    currentHexagon = getHexagon(neighbor);
                    if (!map.containsKey(neighbor)) {
                        map.put(neighbor, true);
                        if (currentHexagon.isOccupied()) {
                            if(currentHexagon.getOccupationColor() == playerColor) {
                                coordinateQueue.add(neighbor);
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean expandSettlementWithCheck(Player player, Coordinate coordinate, Terrain terrain) {
        Color color = this.getHexagon(coordinate).getOccupationColor();
        HexagonOccupationStatus occupationStatus = this.getHexagon(coordinate).getOccupationStatus();
        if( player.getColor() == color
                && occupationStatus == HexagonOccupationStatus.MEEPLE){
            Queue<Coordinate> settlement = expandSettlementFloodFill(coordinate, player, terrain);
            if(settlement.size() <= player.getMeeplesCount()) {
                performFloodFill(player, settlement );
                return true;
            }
            return false;
        }
        return false;
    }

    private void performFloodFill(Player player, Queue<Coordinate> expansion ) {
        while( !expansion.isEmpty() ) {
            player.placeMeepleOnHexagon(expansion.remove(), this);
        }
    }

    private Queue<Coordinate> expandSettlementFloodFill(Coordinate coordinate, Player player, Terrain terrain) {
        HashMap<Coordinate, Boolean> searched = new HashMap<>();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Queue<Coordinate> expansion = new LinkedList<>();

        coordinateQueue.add(coordinate);
        searched.put(coordinate, true);

        while (!coordinateQueue.isEmpty()) {
            Coordinate currentCoordinate = coordinateQueue.remove();
            Coordinate[] neighbors = currentCoordinate.getNeighboringCoordinates();
            for(Coordinate neighbor : neighbors){
                Hexagon hexagon = this.getHexagon(neighbor);
                if( !searched.containsKey(neighbor) &&
                        hexagon.getTerrain() ==  terrain &&
                        hexagon.getOccupationStatus() == HexagonOccupationStatus.EMPTY){
                    // expand to this hexagon

                    coordinateQueue.add(neighbor);
                    expansion.add(neighbor);
                } else if( !searched.containsKey(neighbor) &&
                        hexagon.getOccupationStatus() ==  HexagonOccupationStatus.MEEPLE) {
                    coordinateQueue.add(neighbor);
                    // Unsure if we need the above here... need additional tests for this...
                }
                searched.put(neighbor, true);
            }
        }
        return expansion;
    }
}