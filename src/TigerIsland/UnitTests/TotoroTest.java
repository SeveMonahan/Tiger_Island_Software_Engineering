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
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASSLAND), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        assertEquals(true, board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101))));

        Player player = new Player(Color.WHITE);
        Piece newTotoro = new Totoro(Color.WHITE);

        Hexagon hexagon = board.getHexagon(new Coordinate(101, 100));
        hexagon.setOccupationStatus(newTotoro);

        boolean isValidMove = board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));

        assertEquals(false, isValidMove);
    }

    @Test
    public void placeTotoroOnVolcano() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASSLAND), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        Coordinate coordinate = new Coordinate(101,100).getNeighboringCoordinate(HexagonNeighborDirection.LEFT);
        Player player = new Player(Color.WHITE);

        player.placeTotoroOnHexagon(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(3, player.getTotoroCount());
        Hexagon hexagon = board.getHexagon(coordinate);
        assertEquals(HexagonOccupationStatus.EMPTY, hexagon.getOccupationStatus());
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
        Hexagon hexagon = board.getHexagon(coordinate);
        assertEquals(HexagonOccupationStatus.EMPTY, hexagon.getOccupationStatus());
    }

    private Board getBasicBoardWithHexagonAroundStartWithWhiteMeeples(){
        Board board = new Board();
        board.placeStartingTile();
        board.placeTile(new TileMove(new Tile (Terrain.JUNGLE, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate (98,100)));
        board.placeTile(new TileMove(new Tile (Terrain.JUNGLE, Terrain.JUNGLE), HexagonNeighborDirection.LEFT, new Coordinate (102,100)));

        Player player = new Player(Color.WHITE);

        for(HexagonNeighborDirection direction : HexagonNeighborDirection.values()) {
            assertEquals(true, player.placeMeepleOnHexagon(new Coordinate(100, 100).getNeighboringCoordinate(direction), board));
        }

        return board;
    }

    @Test
    public void boardTest() throws Exception{
        Board board = getBasicBoardWithHexagonAroundStartWithWhiteMeeples();
        assertEquals(false, board.getSettlementContainsTotoro(new Coordinate(100, 101)));
        assertEquals(6, board.getSettlementSize(new Coordinate (100, 101)));
    }

    @Test
    public void placeTotoroSuccess() throws Exception {
        Player player = new Player(Color.WHITE);

        Board board = getBasicBoardWithHexagonAroundStartWithWhiteMeeples();

        boolean result = player.placeTotoroOnHexagon(new Coordinate(98, 99), board);

        assertEquals(true, result);
    }

}
