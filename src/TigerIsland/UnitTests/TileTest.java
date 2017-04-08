package TigerIsland.UnitTests;

import TigerIsland.Terrain;
import TigerIsland.Tile;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TileTest {
    @Test
    public void getTerrainsClockwiseFromVolcanoTestOne() {
        Tile TestTile = new Tile(Terrain.JUNGLE, Terrain.ROCK);

        Terrain[] terrains = TestTile.getTerrainsClockwiseFromVolcano();

        assertEquals(Terrain.VOLCANO, terrains[0]);
        assertEquals(Terrain.JUNGLE, terrains[1]);
        assertEquals(Terrain.ROCK, terrains[2]);
    }

    @Test
    public void getTerrainsClockwiseFromVolcanoTestTwo() {
        Tile TestTile = new Tile(Terrain.ROCK, Terrain.JUNGLE);

        Terrain[] terrains = TestTile.getTerrainsClockwiseFromVolcano();

        assertEquals(Terrain.VOLCANO, terrains[0]);
        assertEquals(Terrain.ROCK, terrains[1]);
        assertEquals(Terrain.JUNGLE, terrains[2]);
    }

    @Test
    public void tileHashCodeTest() {
        Tile tileOne = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        Tile tileTwo = new Tile(Terrain.ROCK, Terrain.JUNGLE);
        assertEquals(true, tileOne.hashCode() != tileTwo.hashCode());
    }
}