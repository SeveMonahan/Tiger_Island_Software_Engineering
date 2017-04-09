package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MeeplesTest {
    @Test
    public void shouldInitializeToTwentyMeeples() throws Exception {
        Player player = new Player(Color.WHITE);
        assertEquals(20, player.getMeeplesCount());
    }

    @Test
    public void placeMeepleOnLevelZeroShouldFail() throws Exception {
        Board board = new Board();
        Coordinate coordinate = new Coordinate(101, 100);
        Player player = new Player(Color.WHITE);

        player.foundSettlement(coordinate, board);

        Hexagon TestHexagon = board.getHexagonAt(coordinate);

        assertEquals(PieceStatusHexagon.EMPTY, TestHexagon.getPiecesStatus());

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
    }

    @Test
    public void foundSettlement() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate coordinate = new Coordinate(99,101);
        Hexagon hexagon = board.getHexagonAt(coordinate);
        Player player = new Player(Color.WHITE);

        player.foundSettlement(coordinate, board);

        assertEquals(1, player.getScore());
        assertEquals(19, player.getMeeplesCount());
        assertEquals(PieceStatusHexagon.MEEPLE, hexagon.getPiecesStatus());
    }

    @Test
    public void foundSettlementShouldFailDueToVolcano() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate coordinate = new Coordinate(100,100);
        Hexagon hexagon = board.getHexagonAt(coordinate);
        Player player = new Player(Color.WHITE);

        player.foundSettlement(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void foundSettlementFailsDueToLevelHeight() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        Tile tile2 = new Tile(Terrain.ROCK, Terrain.ROCK);

        board.placeTile(new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100)));
        board.placeTile(new TileMove(tile2, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(99, 100)));

        Coordinate Level2Coordinate = new Coordinate(99, 101);

        Hexagon hexagon = board.getHexagonAt(Level2Coordinate);

        Player player = new Player(Color.WHITE);

        player.foundSettlement(Level2Coordinate, board);

        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void foundSettlementFailsBecauseHexagonIsAlreadyOccupiedDifferentColor() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate targetCoordinate = new Coordinate (99, 101);
        Hexagon targetHexagon = board.getHexagonAt(targetCoordinate);

        Player playerOne = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);

        boolean isSuccess = playerOne.foundSettlement(targetCoordinate, board);

        assertEquals(true, isSuccess);
        assertEquals(19, playerOne.getMeeplesCount());
        assertEquals(1, playerOne.getScore());
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagon.getPiecesStatus());
        assertEquals(Color.WHITE, targetHexagon.getOccupationColor());

        isSuccess = playerTwo.foundSettlement(targetCoordinate, board);

        assertEquals(false, isSuccess);
        assertEquals(20, playerTwo.getMeeplesCount());
        assertEquals(0, playerTwo.getScore());
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagon.getPiecesStatus());
        assertEquals(Color.WHITE, targetHexagon.getOccupationColor());
    }

    @Test
    public void foundSettlementFailsBecauseHexagonIsAlreadyOccupiedSameColor() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate targetCoordinate = new Coordinate (99, 101);
        Hexagon targetHexagon = board.getHexagonAt(targetCoordinate);

        Player playerOne = new Player(Color.WHITE);

        boolean isSuccess = playerOne.foundSettlement(targetCoordinate, board);

        assertEquals(true, isSuccess);
        assertEquals(19, playerOne.getMeeplesCount());
        assertEquals(1, playerOne.getScore());
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagon.getPiecesStatus());
        assertEquals(Color.WHITE, targetHexagon.getOccupationColor());

        isSuccess = playerOne.foundSettlement(targetCoordinate, board);

        assertEquals(false, isSuccess);
        assertEquals(19, playerOne.getMeeplesCount());
        assertEquals(1, playerOne.getScore());
    }
}
