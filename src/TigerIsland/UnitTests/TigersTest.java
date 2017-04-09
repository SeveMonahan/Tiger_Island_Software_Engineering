package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TigersTest {
    public void placeMeepleOnHexagon(Color playerColor, Hexagon hexagon) {
        hexagon.setOccupationStatus(playerColor, PieceStatusHexagon.MEEPLE);
    }

    @Test
    public void shouldInitializeToTwoTigers() throws Exception {
        Player player = new Player(Color.WHITE);
        assertEquals(2, player.getTigerCount());
    }

    @Test
    public void placeTigerOnLevelZero() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate testCoordinate = new Coordinate(99, 100);
        Hexagon hexagon = board.getHexagonAt(testCoordinate);

        Player player = new Player(Color.WHITE);

        boolean isSuccess = player.buildTigerPlayground(testCoordinate, board);

        assertEquals(false, isSuccess);
        assertEquals(0, player.getScore());
        assertEquals(2, player.getTigerCount());
        assertEquals(PieceStatusHexagon.EMPTY, hexagon.getPiecesStatus());
    }

    @Test
    public void tigerInPartOfSettlementCanBeNuked() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        board.placeTile(new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100)));

        Coordinate targetCoordinateOne = new Coordinate(98, 101);
        Coordinate targetCoordinateTwo = new Coordinate(99,101);
        Coordinate targetCoordinateThree = new Coordinate(100,101);
        Coordinate volcanoCoordinate = new Coordinate(99,100);
        Hexagon targetHexagonOne = board.getHexagonAt(targetCoordinateOne);
        Hexagon targetHexagonTwo = board.getHexagonAt(targetCoordinateTwo);
        Hexagon targetHexagonThree = board.getHexagonAt(targetCoordinateThree);
        Hexagon volcanoHexagon = board.getHexagonAt(volcanoCoordinate);

        volcanoHexagon.changeTerrainTypeThoughExplosion(Terrain.VOLCANO); // Now it's level 2.
        volcanoHexagon.changeTerrainTypeThoughExplosion(Terrain.VOLCANO); // Now it's level 3.

        targetHexagonOne.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 2.
        targetHexagonOne.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 3.

        targetHexagonTwo.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 2.
        targetHexagonTwo.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 3.

        Player player = new Player(Color.WHITE);

        placeMeepleOnHexagon(player.getColor(), targetHexagonOne);
        boolean isSuccess = player.buildTigerPlayground(targetCoordinateTwo, board);
        assertEquals(true, isSuccess);
        player.foundSettlement(targetCoordinateThree, board);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.ROCK);
        TileMove tileMove = new TileMove(tile2, HexagonNeighborDirection.UPPERLEFT, new Coordinate(99, 100));
        isSuccess = board.placeTile(tileMove);

        assertEquals(true, isSuccess);
        assertEquals(PieceStatusHexagon.EMPTY, targetHexagonOne.getPiecesStatus());
        assertEquals(PieceStatusHexagon.EMPTY, targetHexagonTwo.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, targetHexagonThree.getPiecesStatus());
    }
}