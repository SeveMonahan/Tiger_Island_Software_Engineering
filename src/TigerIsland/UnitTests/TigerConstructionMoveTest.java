package TigerIsland.UnitTests;

import TigerIsland.*;
import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TigerConstructionMoveTest {
    public void placeMeepleOnHexagon(Color playerColor, Hexagon hexagon) {
        hexagon.setOccupationStatus(playerColor, PieceStatusHexagon.MEEPLE);
    }

    @Test
    public void canPerformMoveTest() {
        Board board = new Board();
        board.placeStartingTile();
        Coordinate upperRight = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);
        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);
        Coordinate right = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.RIGHT);
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(right);
        move1.makePreverifiedMove(player_1, board);

        TigerConstructionMove move2= new TigerConstructionMove(upperRight);
        assertEquals(upperRight, move2.getCoordinate());
        assertEquals(false, move2.canPerformMove(player_1, board));

        Hexagon levelThreeHexagon = new Hexagon();
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        assertEquals(3, levelThreeHexagon.getLevel());
        board.setHexagonAt(upperRight, levelThreeHexagon);

        assertEquals(2, player_1.getTigerCount());
        assertEquals(true, move2.canPerformMove(player_1, board));
    }

    @Test
    public void makePreverifiedMoveTest() {
        Board board = new Board();
        board.placeStartingTile();
        Coordinate upperRight = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);
        Color Player_Color = Color.WHITE;
        Player player_1 = new Player(Player_Color);
        Coordinate right = new Coordinate(100, 100).getNeighboringCoordinateAt(HexagonNeighborDirection.RIGHT);
        FoundSettlementConstructionMove move1 = new FoundSettlementConstructionMove(right);
        move1.makePreverifiedMove(player_1, board);
        assertEquals(1,player_1.getScore());

        TigerConstructionMove move2 = new TigerConstructionMove(upperRight);
        assertEquals(false, move2.canPerformMove(player_1, board));
        Hexagon levelThreeHexagon = new Hexagon();
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.JUNGLE);
        levelThreeHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);
        assertEquals(3, levelThreeHexagon.getLevel());
        board.setHexagonAt(upperRight, levelThreeHexagon);
        assertEquals(2, player_1.getTigerCount());
        assertEquals(true, move2.canPerformMove(player_1, board));

        TigerConstructionMove move3 = new TigerConstructionMove(upperRight);
        move3.makePreverifiedMove(player_1, board);

        assertEquals(1, player_1.getTigerCount());
        assertEquals(76, player_1.getScore());
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
        TestCase.assertEquals(false, settlement.containsTiger(board));

        boolean isSuccess = player.buildTigerPlayground(testCoordinateOne, board);

        TestCase.assertEquals(false, isSuccess);
        TestCase.assertEquals(1, player.getScore());
        TestCase.assertEquals(2, player.getTigerCount());
        TestCase.assertEquals(PieceStatusHexagon.EMPTY, hexagonOne.getPiecesStatus());
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
        TestCase.assertEquals(false, settlement.containsTiger(board));

        boolean isSuccess = player.buildTigerPlayground(upperLeftCoordinate, board);

        TestCase.assertEquals(false, isSuccess);
        TestCase.assertEquals(PieceStatusHexagon.EMPTY, upperLeft.getPiecesStatus());
        TestCase.assertEquals(0, player.getScore());
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
        TestCase.assertEquals(false, tigerMove.canPerformMove(player, board));
        TestCase.assertEquals(PieceStatusHexagon.EMPTY, upperLeft.getPiecesStatus());
        TestCase.assertEquals(0, player.getScore());
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
        TestCase.assertEquals(false, settlement.containsTiger(board));

        boolean isSuccess = player.buildTigerPlayground(TestCoordinate3, board);

        TestCase.assertEquals(true, isSuccess);
        TestCase.assertEquals(PieceStatusHexagon.TIGER, TestHexagon3.getPiecesStatus());
        TestCase.assertEquals(75, player.getScore());

        isSuccess = player.buildTigerPlayground(TestCoordinate1, board);

        TestCase.assertEquals(false, isSuccess);
        TestCase.assertEquals(PieceStatusHexagon.EMPTY, TestHexagon1.getPiecesStatus());
        TestCase.assertEquals(75, player.getScore());
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
        TestCase.assertEquals(false, settlement.containsTiger(board));

        player.subtractTiger();
        player.subtractTiger();

        boolean isSuccess = player.buildTigerPlayground(upperLeftCoordinate, board);

        TestCase.assertEquals(false, isSuccess);
        TestCase.assertEquals(PieceStatusHexagon.EMPTY, upperLeft.getPiecesStatus());
        TestCase.assertEquals(0, player.getScore());
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
        TestCase.assertEquals(false, settlement.containsTiger(board));

        boolean isSuccess = player.buildTigerPlayground(upperLeftCoordinate, board);

        TestCase.assertEquals(true, isSuccess);
        TestCase.assertEquals(PieceStatusHexagon.TIGER, upperLeft.getPiecesStatus());
        TestCase.assertEquals(75, player.getScore());
    }
}
