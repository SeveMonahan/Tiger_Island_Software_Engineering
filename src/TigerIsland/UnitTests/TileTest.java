import TigerIsland.Terrain;
import TigerIsland.Tile;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TileTest {
    @Test
    public void getTerrainsClockwiseFromVolcano(){
        Tile TestTile = new Tile(Terrain.JUNGLE, Terrain.ROCK);

        Terrain[] result = TestTile.getTerrainsClockwiseFromVolcano();

        assertEquals(Terrain.VOLCANO, result[0]);
        assertEquals(Terrain.JUNGLE, result[1]);
        assertEquals(Terrain.ROCK, result[2]);
    }

    @Test
    public void getTerrainsClockwiseFromVolcanoReverse(){
        Tile TestTile = new Tile(Terrain.ROCK, Terrain.JUNGLE);

        Terrain[] result = TestTile.getTerrainsClockwiseFromVolcano();

        assertEquals(Terrain.VOLCANO, result[0]);
        assertEquals(Terrain.ROCK, result[1]);
        assertEquals(Terrain.JUNGLE, result[2]);


    }


}
