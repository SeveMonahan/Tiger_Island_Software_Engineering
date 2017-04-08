package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SettlementTest {
    @Test
    public void settlementOfSizeOne() {
        Player playerOne = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board();
        board.placeStartingTile();

        Coordinate centerCoordinate = new Coordinate(100,100);
        Coordinate targetOne = centerCoordinate.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERLEFT);
        Coordinate targetTwo = centerCoordinate.getNeighboringCoordinateAt(HexagonNeighborDirection.UPPERRIGHT);

        playerOne.placeMeepleOnHexagon(targetOne, board);
        playerTwo.placeMeepleOnHexagon(targetTwo, board);

        Settlement settlement = board.getSettlement(targetOne);
        assertEquals(1, settlement.getSettlementSize());
    }

    @Test
    public void settlementOfSizeTwo() {
        Player playerOne = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board();
        board.placeStartingTile();

        Coordinate playerOneTargetOne = new Coordinate(99,101);
        Coordinate playerOneTargetTwo = new Coordinate (100,101);

        Coordinate playerTwoTargetOne = new Coordinate(99,99);
        Coordinate playerTwoTargetTwo = new Coordinate (100,99);

        playerOne.placeMeepleOnHexagon(playerOneTargetOne, board);
        playerOne.placeMeepleOnHexagon(playerOneTargetTwo, board);

        playerTwo.placeMeepleOnHexagon(playerTwoTargetOne, board);
        playerTwo.placeMeepleOnHexagon(playerTwoTargetTwo, board);

        Settlement settlement = board.getSettlement(playerOneTargetOne);
        assertEquals(2, settlement.getSettlementSize());
    }

    @Test
    public void settlementExpansionTest1() throws Exception {
        Board TestBoard = new Board();
        Player player = new Player(Color.BLACK);

        Tile tile_01 = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Tile tile_02 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Tile tile_03 = new Tile(Terrain.GRASS, Terrain.ROCK);

        TileMove tileMove_01 = new TileMove(tile_01, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 99));
        TileMove tileMove_02 = new TileMove(tile_02, HexagonNeighborDirection.LOWERLEFT, new Coordinate(100, 102));
        TileMove tileMove_03 = new TileMove(tile_03, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(100, 100));

        TestBoard.placeTile(tileMove_01);
        TestBoard.placeTile(tileMove_02);
        TestBoard.placeTile(tileMove_03);

        Coordinate sourceCoordinate = new Coordinate(101,100);

        player.placeMeepleOnHexagon(sourceCoordinate, TestBoard);

        Settlement settlement = TestBoard.getSettlement(sourceCoordinate);
        boolean result = settlement.expandSettlementWithCheck(TestBoard, player, Terrain.GRASS);

        assertEquals(true, result);

        Hexagon TestHexagon1 = TestBoard.getHexagonAt(new Coordinate(100,101));
        Hexagon TestHexagon2 = TestBoard.getHexagonAt(new Coordinate(99,100));
        Hexagon TestHexagon3 = TestBoard.getHexagonAt(new Coordinate(99,101));

        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon1.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon2.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon3.getPiecesStatus());

        assertEquals(Color.BLACK, TestHexagon1.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon2.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon3.getOccupationColor());

        assertEquals(16, player.getMeeplesCount());
    }

    @Test
    public void settlementExpansionTest2() throws Exception {
        Board TestBoard = new Board();
        Player player = new Player(Color.BLACK);

        Tile tile_01 = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Tile tile_02 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Tile tile_03 = new Tile(Terrain.GRASS, Terrain.ROCK);

        TileMove tileMove_01 = new TileMove(tile_01, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 99));
        TileMove tileMove_02 = new TileMove(tile_02, HexagonNeighborDirection.LOWERLEFT, new Coordinate(100, 102));
        TileMove tileMove_03 = new TileMove(tile_03, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(100, 100));

        TestBoard.placeTile(tileMove_01);
        TestBoard.placeTile(tileMove_02);
        TestBoard.placeTile(tileMove_03);

        Coordinate sourceCoordinate = new Coordinate(99,101);

        player.placeMeepleOnHexagon(sourceCoordinate, TestBoard);

        Settlement settlement = TestBoard.getSettlement(new Coordinate(99, 101));
        boolean result = settlement.expandSettlementWithCheck(TestBoard, player, Terrain.GRASS);

        assertEquals(true, result);

        Hexagon TestHexagon1 = TestBoard.getHexagonAt(new Coordinate(100,101));
        Hexagon TestHexagon2 = TestBoard.getHexagonAt(new Coordinate(99,100));

        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon1.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon2.getPiecesStatus());

        assertEquals(Color.BLACK, TestHexagon1.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon2.getOccupationColor());

        assertEquals(17, player.getMeeplesCount());
    }

    @Test
    public void settlementExpansionTest3() throws Exception {
        Board TestBoard = new Board();
        Player player = new Player(Color.BLACK);

        Tile tile_01 = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Tile tile_02 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Tile tile_03 = new Tile(Terrain.GRASS, Terrain.ROCK);

        TileMove tileMove_01 = new TileMove(tile_01, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 99));
        TileMove tileMove_02 = new TileMove(tile_02, HexagonNeighborDirection.LOWERLEFT, new Coordinate(100, 102));
        TileMove tileMove_03 = new TileMove(tile_03, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(100, 100));

        TestBoard.placeTile(tileMove_01);
        TestBoard.placeTile(tileMove_02);
        TestBoard.placeTile(tileMove_03);

        Coordinate sourceCoordinateOne = new Coordinate(99,101);
        Coordinate sourceCoordinateTwo = new Coordinate(100,101);

        player.placeMeepleOnHexagon(sourceCoordinateOne, TestBoard);
        player.placeMeepleOnHexagon(sourceCoordinateTwo, TestBoard);

        Settlement settlement = TestBoard.getSettlement(sourceCoordinateOne);
        boolean result = settlement.expandSettlementWithCheck(TestBoard, player, Terrain.GRASS);

        assertEquals(true, result);

        Hexagon TestHexagon = TestBoard.getHexagonAt(new Coordinate(99,100));

        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon.getPiecesStatus());

        assertEquals(Color.BLACK, TestHexagon.getOccupationColor());

        assertEquals(17, player.getMeeplesCount());
    }

    @Test
    public void settlementExpansionTest4() {
        Board TestBoard = new Board();
        Player player = new Player(Color.BLACK);

        Tile tile_01 = new Tile(Terrain.GRASS, Terrain.GRASS);
        Tile tile_02 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Tile tile_03 = new Tile(Terrain.GRASS, Terrain.GRASS);
        Tile tile_04 = new Tile(Terrain.ROCK, Terrain.GRASS);

        TileMove tileMove_01 = new TileMove(tile_01, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 99));
        TileMove tileMove_02 = new TileMove(tile_02, HexagonNeighborDirection.LOWERLEFT, new Coordinate(100, 102));
        TileMove tileMove_03 = new TileMove(tile_03, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(100, 100));
        TileMove tileMove_04 = new TileMove(tile_04, HexagonNeighborDirection.LOWERLEFT, new Coordinate(101,99));

        TestBoard.placeTile(tileMove_01);
        TestBoard.placeTile(tileMove_02);
        TestBoard.placeTile(tileMove_03);
        TestBoard.placeTile(tileMove_04);

        Coordinate sourceCoordinateOne = new Coordinate(99,102);
        Coordinate sourceCoordinateTwo = new Coordinate(101, 98);

        player.placeMeepleOnHexagon(sourceCoordinateOne, TestBoard);
        player.placeMeepleOnHexagon(sourceCoordinateTwo, TestBoard);

        Settlement settlement = TestBoard.getSettlement(sourceCoordinateTwo);
        boolean result = settlement.expandSettlementWithCheck(TestBoard, player, Terrain.GRASS);

        assertEquals(true, result);

        Hexagon TestHexagon1 = TestBoard.getHexagonAt(new Coordinate(99,101));
        Hexagon TestHexagon2 = TestBoard.getHexagonAt(new Coordinate(100,101));
        Hexagon TestHexagon3 = TestBoard.getHexagonAt(new Coordinate(99,100));
        Hexagon TestHexagon4 = TestBoard.getHexagonAt(new Coordinate(101,100));
        Hexagon TestHexagon5 = TestBoard.getHexagonAt(new Coordinate(99,99));
        Hexagon TestHexagon6 = TestBoard.getHexagonAt(new Coordinate(100,99));

        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon1.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon2.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon3.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon4.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon5.getPiecesStatus());
        assertEquals(PieceStatusHexagon.MEEPLE, TestHexagon6.getPiecesStatus());

        assertEquals(Color.BLACK, TestHexagon1.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon2.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon3.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon4.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon5.getOccupationColor());
        assertEquals(Color.BLACK, TestHexagon6.getOccupationColor());

        assertEquals(12, player.getMeeplesCount());

        Settlement settlementCheck = TestBoard.getSettlement(sourceCoordinateTwo);

        assertEquals(8, settlementCheck.getSettlementSize());
    }
}
