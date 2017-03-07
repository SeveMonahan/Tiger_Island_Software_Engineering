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

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagon(100, 100).getTerrain());

        assertEquals(Terrain.BEACH, TestBoard.getHexagon(99, 100).getTerrain());

        assertEquals(Terrain.GRASS, TestBoard.getHexagon(99, 101).getTerrain());

        assertEquals(1, TestBoard.getHexagon(100, 100).getlevel());

        assertEquals(1, TestBoard.getHexagon(99, 100).getlevel());

        assertEquals(1, TestBoard.getHexagon(99, 101).getlevel());
    }

    @Test
    public void initBoardWithTile() {
        Board TestBoard = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagon(100, 100).getTerrain());

        assertEquals(Terrain.BEACH, TestBoard.getHexagon(99, 100).getTerrain());

        assertEquals(Terrain.GRASS, TestBoard.getHexagon(99, 101).getTerrain());

        assertEquals(1, TestBoard.getHexagon(100, 100).getlevel());

        assertEquals(1, TestBoard.getHexagon(99, 100).getlevel());

        assertEquals(1, TestBoard.getHexagon(99, 101).getlevel());
    }

    @Test
    public void placeTileWithRestrictionsLevel0Success(){
       Board TestBoard = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));

       boolean success = TestBoard.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE),
               DirectionsHex.LOWERLEFT, 98, 100);

        assertEquals(Terrain.VOLCANO, TestBoard.getHexagon(98, 100).getTerrain());

        assertEquals(Terrain.ROCK, TestBoard.getHexagon(97, 99).getTerrain());

        assertEquals(Terrain.JUNGLE, TestBoard.getHexagon(97, 100).getTerrain());

        assertEquals(1, TestBoard.getHexagon(98, 100).getlevel());

        assertEquals(1, TestBoard.getHexagon(97, 99).getlevel());

        assertEquals(1, TestBoard.getHexagon(97, 100).getlevel());

        assertEquals(true, success);
    }

    @Test
    public void placeTileWithRestrictionsLevel0Fail(){
        Board TestBoard = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));

        boolean success = TestBoard.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE),
                DirectionsHex.LOWERLEFT, 98, 50);

        assertEquals(Terrain.EMPTY, TestBoard.getHexagon(98, 50).getTerrain());

        assertEquals(Terrain.EMPTY, TestBoard.getHexagon(97, 49).getTerrain());

        assertEquals(Terrain.EMPTY, TestBoard.getHexagon(97, 50).getTerrain());

        assertEquals(0, TestBoard.getHexagon(98, 50).getlevel());

        assertEquals(0, TestBoard.getHexagon(97, 49).getlevel());

        assertEquals(0, TestBoard.getHexagon(97, 50).getlevel());

        assertEquals(false, success);
    }
}
