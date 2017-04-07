package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void createBoard() throws Exception{
        Board TestBoard = new Board();
    }

    @Test
    public void getHexagon() throws Exception{
        Board TestBoard = new Board();
        assert(TestBoard.getHexagon( new Coordinate(0,0))
                instanceof Hexagon);
    }

    @Test
    public void setHexagon() throws Exception{
        Board TestBoard = new Board();

        Hexagon TestHexagon = new Hexagon();

        Coordinate TestCoordinate = new Coordinate(0, 0);
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        TestBoard.setHexagon(TestCoordinate,TestHexagon);

        Hexagon ReturnedHexagon = TestBoard.getHexagon(TestCoordinate);

        assertEquals(1, ReturnedHexagon.getLevel());

    }

    @Test
    public void startTile() throws Exception {
        Board TestBoard = new Board();
        TestBoard.placeStartingTile();
        Coordinate centerCoord = new Coordinate(100, 100);
        Hexagon center = TestBoard.getHexagon(centerCoord);
        Hexagon upperLeft = TestBoard.getNeighboringHexagon(centerCoord, HexagonNeighborDirection.UPPERLEFT);
        Hexagon upperRight = TestBoard.getNeighboringHexagon(centerCoord, HexagonNeighborDirection.UPPERRIGHT);
        Hexagon lowerRight = TestBoard.getNeighboringHexagon(centerCoord, HexagonNeighborDirection.LOWERRIGHT);
        Hexagon lowerLeft = TestBoard.getNeighboringHexagon(centerCoord, HexagonNeighborDirection.LOWERLEFT);
        assertEquals(Terrain.JUNGLE, upperLeft.getTerrain());
        assertEquals(Terrain.LAKE, upperRight.getTerrain());
        assertEquals(Terrain.GRASS, lowerRight.getTerrain());
        assertEquals(Terrain.ROCK, lowerLeft.getTerrain());
        assertEquals(Terrain.VOLCANO, center.getTerrain());
    }

    @Test
    public void getNeighborsOddX() throws Exception{
        Board TestBoard = new Board();

        Hexagon TestHexagon = new Hexagon();

        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        TestBoard.setHexagon(new Coordinate(102,101), TestHexagon);
        TestBoard.setHexagon(new Coordinate(100,101), TestHexagon);

        TestBoard.setHexagon(new Coordinate(101,100), TestHexagon);
        TestBoard.setHexagon(new Coordinate(101,102), TestHexagon);

        TestBoard.setHexagon(new Coordinate(102,100), TestHexagon);
        TestBoard.setHexagon(new Coordinate(102,102), TestHexagon);

        Hexagon[] neighbors = TestBoard.getNeighboringHexagons(new Coordinate(101,101));

        for(int i=0; i<6; i++){
            assertEquals(1, neighbors[i].getLevel());
        }
    }

    @Test
    public void getNeighborsEvenX() throws Exception{
        Board TestBoard = new Board();

        Hexagon TestHexagon = new Hexagon();

        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        TestBoard.setHexagon( new Coordinate(53,52), TestHexagon);
        TestBoard.setHexagon( new Coordinate(51,52), TestHexagon);

        TestBoard.setHexagon( new Coordinate(52,51), TestHexagon);
        TestBoard.setHexagon( new Coordinate(52,53), TestHexagon);

        TestBoard.setHexagon( new Coordinate(51,51), TestHexagon);
        TestBoard.setHexagon( new Coordinate(51,53), TestHexagon);

        Hexagon[] neighbors = TestBoard.getNeighboringHexagons(new Coordinate(52,52));

        for(int i=0; i<6; i++){
            assertEquals(1, neighbors[i].getLevel());
        }
    }

    @Test
    public void getNeighborsOddXEvenY() throws Exception{

        Board TestBoard = new Board();

        Hexagon TestHexagon = new Hexagon();

        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        TestBoard.setHexagon( new Coordinate(70,70), TestHexagon);
        TestBoard.setHexagon( new Coordinate(72,70), TestHexagon);

        TestBoard.setHexagon( new Coordinate(70,71), TestHexagon);
        TestBoard.setHexagon( new Coordinate(71,71), TestHexagon);

        TestBoard.setHexagon( new Coordinate(70,69), TestHexagon);
        TestBoard.setHexagon( new Coordinate(71,69), TestHexagon);

        Hexagon[] neighbors = TestBoard.getNeighboringHexagons(new Coordinate(71,70));

        for(int i=0; i<6; i++){
            assertEquals(1, neighbors[i].getLevel());
        }
    }

    @Test
    public void settlementExpansion() throws Exception{
        Board TestBoard = new Board();
        Player player = new Player(Color.BLACK);

        Tile tile_01 = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Tile tile_02 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Tile tile_03 = new Tile(Terrain.GRASS, Terrain.ROCK);

        TileMove tileMove_03 = new TileMove(tile_03, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(100, 100));
        TileMove tileMove_02 = new TileMove(tile_02, HexagonNeighborDirection.LOWERLEFT, new Coordinate(100, 102));
        TileMove tileMove_01 = new TileMove(tile_01, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 99));

        TestBoard.placeTile(tileMove_01);
        TestBoard.placeTile(tileMove_02);
        TestBoard.placeTile(tileMove_03);

        player.placeMeepleOnHexagon(new Coordinate(101, 100), TestBoard);

        Settlement settlement = TestBoard.getSettlement(new Coordinate(101, 100));

        // Boolean result = TestBoard.expandSettlementWithCheck(player, new Coordinate(101, 100), Terrain.GRASS);
        Boolean result = settlement.expandSettlementWithCheck(TestBoard, player, Terrain.GRASS);

        assertEquals(true, result);
        assertEquals(HexagonOccupationStatus.MEEPLE, TestBoard.getHexagon(new Coordinate(100, 101)).getOccupationStatus() );

        assertEquals(HexagonOccupationStatus.MEEPLE, TestBoard.getHexagon(new Coordinate(99, 100)).getOccupationStatus() );

        assertEquals(HexagonOccupationStatus.MEEPLE, TestBoard.getHexagon(new Coordinate(99, 101)).getOccupationStatus() );
    }

    @Test
    public void settlementExpansion2() throws Exception{
        Board TestBoard = new Board();
        Player player = new Player(Color.BLACK);

        Tile tile_01 = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Tile tile_02 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Tile tile_03 = new Tile(Terrain.GRASS, Terrain.ROCK);

        TileMove tileMove_03 = new TileMove(tile_03, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(100, 100));
        TileMove tileMove_02 = new TileMove(tile_02, HexagonNeighborDirection.LOWERLEFT, new Coordinate(100, 102));
        TileMove tileMove_01 = new TileMove(tile_01, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 99));

        TestBoard.placeTile(tileMove_01);
        TestBoard.placeTile(tileMove_02);
        TestBoard.placeTile(tileMove_03);

        player.placeMeepleOnHexagon(new Coordinate(99, 101), TestBoard);

        Settlement settlement = TestBoard.getSettlement(new Coordinate(99, 101));
        Boolean result = settlement.expandSettlementWithCheck(TestBoard, player, Terrain.GRASS);
        // Boolean result = TestBoard.expandSettlementWithCheck(player, new Coordinate(99, 101), Terrain.GRASS);

        assertEquals(true, result);
        assertEquals(HexagonOccupationStatus.MEEPLE, TestBoard.getHexagon(new Coordinate(100, 101)).getOccupationStatus() );

        assertEquals(HexagonOccupationStatus.MEEPLE, TestBoard.getHexagon(new Coordinate(99, 100)).getOccupationStatus() );
    }

    @Test
    public void settlementExpansion3() throws Exception{
        Board TestBoard = new Board();
        Player player = new Player(Color.BLACK);

        Tile tile_01 = new Tile(Terrain.GRASS, Terrain.JUNGLE);
        Tile tile_02 = new Tile(Terrain.GRASS, Terrain.LAKE);
        Tile tile_03 = new Tile(Terrain.GRASS, Terrain.ROCK);

        TileMove tileMove_03 = new TileMove(tile_03, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(100, 100));
        TileMove tileMove_02 = new TileMove(tile_02, HexagonNeighborDirection.LOWERLEFT, new Coordinate(100, 102));
        TileMove tileMove_01 = new TileMove(tile_01, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 99));

        TestBoard.placeTile(tileMove_01);
        TestBoard.placeTile(tileMove_02);
        TestBoard.placeTile(tileMove_03);

        player.placeMeepleOnHexagon(new Coordinate(99, 101), TestBoard);
        player.placeMeepleOnHexagon(new Coordinate(100, 101), TestBoard);

        Settlement settlement = TestBoard.getSettlement(new Coordinate(100, 101));
        Boolean result = settlement.expandSettlementWithCheck(TestBoard, player, Terrain.GRASS);
        // Boolean result = TestBoard.expandSettlementWithCheck(player, new Coordinate(100, 101), Terrain.GRASS);

        assertEquals(true, result);

        assertEquals(HexagonOccupationStatus.MEEPLE, TestBoard.getHexagon(new Coordinate(99, 100)).getOccupationStatus() );
    }
}
