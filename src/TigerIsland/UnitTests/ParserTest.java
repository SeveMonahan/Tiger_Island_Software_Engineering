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
        MoveUpdate expectedMoveUpdate = new MoveUpdate(testGID, testMoveNumber, testPid, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        MoveUpdate testMoveUpdate = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedMoveUpdate.getTileMove().getCoordinate().getX(), testMoveUpdate.getTileMove().getCoordinate().getX());
        assertEquals(expectedMoveUpdate.getTileMove().getCoordinate().getY(), testMoveUpdate.getTileMove().getCoordinate().getY());
        assertEquals(expectedMoveUpdate.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testMoveUpdate.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedMoveUpdate.getTileMove().getDirection(), testMoveUpdate.getTileMove().getDirection());

        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getCoordinate().getX(), testMoveUpdate.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getCoordinate().getY(), testMoveUpdate.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getBuildOption(), testMoveUpdate.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedMoveUpdate.getGid(), testMoveUpdate.getGid());
        assertEquals(expectedMoveUpdate.getMoveID(), testMoveUpdate.getMoveID());

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
        final String testMoveNumber = "3";
        final String testPid = "myPlayerId";
        MoveUpdate expectedMoveUpdate = new MoveUpdate(testGID, testMoveNumber, testPid, expectedTileMove, expectedExpandSettlementMoveTransmission);

        Parser parser = new Parser();
        MoveUpdate testMoveUpdate = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedMoveUpdate.getTileMove().getCoordinate().getX(), testMoveUpdate.getTileMove().getCoordinate().getX());
        assertEquals(expectedMoveUpdate.getTileMove().getCoordinate().getY(), testMoveUpdate.getTileMove().getCoordinate().getY());
        assertEquals(expectedMoveUpdate.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testMoveUpdate.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedMoveUpdate.getTileMove().getDirection(), testMoveUpdate.getTileMove().getDirection());

        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getCoordinate().getX(), testMoveUpdate.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getCoordinate().getY(), testMoveUpdate.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getBuildOption(), testMoveUpdate.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedMoveUpdate.getGid(), testMoveUpdate.getGid());
        assertEquals(expectedMoveUpdate.getMoveID(), testMoveUpdate.getMoveID());

        expectedExpandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) expectedMoveUpdate.getConstructionMoveTransmission();
        ExpandSettlementMoveTransmission testExpandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) testMoveUpdate.getConstructionMoveTransmission();
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
        MoveUpdate expectedMoveUpdate = new MoveUpdate(testGID, testMoveNumber, testPid, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        MoveUpdate testMoveUpdate = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedMoveUpdate.getTileMove().getCoordinate().getX(), testMoveUpdate.getTileMove().getCoordinate().getX());
        assertEquals(expectedMoveUpdate.getTileMove().getCoordinate().getY(), testMoveUpdate.getTileMove().getCoordinate().getY());
        assertEquals(expectedMoveUpdate.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testMoveUpdate.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedMoveUpdate.getTileMove().getDirection(), testMoveUpdate.getTileMove().getDirection());

        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getCoordinate().getX(), testMoveUpdate.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getCoordinate().getY(), testMoveUpdate.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getBuildOption(), testMoveUpdate.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedMoveUpdate.getGid(), testMoveUpdate.getGid());
        assertEquals(expectedMoveUpdate.getMoveID(), testMoveUpdate.getMoveID());

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
        MoveUpdate expectedMoveUpdate = new MoveUpdate(testGID, testMoveNumber, testPid, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        MoveUpdate testMoveUpdate = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedMoveUpdate.getTileMove().getCoordinate().getX(), testMoveUpdate.getTileMove().getCoordinate().getX());
        assertEquals(expectedMoveUpdate.getTileMove().getCoordinate().getY(), testMoveUpdate.getTileMove().getCoordinate().getY());
        assertEquals(expectedMoveUpdate.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testMoveUpdate.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedMoveUpdate.getTileMove().getDirection(), testMoveUpdate.getTileMove().getDirection());

        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getCoordinate().getX(), testMoveUpdate.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getCoordinate().getY(), testMoveUpdate.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedMoveUpdate.getConstructionMoveTransmission().getBuildOption(), testMoveUpdate.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedMoveUpdate.getGid(), testMoveUpdate.getGid());
        assertEquals(expectedMoveUpdate.getMoveID(), testMoveUpdate.getMoveID());

    }
}
