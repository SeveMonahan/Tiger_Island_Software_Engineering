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
    public void getGameMoveTransmissionFromGameMoveMadeStringForFoundedSettlement(){
        String message = "GAME A MOVE 3 PLAYER pid PLACED JUNGLE+LAKE AT 0 3 -3 1 FOUNDED SETTLEMENT AT 1 -1 0";

        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedTileCoordinate = new Coordinate(0, 3, -3);
        TileMove expectedTileMove = new TileMove(expectedTile, HexagonNeighborDirection.UPPERLEFT, expectedTileCoordinate);

        BuildOption expectedBuildOption = BuildOption.FOUNDSETTLEMENT;
        Coordinate expectedConstructionCoordinate = new Coordinate(1, -1, 0);
        ConstructionMoveTransmission expectedConstructionMoveTransmission = new ConstructionMoveTransmission(expectedBuildOption, expectedConstructionCoordinate);

        final String testGID = "A";
        final int testMoveNumber = 3;
        GameMoveTransmission expectedGameMoveTransmission = new GameMoveTransmission(testGID, testMoveNumber, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        GameMoveTransmission testGameMoveTransmission = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedGameMoveTransmission.getTileMove().getCoordinate().getX(), testGameMoveTransmission.getTileMove().getCoordinate().getX());
        assertEquals(expectedGameMoveTransmission.getTileMove().getCoordinate().getY(), testGameMoveTransmission.getTileMove().getCoordinate().getY());
        assertEquals(expectedGameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testGameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedGameMoveTransmission.getTileMove().getDirection(), testGameMoveTransmission.getTileMove().getDirection());

        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getX(), testGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getY(), testGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getBuildOption(), testGameMoveTransmission.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedGameMoveTransmission.getGid(), testGameMoveTransmission.getGid());
        assertEquals(expectedGameMoveTransmission.getMoveNumber(), testGameMoveTransmission.getMoveNumber());

    }

    //Expanded
    @Test
    public void getGameMoveTransmissionFromGameMoveMadeStringForExpandedSettlement(){
        String message = "GAME A MOVE 3 PLAYER pid PLACED JUNGLE+LAKE AT 0 3 -3 1 EXPANDED SETTLEMENT AT 1 -1 0 JUNGLE";

        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedTileCoordinate = new Coordinate(0, 3, -3);
        TileMove expectedTileMove = new TileMove(expectedTile, HexagonNeighborDirection.UPPERLEFT, expectedTileCoordinate);

        BuildOption expectedBuildOption = BuildOption.EXPANDSETTLEMENT;
        Coordinate expectedConstructionCoordinate = new Coordinate(1, -1, 0);
        ExpandSettlementMoveTransmission expectedExpandSettlementMoveTransmission = new ExpandSettlementMoveTransmission(expectedBuildOption, expectedConstructionCoordinate, Terrain.JUNGLE);

        final String testGID = "A";
        final int testMoveNumber = 3;
        GameMoveTransmission expectedGameMoveTransmission = new GameMoveTransmission(testGID, testMoveNumber, expectedTileMove, expectedExpandSettlementMoveTransmission);

        Parser parser = new Parser();
        GameMoveTransmission testGameMoveTransmission = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedGameMoveTransmission.getTileMove().getCoordinate().getX(), testGameMoveTransmission.getTileMove().getCoordinate().getX());
        assertEquals(expectedGameMoveTransmission.getTileMove().getCoordinate().getY(), testGameMoveTransmission.getTileMove().getCoordinate().getY());
        assertEquals(expectedGameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testGameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedGameMoveTransmission.getTileMove().getDirection(), testGameMoveTransmission.getTileMove().getDirection());

        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getX(), testGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getY(), testGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getBuildOption(), testGameMoveTransmission.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedGameMoveTransmission.getGid(), testGameMoveTransmission.getGid());
        assertEquals(expectedGameMoveTransmission.getMoveNumber(), testGameMoveTransmission.getMoveNumber());

        expectedExpandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) expectedGameMoveTransmission.getConstructionMoveTransmission();
        ExpandSettlementMoveTransmission testExpandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) testGameMoveTransmission.getConstructionMoveTransmission();
        assertEquals(expectedExpandSettlementMoveTransmission.getTerrain(), testExpandSettlementMoveTransmission.getTerrain());
    }


    //Built Totoro
    @Test
    public void getGameMoveTransmissionFromGameMoveMadeStringForBuiltTotoro(){
        String message = "GAME A MOVE 3 PLAYER pid PLACED JUNGLE+LAKE AT 0 3 -3 1 BUILT TOTORO SANCTUARY AT 1 -1 0";

        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedTileCoordinate = new Coordinate(0, 3, -3);
        TileMove expectedTileMove = new TileMove(expectedTile, HexagonNeighborDirection.UPPERLEFT, expectedTileCoordinate);

        BuildOption expectedBuildOption = BuildOption.BUILDTOTORO;
        Coordinate expectedConstructionCoordinate = new Coordinate(1, -1, 0);
        ConstructionMoveTransmission expectedConstructionMoveTransmission = new ConstructionMoveTransmission(expectedBuildOption, expectedConstructionCoordinate);

        final String testGID = "A";
        final int testMoveNumber = 3;
        GameMoveTransmission expectedGameMoveTransmission = new GameMoveTransmission(testGID, testMoveNumber, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        GameMoveTransmission testGameMoveTransmission = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedGameMoveTransmission.getTileMove().getCoordinate().getX(), testGameMoveTransmission.getTileMove().getCoordinate().getX());
        assertEquals(expectedGameMoveTransmission.getTileMove().getCoordinate().getY(), testGameMoveTransmission.getTileMove().getCoordinate().getY());
        assertEquals(expectedGameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testGameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedGameMoveTransmission.getTileMove().getDirection(), testGameMoveTransmission.getTileMove().getDirection());

        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getX(), testGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getY(), testGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getBuildOption(), testGameMoveTransmission.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedGameMoveTransmission.getGid(), testGameMoveTransmission.getGid());
        assertEquals(expectedGameMoveTransmission.getMoveNumber(), testGameMoveTransmission.getMoveNumber());

    }


    //Built Tiger
    @Test
    public void getGameMoveTransmissionFromGameMoveMadeStringForBuiltTiger(){
        String message = "GAME A MOVE 3 PLAYER pid PLACED JUNGLE+LAKE AT 0 3 -3 1 BUILT TIGER PLAYGROUND AT 1 -1 0";

        Tile expectedTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate expectedTileCoordinate = new Coordinate(0, 3, -3);
        TileMove expectedTileMove = new TileMove(expectedTile, HexagonNeighborDirection.UPPERLEFT, expectedTileCoordinate);

        BuildOption expectedBuildOption = BuildOption.BUILDTIGER;
        Coordinate expectedConstructionCoordinate = new Coordinate(1, -1, 0);
        ConstructionMoveTransmission expectedConstructionMoveTransmission = new ConstructionMoveTransmission(expectedBuildOption, expectedConstructionCoordinate);

        final String testGID = "A";
        final int testMoveNumber = 3;
        GameMoveTransmission expectedGameMoveTransmission = new GameMoveTransmission(testGID, testMoveNumber, expectedTileMove, expectedConstructionMoveTransmission);

        Parser parser = new Parser();
        GameMoveTransmission testGameMoveTransmission = parser.opponentMoveStringToGameMove(message);

        assertEquals(expectedGameMoveTransmission.getTileMove().getCoordinate().getX(), testGameMoveTransmission.getTileMove().getCoordinate().getX());
        assertEquals(expectedGameMoveTransmission.getTileMove().getCoordinate().getY(), testGameMoveTransmission.getTileMove().getCoordinate().getY());
        assertEquals(expectedGameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano(), testGameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano());
        assertEquals(expectedGameMoveTransmission.getTileMove().getDirection(), testGameMoveTransmission.getTileMove().getDirection());

        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getX(), testGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getX());
        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getY(), testGameMoveTransmission.getConstructionMoveTransmission().getCoordinate().getY());
        assertEquals(expectedGameMoveTransmission.getConstructionMoveTransmission().getBuildOption(), testGameMoveTransmission.getConstructionMoveTransmission().getBuildOption());
        assertEquals(expectedGameMoveTransmission.getGid(), testGameMoveTransmission.getGid());
        assertEquals(expectedGameMoveTransmission.getMoveNumber(), testGameMoveTransmission.getMoveNumber());

    }
}
