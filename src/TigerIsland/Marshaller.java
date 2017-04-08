package TigerIsland;

public class Marshaller {
    public String convertTileMoveAndConstructionMoveToString(GameMoveOutgoingTransmission gameMoveOutgoingTransmission) {

        String tileString = extractTileStringFromGameMove(gameMoveOutgoingTransmission);

        int tileX = gameMoveOutgoingTransmission.getTileMove().getCoordinate().ConvertToCube()[0];
        int tileY = gameMoveOutgoingTransmission.getTileMove().getCoordinate().ConvertToCube()[1];
        int tileZ = gameMoveOutgoingTransmission.getTileMove().getCoordinate().ConvertToCube()[2];

        HexagonNeighborDirection tileDirection = gameMoveOutgoingTransmission.getTileMove().getDirection();
        int tileOrientation = tileDirection.directionToInt(tileDirection);

        String start_of_move_string = "GAME " + gameMoveOutgoingTransmission.getGid() + " MOVE "
                                        + gameMoveOutgoingTransmission.getMoveNumber() + " PLACE "
                                        + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " "
                                        + tileOrientation + " ";

        return start_of_move_string + gameMoveOutgoingTransmission.getConstructionMoveInternal().marshallMove();
    }

    private String extractTileStringFromGameMove(GameMoveOutgoingTransmission gameMoveOutgoingTransmission){
        Terrain terrainA = gameMoveOutgoingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano()[1];
        Terrain terrainB = gameMoveOutgoingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano()[2];
        return terrainA.toString() + "+" + terrainB.toString();
    }
}
