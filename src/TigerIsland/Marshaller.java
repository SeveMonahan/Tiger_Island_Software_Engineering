package TigerIsland;

public class Marshaller {
    public String convertTileMoveAndConstructionMoveToString(GameMoveOutgoingTransmission gameMoveOutgoingTransmission) {

        String tileString = extractTileStringFromGameMove(gameMoveOutgoingTransmission);

        int tileX = gameMoveOutgoingTransmission.getTileMove().getCoordinate().ConvertToCube()[0];
        int tileY = gameMoveOutgoingTransmission.getTileMove().getCoordinate().ConvertToCube()[1];
        int tileZ = gameMoveOutgoingTransmission.getTileMove().getCoordinate().ConvertToCube()[2];

        HexagonNeighborDirection tileDirection = gameMoveOutgoingTransmission.getTileMove().getDirection();
        int tileOrientation = tileDirection.directionToInt(tileDirection);

        if(gameMoveOutgoingTransmission.getConstructionMoveTransmission().getBuildOption() == BuildOption.UNABLETOBUILD) {
            return "GAME " + gameMoveOutgoingTransmission.getGid() + " MOVE " + gameMoveOutgoingTransmission.getMoveNumber() + " PLACE " + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " " + tileOrientation + " UNABLE TO BUILD";
        }

        int constructionX = gameMoveOutgoingTransmission.getConstructionMoveTransmission().getCoordinate().ConvertToCube()[0];
        int constructionY = gameMoveOutgoingTransmission.getConstructionMoveTransmission().getCoordinate().ConvertToCube()[1];
        int constructionZ = gameMoveOutgoingTransmission.getConstructionMoveTransmission().getCoordinate().ConvertToCube()[2];

        if (gameMoveOutgoingTransmission.getConstructionMoveTransmission().getBuildOption() == BuildOption.FOUNDSETTLEMENT) {
            return "GAME " + gameMoveOutgoingTransmission.getGid() + " MOVE " + gameMoveOutgoingTransmission.getMoveNumber() + " PLACE " + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " " + tileOrientation + " FOUND SETTLEMENT AT " + constructionX + " " + constructionY + " " + constructionZ;
        }
        else if(gameMoveOutgoingTransmission.getConstructionMoveTransmission().getBuildOption() == BuildOption.EXPANDSETTLEMENT){
            ExpandSettlementMoveTransmission expandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) gameMoveOutgoingTransmission.getConstructionMoveTransmission();
            String terrainToExpand =  expandSettlementMoveTransmission.getTerrain().toString();

            return "GAME " + gameMoveOutgoingTransmission.getGid() + " MOVE " + gameMoveOutgoingTransmission.getMoveNumber() + " PLACE " + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " " + tileOrientation + " EXPAND SETTLEMENT AT " + constructionX + " " + constructionY + " " + constructionZ + " " + terrainToExpand;
        }
        else if(gameMoveOutgoingTransmission.getConstructionMoveTransmission().getBuildOption() == BuildOption.BUILDTOTORO) {
            return "GAME " + gameMoveOutgoingTransmission.getGid() + " MOVE " + gameMoveOutgoingTransmission.getMoveNumber() + " PLACE " + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " " + tileOrientation + " BUILD TOTORO SANCTUARY AT " + constructionX + " " + constructionY + " " + constructionZ;
        }
        else if(gameMoveOutgoingTransmission.getConstructionMoveTransmission().getBuildOption() == BuildOption.BUILDTIGER) {
            return "GAME " + gameMoveOutgoingTransmission.getGid() + " MOVE " + gameMoveOutgoingTransmission.getMoveNumber() + " PLACE " + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " " + tileOrientation + " BUILD TIGER PLAYGROUND AT " + constructionX + " " + constructionY + " " + constructionZ;
        }
        else return null;
    }

    private String extractTileStringFromGameMove(GameMoveOutgoingTransmission gameMoveOutgoingTransmission){
        Terrain terrainA = gameMoveOutgoingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano()[1];
        Terrain terrainB = gameMoveOutgoingTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano()[2];
        return terrainA.toString() + "+" + terrainB.toString();
    }
}
