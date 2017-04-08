package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
public class MarshallerTest {

    @Test
    public void convertGameMoveTransmissionToStringForFoundSettlement(){
        String expectedString = "GAME A MOVE 3 PLACE JUNGLE+LAKE AT 1 -1 0 1 FOUND SETTLEMENT AT -2 -1 3";

        Tile testTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate testTileCoordinate = new Coordinate(1, -1, 0);
        TileMove testTileMove = new TileMove(testTile, HexagonNeighborDirection.UPPERLEFT, testTileCoordinate);

        Coordinate testConstructionCoordinate = new Coordinate(-2, -1, 3);
        ConstructionMoveInternal testConstructionMoveTransmission = new FoundSettlementConstructionMove(testConstructionCoordinate);

        final String testGID = "A";
        final int testMoveNumber = 3;
        GameMoveOutgoingTransmission testGameMoveOutgoingTransmission = new GameMoveOutgoingTransmission(testGID, testMoveNumber, testTileMove, testConstructionMoveTransmission);

        Marshaller marshaller = new Marshaller();
        String testString = marshaller.convertTileMoveAndConstructionMoveToString(testGameMoveOutgoingTransmission);

        assertEquals(expectedString, testString);
    }

    @Test
    public void convertGameMoveTransmissionToStringForExpandSettlement(){
        String expectedString = "GAME A MOVE 3 PLACE JUNGLE+LAKE AT 1 -1 0 1 EXPAND SETTLEMENT AT -2 -1 3 GRASS";

        Tile testTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate testTileCoordinate = new Coordinate(1, -1, 0);
        TileMove testTileMove = new TileMove(testTile, HexagonNeighborDirection.UPPERLEFT, testTileCoordinate);

        Coordinate testConstructionCoordinate = new Coordinate(-2, -1, 3);
        ExpandSettlementConstructionMove testExpandSettlementMoveTransmission = new ExpandSettlementConstructionMove(testConstructionCoordinate, Terrain.GRASS);

        final String testGID = "A";
        final int testMoveNumber = 3;
        GameMoveOutgoingTransmission testGameMoveOutgoingTransmission = new GameMoveOutgoingTransmission(testGID, testMoveNumber, testTileMove, testExpandSettlementMoveTransmission);

        Marshaller marshaller = new Marshaller();
        String testString = marshaller.convertTileMoveAndConstructionMoveToString(testGameMoveOutgoingTransmission);

        assertEquals(expectedString, testString);
    }

    @Test
    public void convertGameMoveTransmissionToStringForBuildTotoro(){
        String expectedString = "GAME A MOVE 3 PLACE JUNGLE+LAKE AT 1 -1 0 1 BUILD TOTORO SANCTUARY AT -2 -1 3";

        Tile testTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate testTileCoordinate = new Coordinate(1, -1, 0);
        TileMove testTileMove = new TileMove(testTile, HexagonNeighborDirection.UPPERLEFT, testTileCoordinate);

        Coordinate testConstructionCoordinate = new Coordinate(-2, -1, 3);
        TotoroConstructionMove testConstructionMoveTransmission = new TotoroConstructionMove(testConstructionCoordinate);

        final String testGID = "A";
        final int testMoveNumber = 3;
        GameMoveOutgoingTransmission testGameMoveOutgoingTransmission = new GameMoveOutgoingTransmission(testGID, testMoveNumber, testTileMove, testConstructionMoveTransmission);

        Marshaller marshaller = new Marshaller();
        String testString = marshaller.convertTileMoveAndConstructionMoveToString(testGameMoveOutgoingTransmission);

        assertEquals(expectedString, testString);
    }

    @Test
    public void convertGameMoveTransmissionToStringForBuildTiger(){
        String expectedString = "GAME A MOVE 3 PLACE JUNGLE+LAKE AT 1 -1 0 1 BUILD TIGER PLAYGROUND AT -2 -1 3";

        Tile testTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate testTileCoordinate = new Coordinate(1, -1, 0);
        TileMove testTileMove = new TileMove(testTile, HexagonNeighborDirection.UPPERLEFT, testTileCoordinate);

        Coordinate testConstructionCoordinate = new Coordinate(-2, -1, 3);
        TigerConstructionMove testConstructionMoveTransmission = new TigerConstructionMove(testConstructionCoordinate);

        final String testGID = "A";
        final int testMoveNumber = 3;
        GameMoveOutgoingTransmission testGameMoveOutgoingTransmission = new GameMoveOutgoingTransmission(testGID, testMoveNumber, testTileMove, testConstructionMoveTransmission);

        Marshaller marshaller = new Marshaller();
        String testString = marshaller.convertTileMoveAndConstructionMoveToString(testGameMoveOutgoingTransmission);

        assertEquals(expectedString, testString);
    }

    @Test
    public void convertGameMoveTransmissionToStringForUnableToBuild(){
        String expectedString = "GAME A MOVE 3 PLACE JUNGLE+LAKE AT 1 -1 0 1 UNABLE TO BUILD";

        Tile testTile = new Tile(Terrain.JUNGLE, Terrain.LAKE);
        Coordinate testTileCoordinate = new Coordinate(1, -1, 0);
        TileMove testTileMove = new TileMove(testTile, HexagonNeighborDirection.UPPERLEFT, testTileCoordinate);

        Coordinate testConstructionCoordinate = null;
        UnableToBuildConstructionMove testConstructionMoveTransmission = new UnableToBuildConstructionMove();

        final String testGID = "A";
        final int testMoveNumber = 3;
        GameMoveOutgoingTransmission testGameMoveOutgoingTransmission = new GameMoveOutgoingTransmission(testGID, testMoveNumber, testTileMove, testConstructionMoveTransmission);

        Marshaller marshaller = new Marshaller();
        String testString = marshaller.convertTileMoveAndConstructionMoveToString(testGameMoveOutgoingTransmission);

        assertEquals(expectedString, testString);
    }
}
