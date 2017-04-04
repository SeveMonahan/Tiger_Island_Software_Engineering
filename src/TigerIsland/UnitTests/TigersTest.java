package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TigersTest {
    @Test
    public void shouldInitializeToTwoTigers() throws Exception {
        Player player = new Player(Color.WHITE);
        assertEquals(2, player.getTigerCount());
    }

    @Test
    public void placeTigerOnLevelZero() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate testCoordinate = new Coordinate(100, 101);
        Hexagon hexagon = board.getHexagon(testCoordinate);

        Player player = new Player(Color.WHITE);

        player.placeTigerOnHexagon(testCoordinate, board);

        Assert.assertEquals(0, player.getScore());
        Assert.assertEquals(2, player.getTigerCount());
        assertEquals(HexagonOccupationStatus.EMPTY, hexagon.getOccupationStatus());
    }

    @Test
    public void placeTigerOnLevelOne() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASSLAND), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        Coordinate coordinate = new Coordinate(100,100).getNeighboringCoordinate(HexagonNeighborDirection.LEFT);


        Player player = new Player(Color.WHITE);

        player.placeTigerOnHexagon(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(2, player.getTigerCount());

        Hexagon hexagon = board.getHexagon(coordinate);

        assertEquals(HexagonOccupationStatus.EMPTY, hexagon.getOccupationStatus());
    }
    @Test
    public void placeTigerOnVolcano() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASSLAND), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        Coordinate coordinate = new Coordinate(101,100).getNeighboringCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player(Color.WHITE);

        player.placeTigerOnHexagon(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(2, player.getTigerCount());
        assertEquals(HexagonOccupationStatus.EMPTY, hexagon.getOccupationStatus());
    }
}