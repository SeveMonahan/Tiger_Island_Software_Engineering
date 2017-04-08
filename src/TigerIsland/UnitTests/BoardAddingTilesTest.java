import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BoardAddingTilesTest {
    @Test
    public void ConstructorUsingTile(){
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.JUNGLE, Terrain.ROCK), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board TestBoard = boardWithTile;
    }

    @Test
    public void placeTileNoRestrictions() {
        Board TestBoard = new Board();
        TileMove testTileMove = new TileMove ( new Tile(Terrain.LAKE, Terrain.GRASS),
                                            HexagonNeighborDirection.LEFT, new Coordinate(100,100) );

        TestBoard.placeTile(testTileMove);

        Coordinate TestCoordinate1 = new Coordinate(100,100);
        Coordinate TestCoordinate2 = new Coordinate(99,100);
        Coordinate TestCoordinate3 = new Coordinate(99,101);

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagonAt( TestCoordinate1).getTerrain());

        assertEquals(Terrain.LAKE, TestBoard.getHexagonAt( TestCoordinate2).getTerrain());

        assertEquals(Terrain.GRASS, TestBoard.getHexagonAt( TestCoordinate3).getTerrain());

        assertEquals(1, TestBoard.getHexagonAt( TestCoordinate1).getLevel());

        assertEquals(1, TestBoard.getHexagonAt( TestCoordinate2).getLevel());

        assertEquals(1, TestBoard.getHexagonAt( TestCoordinate3).getLevel());
    }

    @Test
    public void initBoardWithTile() {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board TestBoard = boardWithTile;

        Coordinate TestCoordinate1 = new Coordinate(100,100);
        Coordinate TestCoordinate2 = new Coordinate(99,100);
        Coordinate TestCoordinate3 = new Coordinate(99,101);

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagonAt( TestCoordinate1).getTerrain());

        assertEquals(Terrain.LAKE, TestBoard.getHexagonAt( TestCoordinate2).getTerrain());

        assertEquals(Terrain.GRASS, TestBoard.getHexagonAt( TestCoordinate3).getTerrain());

        assertEquals(1, TestBoard.getHexagonAt(TestCoordinate1).getLevel());

        assertEquals(1, TestBoard.getHexagonAt(TestCoordinate2).getLevel());

        assertEquals(1, TestBoard.getHexagonAt(TestCoordinate3).getLevel());
    }

    @Test
    public void placeTileWithRestrictionsLevel0Success(){
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board TestBoard = boardWithTile;

        Coordinate TestCoordinate1 = new Coordinate(98,100);
        Coordinate TestCoordinate2 = new Coordinate(97,99);
        Coordinate TestCoordinate3 = new Coordinate(97,100);

       boolean success = TestBoard.placeTile(
               new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.LOWERLEFT, new Coordinate(98, 100)));

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagonAt(TestCoordinate1).getTerrain());

        assertEquals(Terrain.ROCK, TestBoard.getHexagonAt(TestCoordinate2).getTerrain());

        assertEquals(Terrain.JUNGLE, TestBoard.getHexagonAt(TestCoordinate3).getTerrain());

        assertEquals(1, TestBoard.getHexagonAt(TestCoordinate1).getLevel());

        assertEquals(1, TestBoard.getHexagonAt(TestCoordinate2).getLevel());

        assertEquals(1, TestBoard.getHexagonAt(TestCoordinate3).getLevel());

        assertEquals(true, success);
    }

    @Test
    public void placeTileWithRestrictionsLevel0Fail(){
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board TestBoard = boardWithTile;

        Coordinate TestCoordinate1 = new Coordinate(98,50);
        Coordinate TestCoordinate2 = new Coordinate(97,49);
        Coordinate TestCoordinate3 = new Coordinate(97,50);

        boolean success = TestBoard.placeTile(
                new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.LOWERLEFT, new Coordinate(98, 50)));

        assertEquals(Terrain.EMPTY, TestBoard.getHexagonAt(TestCoordinate1).getTerrain());

        assertEquals(Terrain.EMPTY, TestBoard.getHexagonAt(TestCoordinate2).getTerrain());

        assertEquals(Terrain.EMPTY, TestBoard.getHexagonAt(TestCoordinate3).getTerrain());

        assertEquals(0, TestBoard.getHexagonAt(TestCoordinate1).getLevel());

        assertEquals(0, TestBoard.getHexagonAt(TestCoordinate2).getLevel());

        assertEquals(0, TestBoard.getHexagonAt(TestCoordinate3).getLevel());

        assertEquals(false, success);
    }

    @Test
    public void overwriteLevel1Hex(){
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board TestBoard = boardWithTile;
        TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));

        boolean isSuccess = TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.LAKE), HexagonNeighborDirection.UPPERLEFT, new Coordinate(100, 100)));
        assertEquals(true, isSuccess);

        Hexagon newVolcano = TestBoard.getHexagonAt(new Coordinate(100,100));
        Hexagon newRock = TestBoard.getHexagonAt(new Coordinate(99,101));
        Hexagon newBeach = TestBoard.getHexagonAt(new Coordinate(100,101));

        assertEquals(2, newVolcano.getLevel());
        assertEquals(2, newRock.getLevel());
        assertEquals(2, newBeach.getLevel());

        assertEquals(Terrain.VOLCANO, newVolcano.getTerrain());
        assertEquals(Terrain.ROCK, newRock.getTerrain());
        assertEquals(Terrain.LAKE, newBeach.getTerrain());

        assertEquals(Terrain.LAKE, TestBoard.getHexagonAt(new Coordinate(99,100)).getTerrain());
        assertEquals(Terrain.ROCK, TestBoard.getHexagonAt(new Coordinate(101,101)).getTerrain());
        assertEquals(Terrain.JUNGLE, TestBoard.getHexagonAt(new Coordinate(101,100)).getTerrain());

    }

    @Test
    public void overwriteLevel1HexFailDueToOverhang(){
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board TestBoard = boardWithTile;
        TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));

        boolean isSuccess = TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.LAKE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 100)));
        assertEquals(false, isSuccess);
    }

    @Test
    public void overwriteLevel1HexFailDueToVolcanoMismatch(){
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASS), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board TestBoard = boardWithTile;
        TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(97, 101)));

        boolean isSuccess = TestBoard.placeTile(new TileMove(new Tile(Terrain.LAKE, Terrain.ROCK), HexagonNeighborDirection.UPPERRIGHT, new Coordinate(98, 100)));

        assertEquals(false, isSuccess);
    }

    @Test
    public void overwriteLevel1HexFailDueToExactTileOverlap(){
        Tile testTile = new Tile(Terrain.LAKE, Terrain.GRASS);
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(testTile, HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board testBoard = boardWithTile;

        boolean isSuccess = testBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.LEFT, new Coordinate(100, 100)));

        assertEquals(false, isSuccess);
    }
}