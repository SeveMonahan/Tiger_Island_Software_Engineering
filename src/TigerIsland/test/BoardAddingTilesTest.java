package TigerIsland.test;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardAddingTilesTest {
    @Test
    public void ConstructorUsingTile(){
        Board TestBoard = new Board(new Tile(Terrain.JUNGLE, Terrain.ROCK));
    }

    @Test
    public void placeTileNoRestrictions() {
        Board TestBoard = new Board();
        TileMove testTileMove = new TileMove ( new Tile(Terrain.LAKE, Terrain.GRASSLAND),
                                            HexagonNeighborDirection.LEFT, new Coordinate(100,100) );

        TestBoard.placeTileNoRestrictions(testTileMove);

        Coordinate TestCoordinate1 = new Coordinate(100,100);
        Coordinate TestCoordinate2 = new Coordinate(99,100);
        Coordinate TestCoordinate3 = new Coordinate(99,101);

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagon( TestCoordinate1).getTerrain());

        assertEquals(Terrain.LAKE, TestBoard.getHexagon( TestCoordinate2).getTerrain());

        assertEquals(Terrain.GRASSLAND, TestBoard.getHexagon( TestCoordinate3).getTerrain());

        assertEquals(1, TestBoard.getHexagon( TestCoordinate1).getLevel());

        assertEquals(1, TestBoard.getHexagon( TestCoordinate2).getLevel());

        assertEquals(1, TestBoard.getHexagon( TestCoordinate3).getLevel());
    }

    @Test
    public void initBoardWithTile() {
        Board TestBoard = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));

        Coordinate TestCoordinate1 = new Coordinate(100,100);
        Coordinate TestCoordinate2 = new Coordinate(99,100);
        Coordinate TestCoordinate3 = new Coordinate(99,101);

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagon( TestCoordinate1).getTerrain());

        assertEquals(Terrain.LAKE, TestBoard.getHexagon( TestCoordinate2).getTerrain());

        assertEquals(Terrain.GRASSLAND, TestBoard.getHexagon( TestCoordinate3).getTerrain());

        assertEquals(1, TestBoard.getHexagon(TestCoordinate1).getLevel());

        assertEquals(1, TestBoard.getHexagon(TestCoordinate2).getLevel());

        assertEquals(1, TestBoard.getHexagon(TestCoordinate3).getLevel());
    }

    @Test
    public void placeTileWithRestrictionsLevel0Success(){
       Board TestBoard = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));

        Coordinate TestCoordinate1 = new Coordinate(98,100);
        Coordinate TestCoordinate2 = new Coordinate(97,99);
        Coordinate TestCoordinate3 = new Coordinate(97,100);

       boolean success = TestBoard.placeTile(
               new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.LOWERLEFT, new Coordinate(98, 100)));

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
        Board TestBoard = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));

        Coordinate TestCoordinate1 = new Coordinate(98,50);
        Coordinate TestCoordinate2 = new Coordinate(97,49);
        Coordinate TestCoordinate3 = new Coordinate(97,50);

        boolean success = TestBoard.placeTile(
                new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.LOWERLEFT, new Coordinate(98, 50)));

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
        Board TestBoard = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));

        boolean isSuccess = TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.LAKE), HexagonNeighborDirection.UPPERLEFT, new Coordinate(100, 100)));
        assertEquals(true, isSuccess);

        Hexagon newVolcano = TestBoard.getHexagon(new Coordinate(100,100));
        Hexagon newRock = TestBoard.getHexagon(new Coordinate(99,101));
        Hexagon newBeach = TestBoard.getHexagon(new Coordinate(100,101));

        assertEquals(2, newVolcano.getLevel());
        assertEquals(2, newRock.getLevel());
        assertEquals(2, newBeach.getLevel());

        assertEquals(Terrain.VOLCANO, newVolcano.getTerrain());
        assertEquals(Terrain.ROCK, newRock.getTerrain());
        assertEquals(Terrain.LAKE, newBeach.getTerrain());

        assertEquals(Terrain.LAKE, TestBoard.getHexagon(new Coordinate(99,100)).getTerrain());
        assertEquals(Terrain.ROCK, TestBoard.getHexagon(new Coordinate(101,101)).getTerrain());
        assertEquals(Terrain.JUNGLE, TestBoard.getHexagon(new Coordinate(101,100)).getTerrain());

    }

    @Test
    public void overwriteLevel1HexFailDueToOverhang(){
        Board TestBoard = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));

        boolean isSuccess = TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.LAKE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 100)));
        assertEquals(false, isSuccess);
    }

    @Test
    public void overwriteLevel1HexFailDueToVolcanoMismatch(){
        Board TestBoard = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(97, 101)));

        boolean isSuccess = TestBoard.placeTile(new TileMove(new Tile(Terrain.LAKE, Terrain.ROCK), HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 100)));

        assertEquals(false, isSuccess);
    }

    @Test
    public void overwriteLevel1HexFailDueToExactTileOverlap(){
        Tile testTile = new Tile(Terrain.LAKE, Terrain.GRASSLAND);
        Board testBoard = new Board( testTile );

        boolean isSuccess = testBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.LEFT, new Coordinate(100, 100)));

        assertEquals(false, isSuccess);
    }
}