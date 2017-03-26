package TigerIsland;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Board {
    // This contains enough spaces for every hexagon on the board.
    // The hexagons are stored in a square array, where each odd y
    // value is offshifted to the right in order to replicate the six neighbors
    // for hexagonal tiling.
    // Higher y value is going "up" while higher x value is going "right."
    private Hexagon[][] hexagonArray;

    public Hexagon getHexagon(Coordinate coordinate){
        return hexagonArray[coordinate.getX()][coordinate.getY()];
    }

    public void setHexagon(Coordinate coordinate, Hexagon hex){
        hexagonArray[coordinate.getX()][coordinate.getY()] = hex;
    }

    public Board(){
        initializeHexagonArray();
    }

    // The first tile placement is arbitary, so we just put it in the center of the board.
    // Note that you must use this initalizer if you want to use placeTile() to place any
    // more tiles.
    public Board(Tile first_tile){
        initializeHexagonArray();
        TileMove starting_tile_move = new TileMove(first_tile, HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        placeTileNoRestrictions(starting_tile_move);
    }

    private void initializeHexagonArray() {
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
    public void placeTileNoRestrictions(TileMove tilemove){

        Tile tile = tilemove.getTile();
        HexagonNeighborDirection direction = tilemove.getDirection();
        Coordinate coordinate = tilemove.getCoordinate();

        hexagonArray[coordinate.getX()][coordinate.getY()].changeTerrainTypeThoughExplosion(Terrain.VOLCANO);

        Hexagon overwritten_2 = getHexagonNeighbor(coordinate, direction);

        Hexagon overwritten_3 = getHexagonNeighbor(coordinate, direction.getNextClockwise());

        overwritten_2.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[1]);

        overwritten_3.changeTerrainTypeThoughExplosion(
                tile.getTerrainsClockwiseFromVolcano()[2]);

        // Setting the instance variables to all be the same
        overwritten_2.tileHashCode = tile.hashCode();
        overwritten_3.tileHashCode = tile.hashCode();
        hexagonArray[coordinate.getX()][coordinate.getY()].tileHashCode = tile.hashCode();

    }

    // place a tile, abiding by all game rules.
    public boolean placeTile(TileMove tileMove) {
        Hexagon volcanoHexagon = getHexagon(tileMove.getCoordinate());

        Hexagon overwritten_2 = getHexagonNeighbor(tileMove.getCoordinate(), tileMove.getDirection());

        Hexagon overwritten_3 = getHexagonNeighbor(tileMove.getCoordinate(), tileMove.getDirection().getNextClockwise());

        if (tileMove.isPlaceTileLegal(volcanoHexagon, overwritten_2, overwritten_3, isAdjacentToNonemptyBoard(tileMove))) {
            placeTileNoRestrictions(tileMove);
            return true;
        }

        return false;
    }

    public Hexagon[] getNeighbors(Coordinate coordinate){
        Hexagon[] neighbors = new Hexagon[6];

        int i = 0;

        for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()) {
            neighbors[i] = getHexagonNeighbor(coordinate, direction);
            i++;
        }

        return neighbors;
    }

    public Hexagon getHexagonNeighbor(Coordinate coordinate, HexagonNeighborDirection direction){
        Coordinate neighborCoordinate = coordinate.getHexagonNeighborCoordinate(direction);
        return getHexagon(neighborCoordinate);
    }

    private boolean isAdjacentToNonemptyBoard(TileMove tileMove) {
        Coordinate coordinate = tileMove.getCoordinate();
        HexagonNeighborDirection direction = tileMove.getDirection();

        Coordinate volcanoNeighbor1Coordinate = coordinate.getHexagonNeighborCoordinate(direction);
        Coordinate volcanoNeighbor2Coordinate = coordinate.getHexagonNeighborCoordinate(direction.getNextClockwise());

        boolean found_attach_point = false;

        for(Hexagon neighbor : getNeighbors(coordinate)){
            if(neighbor.getLevel() != 0){
                found_attach_point = true;
            }
        }
        for(Hexagon neighbor : getNeighbors(volcanoNeighbor1Coordinate)){
            if(neighbor.getLevel() != 0){
                found_attach_point = true;
            }
        }
        for(Hexagon neighbor : getNeighbors(volcanoNeighbor2Coordinate)){
            if(neighbor.getLevel() != 0){
                found_attach_point = true;
            }
        }

        return found_attach_point;
    }

    public int settlementSize(Coordinate coordinate, Color playerColor) {
        int size = 0;
        HashMap map = new HashMap();
        Queue<Hexagon> hexagonQueue = new LinkedList<>();
        Coordinate currentCoordinate = coordinate;
        Hexagon currentHexagon = this.getHexagon(currentCoordinate);
        if (currentHexagon.getOccupationStatus() != HexagonOccupationStatus.empty) {
            if (currentHexagon.getOccupationColor() == playerColor) {
                hexagonQueue.add(currentHexagon);
            }
            while(!hexagonQueue.isEmpty()) {
                currentHexagon = hexagonQueue.remove();
                map.put(currentHexagon.hashCode(),true);
                size++;
                for (HexagonNeighborDirection dir : HexagonNeighborDirection.values()) {
                    currentCoordinate = coordinate.getHexagonNeighborCoordinate(dir);
                    currentHexagon = this.getHexagon(currentCoordinate);
                    if (!map.containsKey(currentHexagon.hashCode())) {
                        map.put(currentHexagon.hashCode(), true);
                        if (currentHexagon.getOccupationStatus() != HexagonOccupationStatus.empty) {
                            if( currentHexagon.getOccupationColor() == playerColor) {
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

        }

        return false;
    }

    public Queue<Coordinate> expandSettlementFloodFill(Coordinate coordinate, Terrain terrain) {
        HashMap map = new HashMap();
        Queue<Coordinate> coordinateQueue = new LinkedList<>();
        Queue<Coordinate> expansion = new LinkedList<>();
        // Hexagon currentHexagon = this.getHexagon(currentCoordinate);
        Coordinate currentCoordinate = coordinate;

        coordinateQueue.add(coordinate);

        while (!coordinateQueue.isEmpty()) {
            currentCoordinate = coordinateQueue.remove();
            map.put(currentCoordinate.hashCode(), true);

            for (HexagonNeighborDirection dir : HexagonNeighborDirection.values()) {
                currentCoordinate = coordinate.getHexagonNeighborCoordinate(dir);
                // currentHexagon = this.getHexagon(currentCoordinate);
                // if (!map.containsKey(currentHexagon.hashCode())) {
                if( !map.containsKey(currentCoordinate.hashCode())) {
                    map.put(currentCoordinate.hashCode(), true);
                    // Conditions to flood fill
                    Hexagon hexagon = this.getHexagon(currentCoordinate);
                    if(hexagon.getOccupationStatus() == HexagonOccupationStatus.empty &&
                            hexagon.getTerrain() == terrain) {
                        expansion.add(currentCoordinate);
                    }
                }
            }
        }

        return expansion;
    }
}