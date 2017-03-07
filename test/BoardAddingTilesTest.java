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
}
