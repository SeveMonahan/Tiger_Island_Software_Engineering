package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
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
        assertEquals(Terrain.GRASSLAND, lowerRight.getTerrain());
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
    public void settlementExpansionFloodFill() throws Exception{
        Board TestBoard = new Board();
        Player player = new Player(Color.BLACK);

        Hexagon TestHexagon = new Hexagon();

        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        TestBoard.setHexagon( new Coordinate(70,70), TestHexagon);
        TestBoard.setHexagon( new Coordinate(72,70), TestHexagon);

        TestBoard.setHexagon( new Coordinate(70,71), TestHexagon);
        TestBoard.setHexagon( new Coordinate(71,71), TestHexagon);

        TestBoard.setHexagon( new Coordinate(70,69), TestHexagon);
        TestBoard.setHexagon( new Coordinate(71,69), TestHexagon);

        // Hexagon[] neighbors = TestBoard.getNeighbors(new Coordinate(71,70));

        Coordinate coordinate = new Coordinate(71, 70);
        Terrain terrain = Terrain.ROCK;
        boolean result = TestBoard.expandSettlementWithCheck(player, coordinate, terrain);
        assertEquals(true,result);
    }

    @Test
    public void convertToSquare() throws Exception{
        Coordinate testCoordinate = new Coordinate(0,1,-1);
        assertEquals(101, testCoordinate.getX());
        assertEquals(100, testCoordinate.getY());

        Coordinate testCoordinate2 = new Coordinate(0,2,-2);
        assertEquals(102, testCoordinate2.getX());
        assertEquals(100, testCoordinate2.getY());

        Coordinate testCoordinate3 = new Coordinate(1,-1,0);
        assertEquals(99, testCoordinate3.getX());
        assertEquals(99, testCoordinate3.getY());

        Coordinate testCoordinate4 = new Coordinate(0,-1,1);
        assertEquals(99, testCoordinate4.getX());
        assertEquals(100, testCoordinate4.getY());

        Coordinate testCoordinate5 = new Coordinate(-1,0,1);
        assertEquals(99, testCoordinate5.getX());
        assertEquals(101, testCoordinate5.getY());


    }


    @Test
    public void convertToCubeCordinates() throws Exception{

        Coordinate testCoordinate = new Coordinate(101,100);
        int result[] = testCoordinate.ConvertToCube();
        int testResult[] = {0,1,-1};
        assertArrayEquals(testResult,result);

        Coordinate testCoordinate1 = new Coordinate(102,100);
        int result1[] = testCoordinate1.ConvertToCube();
        int testResult1[] = {0,2,-2};
        assertArrayEquals(testResult1,result1);

        Coordinate testCoordinate2 = new Coordinate(99,100);
        int result2[] = testCoordinate2.ConvertToCube();
        int testResult2[] = {0,-1,1};
        assertArrayEquals(testResult2, result2);

        Coordinate testCoordinate3 = new Coordinate(100,101);
        int result3[] = testCoordinate3.ConvertToCube();
        int testResult3[] = {-1,1,0};
        assertArrayEquals(testResult3, result3);

        Coordinate testCoordinate4 = new Coordinate(99,99);
        int result4[] = testCoordinate4.ConvertToCube();
        int testResult4[] = {1,-1,0};
        assertArrayEquals(testResult4, result4);

        Coordinate testCoordinate5 = new Coordinate(99,100);
        int result5[] = testCoordinate5.ConvertToCube();
        int testResult5[] = {0,-1,1};
        assertArrayEquals(testResult5, result5);

        Coordinate testCoordinate6 = new Coordinate(99,101);
        int result6[] = testCoordinate6.ConvertToCube();
        int testResult6[] = {-1,0,1};
        assertArrayEquals(testResult6, result6);

        Coordinate testCoordinate7 = new Coordinate(101,101);
        int result7[] = testCoordinate7.ConvertToCube();
        int testResult7[] = {-1,2,-1};
        assertArrayEquals(testResult7, result7);

    }

}
