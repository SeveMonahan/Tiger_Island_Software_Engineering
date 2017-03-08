import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class BoardAddingTilesTest {
    @Test
    public void ConstructorUsingTile(){
        Board TestBoard = new Board(new Tile(Terrain.JUNGLE, Terrain.ROCK));
    }

    @Test
    public void placeTileNoRestrictions() {
        Board TestBoard = new Board();
        TestBoard.placeTileNoRestrictions(new Tile(Terrain.BEACH, Terrain.GRASS),
                                            DirectionsHex.LEFT, 100, 100);

        Coordinate TestCoordinate1 = new Coordinate(100,100);
        Coordinate TestCoordinate2 = new Coordinate(99,100);
        Coordinate TestCoordinate3 = new Coordinate(99,101);

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagon( TestCoordinate1).getTerrain());

        assertEquals(Terrain.BEACH, TestBoard.getHexagon( TestCoordinate2).getTerrain());

        assertEquals(Terrain.GRASS, TestBoard.getHexagon( TestCoordinate3).getTerrain());

        assertEquals(1, TestBoard.getHexagon( TestCoordinate1).getLevel());

        assertEquals(1, TestBoard.getHexagon( TestCoordinate2).getLevel());

        assertEquals(1, TestBoard.getHexagon( TestCoordinate3).getLevel());
    }

    @Test
    public void initBoardWithTile() {
        Board TestBoard = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));

        Coordinate TestCoordinate1 = new Coordinate(100,100);
        Coordinate TestCoordinate2 = new Coordinate(99,100);
        Coordinate TestCoordinate3 = new Coordinate(99,101);

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagon( TestCoordinate1).getTerrain());

        assertEquals(Terrain.BEACH, TestBoard.getHexagon( TestCoordinate2).getTerrain());

        assertEquals(Terrain.GRASS, TestBoard.getHexagon( TestCoordinate3).getTerrain());

        assertEquals(1, TestBoard.getHexagon(TestCoordinate1).getLevel());

        assertEquals(1, TestBoard.getHexagon(TestCoordinate2).getLevel());

        assertEquals(1, TestBoard.getHexagon(TestCoordinate3).getLevel());
    }

    @Test
    public void placeTileWithRestrictionsLevel0Success(){
       Board TestBoard = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));

        Coordinate TestCoordinate1 = new Coordinate(98,100);
        Coordinate TestCoordinate2 = new Coordinate(97,99);
        Coordinate TestCoordinate3 = new Coordinate(97,100);

       boolean success = TestBoard.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE),
               DirectionsHex.LOWERLEFT, 98, 100);

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagon(TestCoordinate1).getTerrain());

        assertEquals(Terrain.ROCK, TestBoard.getHexagon(TestCoordinate2).getTerrain());

        assertEquals(Terrain.JUNGLE, TestBoard.getHexagon(TestCoordinate3).getTerrain());

        assertEquals(1, TestBoard.getHexagon(TestCoordinate1).getLevel());

        assertEquals(1, TestBoard.getHexagon(TestCoordinate2).getLevel());

        assertEquals(1, TestBoard.getHexagon(TestCoordinate3).getLevel());

        assertEquals(true, success);
    }

    @Test
    public void placeTileWithRestrictionsLevel0Fail(){
        Board TestBoard = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));

        Coordinate TestCoordinate1 = new Coordinate(98,50);
        Coordinate TestCoordinate2 = new Coordinate(97,49);
        Coordinate TestCoordinate3 = new Coordinate(97,50);

        boolean success = TestBoard.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE),
                DirectionsHex.LOWERLEFT, 98, 50);

        assertEquals(Terrain.EMPTY, TestBoard.getHexagon(TestCoordinate1).getTerrain());

        assertEquals(Terrain.EMPTY, TestBoard.getHexagon(TestCoordinate2).getTerrain());

        assertEquals(Terrain.EMPTY, TestBoard.getHexagon(TestCoordinate3).getTerrain());

        assertEquals(0, TestBoard.getHexagon(TestCoordinate1).getLevel());

        assertEquals(0, TestBoard.getHexagon(TestCoordinate2).getLevel());

        assertEquals(0, TestBoard.getHexagon(TestCoordinate3).getLevel());

        assertEquals(false, success);
    }

    @Test
    public void overwriteLevel1Hex(){
        Board TestBoard = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        TestBoard.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE), DirectionsHex.RIGHT, 100, 101);

        boolean isSuccess = TestBoard.placeTile(new Tile(Terrain.ROCK, Terrain.BEACH), DirectionsHex.UPPERLEFT, 100, 100);
        assertEquals(true, isSuccess);

        Hexagon newVolcano = TestBoard.getHexagon(new Coordinate(100,100));
        Hexagon newRock = TestBoard.getHexagon(new Coordinate(99,101));
        Hexagon newBeach = TestBoard.getHexagon(new Coordinate(100,101));

        assertEquals(2, newVolcano.getLevel());
        assertEquals(2, newRock.getLevel());
        assertEquals(2, newBeach.getLevel());

        assertEquals(Terrain.VOLCANO, newVolcano.getTerrain());
        assertEquals(Terrain.ROCK, newRock.getTerrain());
        assertEquals(Terrain.BEACH, newBeach.getTerrain());

        assertEquals(Terrain.BEACH, TestBoard.getHexagon(new Coordinate(99,100)).getTerrain());
        assertEquals(Terrain.ROCK, TestBoard.getHexagon(new Coordinate(101,101)).getTerrain());
        assertEquals(Terrain.JUNGLE, TestBoard.getHexagon(new Coordinate(101,100)).getTerrain());

    }

    @Test
    public void overwriteLevel1HexFailDueToOverhang(){
        Board TestBoard = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        TestBoard.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE), DirectionsHex.RIGHT, 100, 101);

        boolean isSuccess = TestBoard.placeTile(new Tile(Terrain.ROCK, Terrain.BEACH), DirectionsHex.RIGHT, 100, 100);
        assertEquals(false, isSuccess);
    }

    @Test
    public void overwriteLevel1HexFailDueToVolcanoMismatch(){
        Board TestBoard = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        TestBoard.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE), DirectionsHex.RIGHT, 97, 101);

        boolean isSuccess = TestBoard.placeTile(new Tile(Terrain.BEACH, Terrain.ROCK), DirectionsHex.UPPERRIGHT, 98, 100);

        assertEquals(false, isSuccess);
    }
}