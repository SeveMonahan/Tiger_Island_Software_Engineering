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
        String message = "GAME <gid> MOVE <#> PLAYER <pid> PLACED JUNGLE+LAKE AT 0 3 -3 1 FOUNDED SETTLEMENT AT <x> <y> <z>";
        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedCoordinate = new Coordinate(0, 3, -3);
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
        ConstructionMoveTransmission expectedConstructionMoveTransmission = new ConstructionMoveTransmission(expectedBuildOption, expectedCoordinate);

        Parser parser = new Parser();
        ConstructionMoveTransmission testConstructionMoveTransmission = parser.opponentMoveStringToBuildMove(message);

        assertEquals(expectedConstructionMoveTransmission.getCoordinate().getX(), testConstructionMoveTransmission.getCoordinate().getX());
        assertEquals(expectedConstructionMoveTransmission.getCoordinate().getY(), testConstructionMoveTransmission.getCoordinate().getY());
        assertEquals(expectedConstructionMoveTransmission.getBuildOption(), testConstructionMoveTransmission.getBuildOption());
    }

    //Expanded
    @Test
    public void getBuildMoveFromGameMoveMadeStringForExpandedSettlement(){
        String message = "GAME <gid> MOVE <#> PLAYER <pid> PLACED <tile> AT <x> <y> <z> <orientation> EXPANDED SETTLEMENT AT 0 3 -3 JUNGLE";

        BuildOption expectedBuildOption = BuildOption.EXPANDSETTLEMENT;
        Coordinate expectedCoordinate = new Coordinate(0, 3, -3);
        Terrain expectedTerrain = Terrain.JUNGLE;
        ExpandSettlementMoveTransmission expectedConstructionMoveTransmission = new ExpandSettlementMoveTransmission(expectedBuildOption, expectedCoordinate, expectedTerrain);

        Parser parser = new Parser();
        ConstructionMoveTransmission testConstructionMoveTransmission = parser.opponentMoveStringToBuildMove(message);

        assertEquals(true, testConstructionMoveTransmission instanceof ExpandSettlementMoveTransmission);

        ExpandSettlementMoveTransmission testResult = (ExpandSettlementMoveTransmission) testConstructionMoveTransmission;

        assertEquals(expectedConstructionMoveTransmission.getCoordinate().getX(), testResult.getCoordinate().getX());
        assertEquals(expectedConstructionMoveTransmission.getCoordinate().getY(), testResult.getCoordinate().getY());
        assertEquals(expectedConstructionMoveTransmission.getBuildOption(), testResult.getBuildOption());
        assertEquals(expectedConstructionMoveTransmission.getTerrain(), testResult.getTerrain());
    }

    //Built Totoro Sanctuary
    @Test
    public void getBuildMoveFromGameMoveMadeStringForBuiltTotoroSanctuary(){
        String message = "GAME <gid> MOVE <#> PLAYER <pid> PLACED <tile> AT <x> <y> <z> <orientation> BUILT TOTORO SANCTUARY AT 0 3 -3";

        BuildOption expectedBuildOption = BuildOption.BUILDTOTORO;
        Coordinate expectedCoordinate = new Coordinate(0, 3, -3);
        ConstructionMoveTransmission expectedConstructionMoveTransmission = new ConstructionMoveTransmission(expectedBuildOption, expectedCoordinate);

        Parser parser = new Parser();
        ConstructionMoveTransmission testConstructionMoveTransmission = parser.opponentMoveStringToBuildMove(message);

        assertEquals(expectedConstructionMoveTransmission.getCoordinate().getX(), testConstructionMoveTransmission.getCoordinate().getX());
        assertEquals(expectedConstructionMoveTransmission.getCoordinate().getY(), testConstructionMoveTransmission.getCoordinate().getY());
        assertEquals(expectedConstructionMoveTransmission.getBuildOption(), testConstructionMoveTransmission.getBuildOption());
    }

    //Built Tiger Playground
    @Test
    public void getBuildMoveFromGameMoveMadeStringForBuiltTigerPlayground(){
        String message = "GAME <gid> MOVE <#> PLAYER <pid> PLACED <tile> AT <x> <y> <z> <orientation> BUILT TIGER PLAYGROUND AT 0 3 -3";

        BuildOption expectedBuildOption = BuildOption.BUILDTIGER;
        Coordinate expectedCoordinate = new Coordinate(0, 3, -3);
        ConstructionMoveTransmission expectedConstructionMoveTransmission = new ConstructionMoveTransmission(expectedBuildOption, expectedCoordinate);

        Parser parser = new Parser();
        ConstructionMoveTransmission testConstructionMoveTransmission = parser.opponentMoveStringToBuildMove(message);

        assertEquals(expectedConstructionMoveTransmission.getCoordinate().getX(), testConstructionMoveTransmission.getCoordinate().getX());
        assertEquals(expectedConstructionMoveTransmission.getCoordinate().getY(), testConstructionMoveTransmission.getCoordinate().getY());
        assertEquals(expectedConstructionMoveTransmission.getBuildOption(), testConstructionMoveTransmission.getBuildOption());
    }
}
