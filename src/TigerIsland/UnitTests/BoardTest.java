package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardTest {
    @Test
    public void initializeBoard() throws Exception {
        Board TestBoard = new Board();
    }

    @Test
    public void getHexagon() throws Exception {
        Board TestBoard = new Board();
        Hexagon TestHexagon = TestBoard.getHexagonAt(new Coordinate(0, 0));
        assert(TestHexagon instanceof Hexagon);
    }

    @Test
    public void setHexagon() throws Exception {
        Board TestBoard = new Board();

        Coordinate TestCoordinate = new Coordinate(0, 0);
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        TestBoard.setHexagonAt(TestCoordinate, TestHexagon);

        Hexagon ReturnedHexagon = TestBoard.getHexagonAt(TestCoordinate);
        assertEquals(Terrain.ROCK, ReturnedHexagon.getTerrain());
        assertEquals(1, ReturnedHexagon.getLevel());
    }

    @Test
    public void getNeighborsOddX() throws Exception {
        Board TestBoard = new Board();
        Hexagon TestHexagon = new Hexagon();

        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        TestBoard.setHexagonAt(new Coordinate(102,101), TestHexagon);
        TestBoard.setHexagonAt(new Coordinate(100,101), TestHexagon);
        TestBoard.setHexagonAt(new Coordinate(101,100), TestHexagon);
        TestBoard.setHexagonAt(new Coordinate(101,102), TestHexagon);
        TestBoard.setHexagonAt(new Coordinate(102,100), TestHexagon);
        TestBoard.setHexagonAt(new Coordinate(102,102), TestHexagon);

        Hexagon[] neighbors = TestBoard.getNeighboringHexagons(new Coordinate(101,101));

        for(int i=0; i<6; i++){
            assertEquals(1, neighbors[i].getLevel());
        }
    }

    @Test
    public void getNeighborsEvenX() throws Exception {
        Board TestBoard = new Board();
        Hexagon TestHexagon = new Hexagon();

        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        TestBoard.setHexagonAt( new Coordinate(53,52), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(51,52), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(52,51), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(52,53), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(51,51), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(51,53), TestHexagon);

        Hexagon[] neighbors = TestBoard.getNeighboringHexagons(new Coordinate(52,52));

        for(int i=0; i<6; i++){
            assertEquals(1, neighbors[i].getLevel());
        }
    }

    @Test
    public void getNeighborsOddXEvenY() throws Exception {
        Board TestBoard = new Board();
        Hexagon TestHexagon = new Hexagon();

        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        TestBoard.setHexagonAt( new Coordinate(70,70), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(72,70), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(70,71), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(71,71), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(70,69), TestHexagon);
        TestBoard.setHexagonAt( new Coordinate(71,69), TestHexagon);

        Hexagon[] neighbors = TestBoard.getNeighboringHexagons(new Coordinate(71,70));

        for(int i=0; i<6; i++){
            assertEquals(1, neighbors[i].getLevel());
        }
    }
}
