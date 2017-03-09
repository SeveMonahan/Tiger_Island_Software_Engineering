import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by jayC on 3/8/17.
 */
public class MeeplesTest {
    @Test
    public void meeplesCountTest() throws Exception {
        Meeples myMeeples = new Meeples();
        assertEquals(20, myMeeples.getCount());
    }

    @Test
    public void populationLevelTest() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.BEACH);

        Meeples myMeeples = new Meeples();
        Player myPlayer = new Player(myMeeples);
        myMeeples.populateHex(TestHexagon);

        assertEquals(1, TestHexagon.getLevel());
    }
    @Test
    public void checkLevelTest() throws Exception {
        Hexagon TestHexagon = new Hexagon();

        Meeples myMeeples = new Meeples();
        Player myPlayer = new Player(myMeeples);
        myMeeples.populateHex(TestHexagon);

        assertEquals(false, myMeeples.checkLevel(TestHexagon));
    }

    @Test
    public void populationLevel3Test() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.BEACH);
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);

        Meeples myMeeples = new Meeples();
        Player myPlayer = new Player(myMeeples);
        myMeeples.populateHex(TestHexagon);

        assertEquals(3, TestHexagon.getLevel());

    }

    @Test
    public void meepleCountAfterPopulateTest() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.BEACH);
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.GRASS);

        Meeples myMeeples = new Meeples();
        Player myPlayer = new Player(myMeeples);
        myMeeples.populateHex(TestHexagon);

        assertEquals(17, myMeeples.getCount());

    }

    @Test
    public void populateVolcano() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.VOLCANO);

        Meeples myMeeples = new Meeples();
        Player myPlayer = new Player(myMeeples);
        myMeeples.populateHex(TestHexagon);

        assertEquals(20, myMeeples.getCount());
        assertEquals(0, TestHexagon.getPopulation());

    }

    @Test
    public void nukeTest() throws Exception {
        Hexagon TestHexagon = new Hexagon();
        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.BEACH);

        Meeples myMeeples = new Meeples();
        Player myPlayer = new Player(myMeeples);
        myMeeples.populateHex(TestHexagon);

        myMeeples.beforeNewLevel(TestHexagon,Terrain.BEACH);

        assertEquals(2, TestHexagon.getPopulation());

    }

}
