package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileMoveTest
{
    @Test
    public void adjacentTilePlacementShouldPass() {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.LEFT, new Coordinate(99, 100));

        boolean success = board.placeTile(tileMove);

        assertEquals(true, success);

        Coordinate TestCoordinate1 = new Coordinate(99,100);
        Coordinate TestCoordinate2 = new Coordinate(98,100);
        Coordinate TestCoordinate3 = new Coordinate(98,101);

        assertEquals(Terrain.VOLCANO, board.getHexagonAt(TestCoordinate1).getTerrain());
        assertEquals(Terrain.ROCK, board.getHexagonAt(TestCoordinate2).getTerrain());
        assertEquals(Terrain.JUNGLE, board.getHexagonAt(TestCoordinate3).getTerrain());

        assertEquals(1, board.getHexagonAt(TestCoordinate1).getLevel());
        assertEquals(1, board.getHexagonAt(TestCoordinate2).getLevel());
        assertEquals(1, board.getHexagonAt(TestCoordinate3).getLevel());
    }

    @Test
    public void nonAdjacentTilePlacementShouldFail() {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.LOWERLEFT, new Coordinate(98, 50));

        boolean success = board.placeTile(tileMove);

        assertEquals(false, success);

        Coordinate TestCoordinate1 = new Coordinate(98,50);
        Coordinate TestCoordinate2 = new Coordinate(97,49);
        Coordinate TestCoordinate3 = new Coordinate(97,50);

        assertEquals(Terrain.EMPTY, board.getHexagonAt(TestCoordinate1).getTerrain());
        assertEquals(Terrain.EMPTY, board.getHexagonAt(TestCoordinate2).getTerrain());
        assertEquals(Terrain.EMPTY, board.getHexagonAt(TestCoordinate3).getTerrain());

        assertEquals(0, board.getHexagonAt(TestCoordinate1).getLevel());
        assertEquals(0, board.getHexagonAt(TestCoordinate2).getLevel());
        assertEquals(0, board.getHexagonAt(TestCoordinate3).getLevel());
    }

    @Test
    public void invalidPlacementDueToNotEnoughOpenGapsTest(){
        Board board = new Board();
        Player player = new Player(Color.BLACK);

        Tile tile_01 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        Tile tile_02 = new Tile(Terrain.GRASS, Terrain.LAKE);

        TileMove tileMove_01 = new TileMove(tile_01, HexagonNeighborDirection.RIGHT, new Coordinate(97, 101));
        TileMove tileMove_02 = new TileMove(tile_02, HexagonNeighborDirection.LEFT, new Coordinate(99, 100));

        //check if first move was valid (leaves a gap at 99,100)
        board.placeTile(tileMove_01);
        Coordinate TestCoordinate1 = new Coordinate(97,101);
        Coordinate TestCoordinate2 = new Coordinate(98,101);
        Coordinate TestCoordinate3 = new Coordinate(98,100);
        assertEquals(Terrain.VOLCANO, board.getHexagonAt(TestCoordinate1).getTerrain());
        assertEquals(Terrain.ROCK, board.getHexagonAt(TestCoordinate2).getTerrain());
        assertEquals(Terrain.JUNGLE, board.getHexagonAt(TestCoordinate3).getTerrain());
        boolean success = board.placeTile(tileMove_01);
        assertEquals(true, success);

        //check if second tile move is valid(it should not be, even though there is a gap, tile will have overlap)
        board.placeTile(tileMove_02);
        Coordinate TestCoordinate4 = new Coordinate(99,100);
        Coordinate TestCoordinate5 = new Coordinate(98,100);
        Coordinate TestCoordinate6 = new Coordinate(98,101);
        assertEquals(Terrain.VOLCANO, board.getHexagonAt(TestCoordinate4).getTerrain());
        assertEquals(Terrain.GRASS, board.getHexagonAt(TestCoordinate5).getTerrain());
        assertEquals(Terrain.LAKE, board.getHexagonAt(TestCoordinate6).getTerrain());
        boolean isPlacingOverTileOne = board.placeTile(tileMove_02);
        assertEquals(true, isPlacingOverTileOne);

    }

    @Test
    public void overwriteLevelOneShouldSucceedWhenValid() {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        TileMove tileMove1 = new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100));

        board.placeTile(tileMove1);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.LAKE);
        TileMove tileMove2 = new TileMove(tile2, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(99, 100));

        boolean isSuccess = board.placeTile(tileMove2);
        assertEquals(true, isSuccess);
    }

    @Test
    public void overwriteLevelOneShouldUpdateTerrainTypes() {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        TileMove tileMove1 = new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100));

        board.placeTile(tileMove1);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.LAKE);
        TileMove tileMove2 = new TileMove(tile2, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(99, 100));

        board.placeTile(tileMove2);

        Coordinate TestCoordinate1 = new Coordinate(99,100);
        Coordinate TestCoordinate2 = new Coordinate(99,101);
        Coordinate TestCoordinate3 = new Coordinate(100,100);

        Hexagon newVolcanoHexagon = board.getHexagonAt(TestCoordinate1);
        Hexagon newNeighborOneHexagon = board.getHexagonAt(TestCoordinate2);
        Hexagon newNeighborTwoHexagon = board.getHexagonAt(TestCoordinate3);

        assertEquals(Terrain.VOLCANO, newVolcanoHexagon.getTerrain());
        assertEquals(Terrain.ROCK, newNeighborOneHexagon.getTerrain());
        assertEquals(Terrain.LAKE, newNeighborTwoHexagon.getTerrain());

        // Make sure that the other hexagons that weren't overlapped didn't get modified.
        Hexagon untouchedHexagonOne = board.getHexagonAt(new Coordinate(98, 100));
        Hexagon untouchedHexagonTwo = board.getHexagonAt(new Coordinate(98, 101));
        Hexagon untouchedHexagonThree = board.getHexagonAt(new Coordinate(100,101));
        Hexagon untouchedHexagonFour = board.getHexagonAt(new Coordinate(99,99));
        Hexagon untouchedHexagonFive = board.getHexagonAt(new Coordinate(100,99));

        assertEquals(Terrain.ROCK, untouchedHexagonOne.getTerrain());
        assertEquals(Terrain.JUNGLE, untouchedHexagonTwo.getTerrain());
        assertEquals(Terrain.LAKE, untouchedHexagonThree.getTerrain());
        assertEquals(Terrain.ROCK, untouchedHexagonFour.getTerrain());
        assertEquals(Terrain.GRASS, untouchedHexagonFive.getTerrain());
    }

    @Test
    public void overwriteLevelOneShouldUpdateLevels() {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        TileMove tileMove1 = new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100));

        board.placeTile(tileMove1);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.LAKE);
        TileMove tileMove2 = new TileMove(tile2, HexagonNeighborDirection.UPPERRIGHT, new Coordinate(99, 100));

        board.placeTile(tileMove2);

        Coordinate TestCoordinate1 = new Coordinate(99,100);
        Coordinate TestCoordinate2 = new Coordinate(99,101);
        Coordinate TestCoordinate3 = new Coordinate(100,100);

        Hexagon newVolcanoHexagon = board.getHexagonAt(TestCoordinate1);
        Hexagon newNeighborOneHexagon = board.getHexagonAt(TestCoordinate2);
        Hexagon newNeighborTwoHexagon = board.getHexagonAt(TestCoordinate3);

        assertEquals(2, newVolcanoHexagon.getLevel());
        assertEquals(2, newNeighborOneHexagon.getLevel());
        assertEquals(2, newNeighborTwoHexagon.getLevel());

        // Make sure that the other hexagons that weren't overlapped didn't get modified.
        Hexagon untouchedHexagonOne = board.getHexagonAt(new Coordinate(98, 100));
        Hexagon untouchedHexagonTwo = board.getHexagonAt(new Coordinate(98, 101));
        Hexagon untouchedHexagonThree = board.getHexagonAt(new Coordinate(100,101));
        Hexagon untouchedHexagonFour = board.getHexagonAt(new Coordinate(99,99));
        Hexagon untouchedHexagonFive = board.getHexagonAt(new Coordinate(100,99));

        assertEquals(1, untouchedHexagonOne.getLevel());
        assertEquals(1, untouchedHexagonTwo.getLevel());
        assertEquals(1, untouchedHexagonThree.getLevel());
        assertEquals(1, untouchedHexagonFour.getLevel());
        assertEquals(1, untouchedHexagonFive.getLevel());
    }

    @Test
    public void overwriteLevelOneShouldFailDueToOverhang() {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        TileMove tileMove1 = new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100));

        board.placeTile(tileMove1);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.LAKE);
        TileMove tileMove2 = new TileMove(tile2, HexagonNeighborDirection.LOWERRIGHT, new Coordinate(99, 100));

        boolean isSuccess = board.placeTile(tileMove2);

        assertEquals(false, isSuccess);

        // Make sure that the hexagons where the tile would've been placed where not modified.
        Coordinate TestCoordinate1 = new Coordinate(99,100);
        Coordinate TestCoordinate2 = new Coordinate(99,99);
        Coordinate TestCoordinate3 = new Coordinate(98,99);

        Hexagon newVolcanoHexagon = board.getHexagonAt(TestCoordinate1);
        Hexagon newNeighborOneHexagon = board.getHexagonAt(TestCoordinate2);
        Hexagon newNeighborTwoHexagon = board.getHexagonAt(TestCoordinate3);

        assertEquals(Terrain.VOLCANO, newVolcanoHexagon.getTerrain());
        assertEquals(Terrain.ROCK, newNeighborOneHexagon.getTerrain());
        assertEquals(Terrain.EMPTY, newNeighborTwoHexagon.getTerrain());

        assertEquals(1, newVolcanoHexagon.getLevel());
        assertEquals(1, newNeighborOneHexagon.getLevel());
        assertEquals(0, newNeighborTwoHexagon.getLevel());
    }

    @Test
    public void overwriteLevelOneShouldFailDueToVolcanoMismatch() {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile1 = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        TileMove tileMove1 = new TileMove(tile1, HexagonNeighborDirection.LEFT, new Coordinate(99, 100));
        board.placeTile(tileMove1);

        Tile tile2 = new Tile(Terrain.ROCK, Terrain.LAKE);
        TileMove tileMove2 = new TileMove(tile2, HexagonNeighborDirection.UPPERLEFT, new Coordinate(99, 99));

        boolean isSuccess = board.placeTile(tileMove2);

        assertEquals(false, isSuccess);

        // Make sure that the hexagons where the tile would've been placed where not modified.
        Coordinate TestCoordinate1 = new Coordinate(99,99);
        Coordinate TestCoordinate2 = new Coordinate(99,100);
        Coordinate TestCoordinate3 = new Coordinate(100,100);

        Hexagon newVolcanoHexagon = board.getHexagonAt(TestCoordinate1);
        Hexagon newNeighborOneHexagon = board.getHexagonAt(TestCoordinate2);
        Hexagon newNeighborTwoHexagon = board.getHexagonAt(TestCoordinate3);

        assertEquals(Terrain.ROCK, newVolcanoHexagon.getTerrain());
        assertEquals(Terrain.VOLCANO, newNeighborOneHexagon.getTerrain());
        assertEquals(Terrain.VOLCANO, newNeighborTwoHexagon.getTerrain());

        assertEquals(1, newVolcanoHexagon.getLevel());
        assertEquals(1, newNeighborOneHexagon.getLevel());
        assertEquals(1, newNeighborTwoHexagon.getLevel());
    }

    @Test
    public void overwriteLevel1HexFailDueToExactTileOverlap() {
        Board board = new Board();
        board.placeStartingTile();

        Tile tile = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        TileMove tileMove = new TileMove(tile, HexagonNeighborDirection.UPPERLEFT, new Coordinate(100, 100));

        boolean isSuccess = board.placeTile(tileMove);

        assertEquals(false, isSuccess);

        // Make sure that the hexagons where the tile would've been placed where not modified.
        Coordinate TestCoordinate1 = new Coordinate(100,100);
        Coordinate TestCoordinate2 = new Coordinate(99,101);
        Coordinate TestCoordinate3 = new Coordinate(100,101);

        Hexagon newVolcanoHexagon = board.getHexagonAt(TestCoordinate1);
        Hexagon newNeighborOneHexagon = board.getHexagonAt(TestCoordinate2);
        Hexagon newNeighborTwoHexagon = board.getHexagonAt(TestCoordinate3);

        assertEquals(Terrain.VOLCANO, newVolcanoHexagon.getTerrain());
        assertEquals(Terrain.JUNGLE, newNeighborOneHexagon.getTerrain());
        assertEquals(Terrain.LAKE, newNeighborTwoHexagon.getTerrain());

        assertEquals(1, newVolcanoHexagon.getLevel());
        assertEquals(1, newNeighborOneHexagon.getLevel());
        assertEquals(1, newNeighborTwoHexagon.getLevel());
    }
}