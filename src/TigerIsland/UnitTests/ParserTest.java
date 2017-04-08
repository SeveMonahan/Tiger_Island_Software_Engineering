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

    //Founded
    @Test
    public void getGameMoveIncomingTransmissionFromGameMoveMadeStringForFoundedSettlement(){
        String message = "GAME A MOVE 3 PLAYER myPlayerId PLACED JUNGLE+LAKE AT 0 3 -3 1 FOUNDED SETTLEMENT AT 1 -1 0";

        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedTileCoordinate = new Coordinate(0, 3, -3);
        TileMove expectedTileMove = new TileMove(expectedTile, HexagonNeighborDirection.UPPERLEFT, expectedTileCoordinate);

        BuildOption expectedBuildOption = BuildOption.FOUNDSETTLEMENT;
        Coordinate expectedConstructionCoordinate = new Coordinate(1, -1, 0);
        ConstructionMoveTransmission expectedConstructionMoveTransmission = new ConstructionMoveTransmission(expectedBuildOption, expectedConstructionCoordinate);

        final String testGID = "A";
        final int testMoveNumber = 3;
        final String testPid = "myPlayerId";
        GameMoveIncomingTransmission expectedGameMoveIncomingTransmission = new GameMoveIncomingTransmission(testGID, testMoveNumber, testPid, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        GameMoveIncomingTransmission testGameMoveIncomingTransmission = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getCoordinate().getX(), testGameMoveIncomingTransmission.getTileMove().getCoordinate().getX());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getCoordinate().getY(), testGameMoveIncomingTransmission.getTileMove().getCoordinate().getY());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testGameMoveIncomingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getDirection(), testGameMoveIncomingTransmission.getTileMove().getDirection());

        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getX(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getY(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedGameMoveIncomingTransmission.getGid(), testGameMoveIncomingTransmission.getGid());
        assertEquals(expectedGameMoveIncomingTransmission.getMoveNumber(), testGameMoveIncomingTransmission.getMoveNumber());

    }

    //Expanded
    @Test
    public void getGameMoveIncomingTransmissionFromGameMoveMadeStringForExpandedSettlement(){
        String message = "GAME A MOVE 3 PLAYER myPlayerId PLACED JUNGLE+LAKE AT 0 3 -3 1 EXPANDED SETTLEMENT AT 1 -1 0 JUNGLE";

        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedTileCoordinate = new Coordinate(0, 3, -3);
        TileMove expectedTileMove = new TileMove(expectedTile, HexagonNeighborDirection.UPPERLEFT, expectedTileCoordinate);

        BuildOption expectedBuildOption = BuildOption.EXPANDSETTLEMENT;
        Coordinate expectedConstructionCoordinate = new Coordinate(1, -1, 0);
        ExpandSettlementMoveTransmission expectedExpandSettlementMoveTransmission = new ExpandSettlementMoveTransmission(expectedBuildOption, expectedConstructionCoordinate, Terrain.JUNGLE);

        final String testGID = "A";
        final int testMoveNumber = 3;
        final String testPid = "myPlayerId";
        GameMoveIncomingTransmission expectedGameMoveIncomingTransmission = new GameMoveIncomingTransmission(testGID, testMoveNumber, testPid, expectedTileMove, expectedExpandSettlementMoveTransmission);

        Parser parser = new Parser();
        GameMoveIncomingTransmission testGameMoveIncomingTransmission = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getCoordinate().getX(), testGameMoveIncomingTransmission.getTileMove().getCoordinate().getX());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getCoordinate().getY(), testGameMoveIncomingTransmission.getTileMove().getCoordinate().getY());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testGameMoveIncomingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getDirection(), testGameMoveIncomingTransmission.getTileMove().getDirection());

        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getX(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getY(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedGameMoveIncomingTransmission.getGid(), testGameMoveIncomingTransmission.getGid());
        assertEquals(expectedGameMoveIncomingTransmission.getMoveNumber(), testGameMoveIncomingTransmission.getMoveNumber());

        expectedExpandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) expectedGameMoveIncomingTransmission.getConstructionMoveTransmission();
        ExpandSettlementMoveTransmission testExpandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) testGameMoveIncomingTransmission.getConstructionMoveTransmission();
        assertEquals(expectedExpandSettlementMoveTransmission.getTerrain(), testExpandSettlementMoveTransmission.getTerrain());
    }


    //Built Totoro
    @Test
    public void getGameMoveIncomingTransmissionFromGameMoveMadeStringForBuiltTotoro(){
        String message = "GAME A MOVE 3 PLAYER myPlayerId PLACED JUNGLE+LAKE AT 0 3 -3 1 BUILT TOTORO SANCTUARY AT 1 -1 0";

        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedTileCoordinate = new Coordinate(0, 3, -3);
        TileMove expectedTileMove = new TileMove(expectedTile, HexagonNeighborDirection.UPPERLEFT, expectedTileCoordinate);

        BuildOption expectedBuildOption = BuildOption.BUILDTOTORO;
        Coordinate expectedConstructionCoordinate = new Coordinate(1, -1, 0);
        ConstructionMoveTransmission expectedConstructionMoveTransmission = new ConstructionMoveTransmission(expectedBuildOption, expectedConstructionCoordinate);

        final String testGID = "A";
        final int testMoveNumber = 3;
        final String testPid = "myPlayerId";
        GameMoveIncomingTransmission expectedGameMoveIncomingTransmission = new GameMoveIncomingTransmission(testGID, testMoveNumber, testPid, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        GameMoveIncomingTransmission testGameMoveIncomingTransmission = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getCoordinate().getX(), testGameMoveIncomingTransmission.getTileMove().getCoordinate().getX());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getCoordinate().getY(), testGameMoveIncomingTransmission.getTileMove().getCoordinate().getY());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testGameMoveIncomingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getDirection(), testGameMoveIncomingTransmission.getTileMove().getDirection());

        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getX(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getY(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedGameMoveIncomingTransmission.getGid(), testGameMoveIncomingTransmission.getGid());
        assertEquals(expectedGameMoveIncomingTransmission.getMoveNumber(), testGameMoveIncomingTransmission.getMoveNumber());

    }


    //Built Tiger
    @Test
    public void getGameMoveIncomingTransmissionFromGameMoveMadeStringForBuiltTiger(){
        String message = "GAME A MOVE 3 PLAYER myPlayerId PLACED JUNGLE+LAKE AT 0 3 -3 1 BUILT TIGER PLAYGROUND AT 1 -1 0";

        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedTileCoordinate = new Coordinate(0, 3, -3);
        TileMove expectedTileMove = new TileMove(expectedTile, HexagonNeighborDirection.UPPERLEFT, expectedTileCoordinate);

        BuildOption expectedBuildOption = BuildOption.BUILDTIGER;
        Coordinate expectedConstructionCoordinate = new Coordinate(1, -1, 0);
        ConstructionMoveTransmission expectedConstructionMoveTransmission = new ConstructionMoveTransmission(expectedBuildOption, expectedConstructionCoordinate);

        final String testGID = "A";
        final int testMoveNumber = 3;
        final String testPid = "myPlayerId";
        GameMoveIncomingTransmission expectedGameMoveIncomingTransmission = new GameMoveIncomingTransmission(testGID, testMoveNumber, testPid, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        GameMoveIncomingTransmission testGameMoveIncomingTransmission = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getCoordinate().getX(), testGameMoveIncomingTransmission.getTileMove().getCoordinate().getX());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getCoordinate().getY(), testGameMoveIncomingTransmission.getTileMove().getCoordinate().getY());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testGameMoveIncomingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedGameMoveIncomingTransmission.getTileMove().getDirection(), testGameMoveIncomingTransmission.getTileMove().getDirection());

        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getX(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getY(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedGameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption(), testGameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedGameMoveIncomingTransmission.getGid(), testGameMoveIncomingTransmission.getGid());
        assertEquals(expectedGameMoveIncomingTransmission.getMoveNumber(), testGameMoveIncomingTransmission.getMoveNumber());

    }
}
