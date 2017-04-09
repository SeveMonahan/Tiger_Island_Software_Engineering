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
        Hexagon hexagon = board.getHexagonAt(testCoordinate);

        Player player = new Player(Color.WHITE);

        player.buildTigerPlayground(testCoordinate, board);

        Assert.assertEquals(0, player.getScore());
        Assert.assertEquals(2, player.getTigerCount());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void placeTigerOnLevelOne() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        Coordinate coordinate = new Coordinate(100,100).getNeighboringCoordinateAt(HexagonNeighborDirection.LEFT);


        Player player = new Player(Color.WHITE);

        player.buildTigerPlayground(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(2, player.getTigerCount());

        Hexagon hexagon = board.getHexagonAt(coordinate);

        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void placeTigerOnLevelThree() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Hexagon upperleft = board.getHexagonAt(new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT));

        upperleft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        upperleft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);

        Player player = new Player(Color.BLACK);
        TigerConstructionMove move = new TigerConstructionMove(new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT));

        assertEquals(false, move.canPerformMove(player, board));

        player.foundSettlement(new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT), board);

        assertEquals(true, move.canPerformMove(player, board));

    }

    @Test
    public void placeTigerOnVolcano() throws Exception {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
        Coordinate coordinate = new Coordinate(101,100).getNeighboringCoordinateAt(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagonAt(coordinate);
        Player player = new Player(Color.WHITE);

        player.buildTigerPlayground(coordinate, board);

        assertEquals(0, player.getScore());
        assertEquals(2, player.getTigerCount());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    // TODO: Need to write success case, case where a Tile tries to nuke a Tiger and is successful, and case were being added to a settlement which already has a Tiger
    // stops the Tiger, TigerMoves not working once the plaer is out of Tigers.
}