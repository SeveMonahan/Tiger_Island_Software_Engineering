package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileMoveNukePiecesTest
{
    @Test
    public void entireSettlementShouldNotBeNukedSizeOne() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        board.placeTile(new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100)));

        Coordinate targetCoordinate = new Coordinate(99, 101);
        Hexagon targetHexagon = board.getHexagonAt(targetCoordinate);

        Player player = new Player(Color.WHITE);

        player.foundSettlement(targetCoordinate, board);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.ROCK);
        TileMove tileMove = new TileMove(tile2, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(99, 100));
        boolean isSuccess = board.placeTile(tileMove);

        assertEquals(false, isSuccess);
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagon.getPiecesStatus());
    }

    @Test
    public void entireSettlementShouldNotBeNukedSizeTwo() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        board.placeTile(new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100)));

        Coordinate targetCoordinateOne = new Coordinate(99, 101);
        Coordinate targetCoordinateTwo = new Coordinate(98,101);
        Hexagon targetHexagonOne = board.getHexagonAt(targetCoordinateOne);
        Hexagon targetHexagonTwo = board.getHexagonAt(targetCoordinateTwo);

        Player player = new Player(Color.WHITE);

        player.foundSettlement(targetCoordinateOne, board);
        player.foundSettlement(targetCoordinateTwo, board);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.ROCK);
        TileMove tileMove = new TileMove(tile2, HexagonNeighborDirection.UPPERLEFT, new Coordinate(99, 100));
        boolean isSuccess = board.placeTile(tileMove);

        assertEquals(false, isSuccess);
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagonOne.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagonTwo.getPiecesStatus());
    }

    @Test
    public void partOfSettlementShouldBeNukedSizeOne() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        board.placeTile(new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100)));

        Coordinate targetCoordinateOne = new Coordinate(99, 101);
        Coordinate targetCoordinateTwo = new Coordinate(100,101);
        Hexagon targetHexagonOne = board.getHexagonAt(targetCoordinateOne);
        Hexagon targetHexagonTwo = board.getHexagonAt(targetCoordinateTwo);

        Player player = new Player(Color.WHITE);

        player.foundSettlement(targetCoordinateOne, board);
        player.foundSettlement(targetCoordinateTwo, board);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.ROCK);
        TileMove tileMove = new TileMove(tile2, HexagonNeighborDirection.UPPERLEFT, new Coordinate(99, 100));
        boolean isSuccess = board.placeTile(tileMove);

        assertEquals(true, isSuccess);
        assertEquals(PieceStatusHexagon.EMPTY, targetHexagonOne.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagonTwo.getPiecesStatus());
    }

    @Test
    public void partOfSettlementShouldBeNukedSizeTwo() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        board.placeTile(new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100)));

        Coordinate targetCoordinateOne = new Coordinate(98, 101);
        Coordinate targetCoordinateTwo = new Coordinate(99,101);
        Coordinate targetCoordinateThree = new Coordinate(100,101);
        Hexagon targetHexagonOne = board.getHexagonAt(targetCoordinateOne);
        Hexagon targetHexagonTwo = board.getHexagonAt(targetCoordinateTwo);
        Hexagon targetHexagonThree = board.getHexagonAt(targetCoordinateThree);

        Player player = new Player(Color.WHITE);

        player.foundSettlement(targetCoordinateOne, board);
        player.foundSettlement(targetCoordinateTwo, board);
        player.foundSettlement(targetCoordinateThree, board);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.ROCK);
        TileMove tileMove = new TileMove(tile2, HexagonNeighborDirection.UPPERLEFT, new Coordinate(99, 100));
        boolean isSuccess = board.placeTile(tileMove);

        assertEquals(true, isSuccess);
        assertEquals(PieceStatusHexagon.EMPTY, targetHexagonOne.getPiecesStatus());
        assertEquals(PieceStatusHexagon.EMPTY, targetHexagonTwo.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagonThree.getPiecesStatus());
    }
}