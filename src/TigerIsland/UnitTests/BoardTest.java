package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

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
        boolean result = TestBoard.expandSettlementCheck(player, coordinate, terrain);
        assertEquals(true,result);
    }
}
