import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by jayC on 3/8/17.
 */
public class MeeplesTest {
    @Test
    public void meeplesCount() throws Exception {
        Meeples myMeeples = new Meeples();
        assertEquals(20, myMeeples.getCount());
    }

    @Test
    public void populationTest() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.BEACH);
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);

        Meeples myMeeples = new Meeples();
        Player myPlayer = new Player(myMeeples);
        myMeeples.populateHex(TestHexagon);

        assertEquals(3, TestHexagon.getLevel());

    }
}
