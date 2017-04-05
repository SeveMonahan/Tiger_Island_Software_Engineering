package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class ParserTest {
    @Test
    public void getTileFromMakeYourMoveString(){
        String message = "MAKE YOUR MOVE IN GAME <gid> WITHIN <timemove> SECOND: MOVE <#> PLACE JUNGLE+LAKE";
        Tile expectedTestTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Parser parser = new Parser();
        Tile testTile = parser.makeYourMoveStringToTile(message);
        assertEquals(expectedTestTile.getTerrainsClockwiseFromVolcano(), testTile.getTerrainsClockwiseFromVolcano());
    }

    @Test
    public void getTileMoveFromGameMoveMadeString(){
        String message = "GAME <gid> MOVE <#> PLAYER <pid> PLACED JUNGLE+LAKE AT 1 3 0 1 FOUNDED SETTLEMENT AT <x> <y> <z>";
        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedCoordinate = new Coordinate(1, 3, 0);
        TileMove expectedTileMove = new TileMove(expectedTile, HexagonNeighborDirection.UPPERLEFT, expectedCoordinate);

        Parser parser = new Parser();
        TileMove testTileMove = parser.opponentMoveStringToTileMove(message);

        assertEquals(expectedTileMove.getTile().getTerrainsClockwiseFromVolcano(), testTileMove.getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedTileMove.getDirection(), testTileMove.getDirection());
        assertEquals(expectedTileMove.getCoordinate().getX(), testTileMove.getCoordinate().getX());
        assertEquals(expectedTileMove.getCoordinate().getY(), testTileMove.getCoordinate().getY());


    }
}
