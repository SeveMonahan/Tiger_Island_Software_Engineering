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

    //Founded
    @Test
    public void getBuildMoveFromGameMoveMadeStringForFoundedSettlement(){
        String message = "GAME <gid> MOVE <#> PLAYER <pid> PLACED <tile> AT <x> <y> <z> <orientation> FOUNDED SETTLEMENT AT 1 3 0";

        BuildOption expectedBuildOption = BuildOption.FOUNDSETTLEMENT;
        Coordinate expectedCoordinate = new Coordinate(1, 3, 0);
        BuildMove expectedBuildMove = new BuildMove(expectedBuildOption, expectedCoordinate);

        Parser parser = new Parser();
        BuildMove testBuildMove = parser.opponentMoveStringToBuildMove(message);

        assertEquals(expectedBuildMove.getCoordinate().getX(), testBuildMove.getCoordinate().getX());
        assertEquals(expectedBuildMove.getCoordinate().getY(), testBuildMove.getCoordinate().getY());
        assertEquals(expectedBuildMove.getBuildOption(), testBuildMove.getBuildOption());
    }

    //Expanded
    @Test
    public void getBuildMoveFromGameMoveMadeStringForExpandedSettlement(){
        String message = "GAME <gid> MOVE <#> PLAYER <pid> PLACED <tile> AT <x> <y> <z> <orientation> EXPANDED SETTLEMENT AT 1 3 0 JUNGLE";

        BuildOption expectedBuildOption = BuildOption.EXPANDSETTLEMENT;
        Coordinate expectedCoordinate = new Coordinate(1, 3, 0);
        Terrain expectedTerrain = Terrain.JUNGLE;
        BuildMove expectedBuildMove = new BuildMove(expectedBuildOption, expectedCoordinate, expectedTerrain);

        Parser parser = new Parser();
        BuildMove testBuildMove = parser.opponentMoveStringToBuildMove(message);

        assertEquals(expectedBuildMove.getCoordinate().getX(), testBuildMove.getCoordinate().getX());
        assertEquals(expectedBuildMove.getCoordinate().getY(), testBuildMove.getCoordinate().getY());
        assertEquals(expectedBuildMove.getBuildOption(), testBuildMove.getBuildOption());
        assertEquals(expectedBuildMove.getTerrain(), testBuildMove.getTerrain());
    }
}
