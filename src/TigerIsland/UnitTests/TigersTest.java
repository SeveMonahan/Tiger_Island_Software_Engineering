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
    public void buildTigerFailsDueToLevelHeight() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate testCoordinateOne = new Coordinate(99, 101);
        Coordinate testCoordinateTwo = new Coordinate(100,101);
        Hexagon hexagonOne = board.getHexagonAt(testCoordinateOne);

        Player player = new Player(Color.WHITE);

        player.foundSettlement(testCoordinateTwo, board);

        Settlement settlement = board.getSettlement(testCoordinateTwo);
        assertEquals(false, settlement.containsTiger(board));

        boolean isSuccess = player.buildTigerPlayground(testCoordinateOne, board);

        assertEquals(false, isSuccess);
        assertEquals(1, player.getScore());
        assertEquals(2, player.getTigerCount());
        assertEquals(PieceStatusHexagon.EMPTY, hexagonOne.getPiecesStatus());
    }

    @Test
    public void buildTigerFailsDueToVolcano() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate center = new Coordinate(100,100);
        Coordinate upperLeftCoordinate = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);
        Coordinate upperRightCoordinate = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);

        Hexagon upperLeft = board.getHexagonAt(upperLeftCoordinate);

        upperLeft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 2.
        upperLeft.changeTerrainTypeThoughExplosion(Terrain.VOLCANO); // Now it's level 3.

        Hexagon upperRight = board.getHexagonAt(upperRightCoordinate);

        upperRight.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 2.
        upperRight.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 3.

        Player player = new Player(Color.BLACK);

        placeMeepleOnHexagon(player.getColor(), upperRight);

        Settlement settlement = board.getSettlement(upperRightCoordinate);
        assertEquals(false, settlement.containsTiger(board));

        boolean isSuccess = player.buildTigerPlayground(upperLeftCoordinate, board);

        assertEquals(false, isSuccess);
        assertEquals(PieceStatusHexagon.EMPTY, upperLeft.getPiecesStatus());
        assertEquals(0, player.getScore());
    }

    @Test
    public void buildTigerPlaygroundFailsDueToNoAdjacency() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate center = new Coordinate(100,100);
        Coordinate upperLeftCoordinate = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);

        Hexagon upperLeft = board.getHexagonAt(upperLeftCoordinate);

        upperLeft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 2.
        upperLeft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 3.

        Player player = new Player(Color.BLACK);

        TigerConstructionMove tigerMove = new TigerConstructionMove(upperLeftCoordinate);
        assertEquals(false, tigerMove.canPerformMove(player, board));
        assertEquals(PieceStatusHexagon.EMPTY, upperLeft.getPiecesStatus());
        assertEquals(0, player.getScore());
    }

    @Test
    public void buildTigerPlaygroundFailsBecauseSettlementAlreadyHasTiger() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile = new Tile(Terrain.GRASS, Terrain.GRASS);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.LEFT, new Coordinate(99,100));
        board.placeTile(tileMove);

        Coordinate TestCoordinate1 = new Coordinate(98,101);
        Coordinate TestCoordinate2 = new Coordinate(99,101);
        Coordinate TestCoordinate3 = new Coordinate(100,101);

        Hexagon TestHexagon1 = board.getHexagonAt(TestCoordinate1);
        Hexagon TestHexagon2 = board.getHexagonAt(TestCoordinate2);
        Hexagon TestHexagon3 = board.getHexagonAt(TestCoordinate3);

        TestHexagon1.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 2.
        TestHexagon1.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 3.

        TestHexagon2.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 2.
        TestHexagon2.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 3.

        TestHexagon3.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 2.
        TestHexagon3.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 3.

        Player player = new Player(Color.BLACK);

        placeMeepleOnHexagon(player.getColor(), TestHexagon2);

        Settlement settlement = board.getSettlement(TestCoordinate2);
        assertEquals(false, settlement.containsTiger(board));

        boolean isSuccess = player.buildTigerPlayground(TestCoordinate3, board);

        assertEquals(true, isSuccess);
        assertEquals(PieceStatusHexagon.TIGER, TestHexagon3.getPiecesStatus());
        assertEquals(75, player.getScore());

        isSuccess = player.buildTigerPlayground(TestCoordinate1, board);

        assertEquals(false, isSuccess);
        assertEquals(PieceStatusHexagon.EMPTY, TestHexagon1.getPiecesStatus());
        assertEquals(75, player.getScore());
    }

    @Test
    public void buildTigerFailsBecausePlayerIsOutOfTigers() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate center = new Coordinate(100,100);
        Coordinate upperLeftCoordinate = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);
        Coordinate upperRightCoordinate = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);

        Hexagon upperLeft = board.getHexagonAt(upperLeftCoordinate);

        upperLeft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 2.
        upperLeft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 3.

        Hexagon upperRight = board.getHexagonAt(upperRightCoordinate);

        upperRight.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 2.
        upperRight.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 3.

        Player player = new Player(Color.BLACK);

        placeMeepleOnHexagon(player.getColor(), upperRight);

        Settlement settlement = board.getSettlement(upperRightCoordinate);
        assertEquals(false, settlement.containsTiger(board));

        player.subtractTiger();
        player.subtractTiger();

        boolean isSuccess = player.buildTigerPlayground(upperLeftCoordinate, board);

        assertEquals(false, isSuccess);
        assertEquals(PieceStatusHexagon.EMPTY, upperLeft.getPiecesStatus());
        assertEquals(0, player.getScore());
    }

    @Test
    public void buildTigerPlaygroundSuccess() throws Exception {
        Board board = new Board();
        board.placeStartingTile();

        Coordinate center = new Coordinate(100,100);
        Coordinate upperLeftCoordinate = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);
        Coordinate upperRightCoordinate = center.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);

        Hexagon upperLeft = board.getHexagonAt(upperLeftCoordinate);

        upperLeft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 2.
        upperLeft.changeTerrainTypeThoughExplosion(Terrain.JUNGLE); // Now it's level 3.

        Hexagon upperRight = board.getHexagonAt(upperRightCoordinate);

        upperRight.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 2.
        upperRight.changeTerrainTypeThoughExplosion(Terrain.GRASS); // Now it's level 3.

        Player player = new Player(Color.BLACK);

        placeMeepleOnHexagon(player.getColor(), upperRight);

        Settlement settlement = board.getSettlement(upperRightCoordinate);
        assertEquals(false, settlement.containsTiger(board));

        boolean isSuccess = player.buildTigerPlayground(upperLeftCoordinate, board);

        assertEquals(true, isSuccess);
        assertEquals(PieceStatusHexagon.TIGER, upperLeft.getPiecesStatus());
        assertEquals(75, player.getScore());
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