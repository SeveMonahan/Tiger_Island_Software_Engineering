import TigerIsland.RandomTileBag;
import TigerIsland.Tile;
import TigerIsland.TileBag;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.NoSuchElementException;

public class TileBagTest {
    @Test
    public void drawTileFromBag() {
        TileBag testBag = new RandomTileBag();

        assert( testBag.drawTile() instanceof Tile);
        assertEquals(47, testBag.getNumberOfTilesInBag() );
    }

    @Test
    public void drawAllTilesFromBag() {
        TileBag testBag = new RandomTileBag();

        // Checking if all of the tiles are there would be difficult because duplicates
        // are permitted. Its doable but could be unnecessary...
        assertEquals(48, testBag.getNumberOfTilesInBag() );
    }

    @Test(expected = NoSuchElementException.class)
    public void drawTileFromEmptyBagFails() throws Exception {
        TileBag testBag = new RandomTileBag();

        while( testBag.getNumberOfTilesInBag() != 0 ) {
            testBag.drawTile();
        }

        testBag.drawTile();
    }
}
