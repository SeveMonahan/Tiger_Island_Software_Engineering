package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class TotoroTest {
    @Test
    public void shouldInitializeToThreeTotoro() {
        Player player = new Player(Color.WHITE);
        assertEquals(3, player.getTotoroCount());
    }

    @Test
    public void totoroShouldStopTilePlacement() {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        assertEquals(true, board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101))));

        Player player = new Player(Color.WHITE);
        TotoroConstructionMove newTotoro = new TotoroConstructionMove(new Coordinate(100, 100));
        assertEquals(new Coordinate(100,100), newTotoro.getCoordinate());
        Hexagon hexagon = board.getHexagonAt(new Coordinate(100, 100));
        hexagon.setOccupationStatus(Color.WHITE, PieceStatusHexagon.TOTORO);

        boolean isValidMove = board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));

        assertEquals(false, isValidMove);
    }

    @Test
    public void placeTotoroOnVolcano() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        Coordinate coordinate = new Coordinate(101,100).getNeighboringCoordinateAt(HexagonNeighborDirection.LEFT);
        Player player = new Player(Color.WHITE);

        player.placeTotoroOnHexagon(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(3, player.getTotoroCount());
        Hexagon hexagon = board.getHexagonAt(coordinate);
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void placeTotoroFailsDueToNoAdjacentSettlements() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Player player = new Player(Color.WHITE);

        Coordinate coordinate = new Coordinate(101,100);
        player.placeTotoroOnHexagon(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(3, player.getTotoroCount());
        Hexagon hexagon = board.getHexagonAt(coordinate);
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    private Board getBasicBoardWithHexagonAroundStartWithWhiteMeeples(){
        Board board = new Board();
        board.placeStartingTile();
        board.placeTile(new TileMove(new Tile (Terrain.JUNGLE, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate (98,100)));
        board.placeTile(new TileMove(new Tile (Terrain.JUNGLE, Terrain.JUNGLE), HexagonNeighborDirection.LEFT, new Coordinate (102,100)));

        Player player = new Player(Color.WHITE);

        for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()) {
            assertEquals(true, player.placeMeepleOnHexagon(new Coordinate(100, 100).getNeighboringCoordinateAt(direction), board));
        }

        return board;
    }

    @Test
    public void boardTest() throws Exception{
        Board board = getBasicBoardWithHexagonAroundStartWithWhiteMeeples();
        Settlement settlement = board.getSettlement(new Coordinate(100, 101) );

        assertEquals(false, settlement.getSettlementContainsTotoro(board));
        assertEquals(6, settlement.getSettlementSize());
    }

    @Test
    public void placeTotoroSuccess() throws Exception {
        Player player = new Player(Color.WHITE);

        Board board = getBasicBoardWithHexagonAroundStartWithWhiteMeeples();

        boolean result = player.placeTotoroOnHexagon(new Coordinate(98, 99), board);

        assertEquals(true, result);
    }

}
