package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class ParserTest {
    @Test
    public void getTileFromMakeYourMove(){
        String message = "MAKE YOUR MOVE IN GAME <gid> WITHIN <timemove> SECOND: MOVE <#> PLACE JUNGLE+LAKE";
        Tile expectedTestTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Parser parser = new Parser();
        Tile testTile = parser.makeYourMoveStringToTile(message);
        assertEquals(expectedTestTile.getTerrainsClockwiseFromVolcano(), testTile.getTerrainsClockwiseFromVolcano());
    }
}
