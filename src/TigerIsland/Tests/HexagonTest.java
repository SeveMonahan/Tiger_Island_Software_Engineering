package TigerIsland.Tests;

import TigerIsland.Hexagon;
import TigerIsland.Terrain;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class HexagonTest {
    @Test
    public void getlevel() throws Exception{
        Hexagon TestHexagon = new Hexagon();
        assertEquals(0, TestHexagon.getLevel());
    }

    @Test
    public void changeTerrainTypeThoughExplosion() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.LAKE);
        assertEquals(1, TestHexagon.getLevel());
        assertEquals(Terrain.LAKE, TestHexagon.getTerrain());
    }

    @Test
    public void isVolcanoTest() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.VOLCANO);
        assertEquals(true, TestHexagon.isVolcanoHex());
    }
}