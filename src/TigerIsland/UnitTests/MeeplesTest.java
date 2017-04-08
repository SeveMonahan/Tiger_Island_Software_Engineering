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
    public void placeMeepleOnLevelZero() throws Exception {
        Board board = new Board();

        Coordinate coordinate = new Coordinate(101, 100);

        Hexagon hexagon = board.getHexagonAt(coordinate);

        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void placeMeepleOnLevelOne() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        Coordinate coordinate = new Coordinate(100,100).getNeighboringCoordinateAt(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagonAt(coordinate);
        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(coordinate, board);

        assertEquals(1, player.getScore());
        assertEquals(19, player.getMeeplesCount());
        assertEquals(PieceStatusHexagon.MEEPLE, hexagon.getPiecesStatus());
    }

    @Test
    public void startMeepleSettlement() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        Coordinate coordinate = new Coordinate(100,100).getNeighboringCoordinateAt(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagonAt(coordinate);
        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(coordinate, board);

        assertEquals(1, player.getScore());
        assertEquals(19, player.getMeeplesCount());
        assertEquals(PieceStatusHexagon.MEEPLE, hexagon.getPiecesStatus());
    }

    @Test
    public void startMeepleSettlementOnVolcano() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        Coordinate coordinate = new Coordinate(101,100).getNeighboringCoordinateAt(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagonAt(coordinate);
        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void placeMeepleOnLevelTwo() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));

        Coordinate Level2Coordinate = new Coordinate(100, 100);

        Hexagon hexagon = board.getHexagonAt(Level2Coordinate);

        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(Level2Coordinate, board);

        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void cantStartSettlementOverTopExistingSettlementSameColor() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));

        Coordinate testCoordinate = new Coordinate (100, 100);
        Hexagon hexagon = board.getHexagonAt(testCoordinate);
        Player player = new Player(Color.WHITE);

        boolean success = player.placeMeepleOnHexagon(testCoordinate, board);

        assertEquals(false, success);
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void cantStartSettlementOverTopExistingSettlementDifferentColor() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));

        Coordinate testCoordinate = new Coordinate (100, 100);
        Hexagon hexagon = board.getHexagonAt(testCoordinate);

        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(testCoordinate, board);

        Player player2 = new Player(Color.BLACK);

        assertEquals(false, player2.placeMeepleOnHexagon(testCoordinate, board));
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void tryToStartSettlementOnLevelTwo() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));

        Coordinate testCoordinate = new Coordinate (100, 100);

        Hexagon hexagon = board.getHexagonAt(new Coordinate(100, 100));

        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(testCoordinate, board);

        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void volcanoShouldPreventMeeplePlacement() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;

        Coordinate testCoordinate = new Coordinate(100, 100);

        Hexagon hexagon = board.getHexagonAt(testCoordinate);

        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(testCoordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void entireSettlementShouldNotBeNuked() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        board.placeTile(new TileMove(new Tile(Terrain.JUNGLE, Terrain.ROCK), HexagonNeighborDirection.LOWERLEFT, new Coordinate(98, 101)));

        Coordinate testCoordinateOne = new Coordinate(99, 101);
        Coordinate testCoordinateTwo = new Coordinate(99, 100);

        Hexagon hexagonOne = board.getHexagonAt(testCoordinateOne);
        Hexagon hexagonTwo = board.getHexagonAt(testCoordinateTwo);

        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(testCoordinateOne, board);
        player.placeMeepleOnHexagon(testCoordinateTwo, board);

        board.placeTile(new TileMove(new Tile(Terrain.JUNGLE, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(98, 101)));

        assertEquals(PieceStatusHexagon.MEEPLE, hexagonOne.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, hexagonTwo.getPiecesStatus());
        assertEquals(18, player.getMeeplesCount());
        assertEquals(2, player.getScore());
    }
}
