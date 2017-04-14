package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ParserTest {
    @Test
    public void getGameMoveIncomingCommandMakeYourMoveString(){
        String message = "MAKE YOUR MOVE IN GAME gameId WITHIN 1.5 SECONDs: MOVE 5 PLACE JUNGLE+LAKE";

        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        ServerRequestAskingUsToMove expectedServerRequestAskingUsToMove = new ServerRequestAskingUsToMove("gameId", 1.5, "5", expectedTile);

        Parser parser = new Parser();
        ServerRequestAskingUsToMove testServerRequestAskingUsToMove = parser.commandToObject(message);

        assertEquals(expectedServerRequestAskingUsToMove.getTile().getTerrainsClockwiseFromVolcano(), testServerRequestAskingUsToMove.getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedServerRequestAskingUsToMove.getGid(), testServerRequestAskingUsToMove.getGid());
        assertEquals(expectedServerRequestAskingUsToMove.getMoveNumber(), testServerRequestAskingUsToMove.getMoveNumber());
        assertEquals(expectedServerRequestAskingUsToMove.getTime(), testServerRequestAskingUsToMove.getTime(), 0);
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
        final String testMoveNumber = "3";
        final String testPid = "myPlayerId";
        MoveInGameIncoming expectedMoveInGameIncoming = new MoveInGameIncoming(testGID, testMoveNumber, testPid, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        MoveInGameIncoming testMoveInGameIncoming = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedMoveInGameIncoming.getTileMove().getCoordinate().getX(), testMoveInGameIncoming.getTileMove().getCoordinate().getX());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getCoordinate().getY(), testMoveInGameIncoming.getTileMove().getCoordinate().getY());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testMoveInGameIncoming.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getDirection(), testMoveInGameIncoming.getTileMove().getDirection());

        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getX(), testMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getY(), testMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getBuildOption(), testMoveInGameIncoming.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedMoveInGameIncoming.getGid(), testMoveInGameIncoming.getGid());
        assertEquals(expectedMoveInGameIncoming.getMoveID(), testMoveInGameIncoming.getMoveID());

    }

    //Expanded
    @Test
    public void getGameMoveIncomingTransmissionFromGameMoveMadeStringForExpandedSettlement(){
        String message = "GAME B MOVE 3 PLAYER 14 PLACED GRASS+LAKE AT -2 0 2 3 EXPANDED SETTLEMENT AT 0 0 0 GRASS";

        Tile expectedTile = new Tile(Terrain.GRASS, Terrain.LAKE);
        Coordinate expectedTileCoordinate = new Coordinate(-2, 0, 2);
        HexagonNeighborDirection expectedDirection = HexagonNeighborDirection.UPPERLEFT;
        expectedDirection = expectedDirection.intToDirection(3);
        TileMove expectedTileMove = new TileMove(expectedTile, expectedDirection, expectedTileCoordinate);

        BuildOption expectedBuildOption = BuildOption.EXPANDSETTLEMENT;
        Coordinate expectedConstructionCoordinate = new Coordinate(0, 0, 0);
        ExpandSettlementMoveTransmission expectedExpandSettlementMoveTransmission = new ExpandSettlementMoveTransmission(expectedBuildOption, expectedConstructionCoordinate, Terrain.GRASS);

        final String expectedGID = "B";
        final String expectedMoveNumber = "3";
        final String expectedPid = "14";
        MoveInGameIncoming expectedMoveInGameIncoming = new MoveInGameIncoming(expectedGID, expectedMoveNumber, expectedPid, expectedTileMove, expectedExpandSettlementMoveTransmission);

        Parser parser = new Parser();
        MoveInGameIncoming testMoveInGameIncoming = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedMoveInGameIncoming.getTileMove().getCoordinate().getX(), testMoveInGameIncoming.getTileMove().getCoordinate().getX());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getCoordinate().getY(), testMoveInGameIncoming.getTileMove().getCoordinate().getY());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testMoveInGameIncoming.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getDirection(), testMoveInGameIncoming.getTileMove().getDirection());

        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getX(), testMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getY(), testMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getBuildOption(), testMoveInGameIncoming.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedMoveInGameIncoming.getGid(), testMoveInGameIncoming.getGid());
        assertEquals(expectedMoveInGameIncoming.getMoveID(), testMoveInGameIncoming.getMoveID());

        expectedExpandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) expectedMoveInGameIncoming.getConstructionMoveTransmission();
        ExpandSettlementMoveTransmission testExpandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) testMoveInGameIncoming.getConstructionMoveTransmission();
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
        final String testMoveNumber = "3";
        final String testPid = "myPlayerId";
        MoveInGameIncoming expectedMoveInGameIncoming = new MoveInGameIncoming(testGID, testMoveNumber, testPid, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        MoveInGameIncoming testMoveInGameIncoming = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedMoveInGameIncoming.getTileMove().getCoordinate().getX(), testMoveInGameIncoming.getTileMove().getCoordinate().getX());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getCoordinate().getY(), testMoveInGameIncoming.getTileMove().getCoordinate().getY());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testMoveInGameIncoming.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getDirection(), testMoveInGameIncoming.getTileMove().getDirection());

        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getX(), testMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getY(), testMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getBuildOption(), testMoveInGameIncoming.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedMoveInGameIncoming.getGid(), testMoveInGameIncoming.getGid());
        assertEquals(expectedMoveInGameIncoming.getMoveID(), testMoveInGameIncoming.getMoveID());

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
        final String testMoveNumber = "3";
        final String testPid = "myPlayerId";
        MoveInGameIncoming expectedMoveInGameIncoming = new MoveInGameIncoming(testGID, testMoveNumber, testPid, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        MoveInGameIncoming testMoveInGameIncoming = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedMoveInGameIncoming.getTileMove().getCoordinate().getX(), testMoveInGameIncoming.getTileMove().getCoordinate().getX());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getCoordinate().getY(), testMoveInGameIncoming.getTileMove().getCoordinate().getY());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testMoveInGameIncoming.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedMoveInGameIncoming.getTileMove().getDirection(), testMoveInGameIncoming.getTileMove().getDirection());

        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getX(), testMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getY(), testMoveInGameIncoming.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedMoveInGameIncoming.getConstructionMoveTransmission().getBuildOption(), testMoveInGameIncoming.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedMoveInGameIncoming.getGid(), testMoveInGameIncoming.getGid());
        assertEquals(expectedMoveInGameIncoming.getMoveID(), testMoveInGameIncoming.getMoveID());

    }
}
