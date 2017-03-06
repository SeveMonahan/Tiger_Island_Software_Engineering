import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HexagonTest {
    @Test
    public void getlevel() throws Exception{
        Hexagon TestHexagon = new Hexagon();
        assertEquals(0, TestHexagon.getlevel());
    }

    @Test
    public void changeTerrainTypeThoughExplosion() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.BEACH);
        assertEquals(1, TestHexagon.getlevel());
        assertEquals(Terrain.BEACH, TestHexagon.getTerrain());
    }
}