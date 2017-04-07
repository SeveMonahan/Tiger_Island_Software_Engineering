package TigerIsland;

public class Marshaller {
    public String convertTileMoveAndConstructionMoveToString(GameMoveTransmission gameMoveTransmission) {
        Terrain terrainA = gameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano()[1];
        Terrain terrainB = gameMoveTransmission.getTileMove().getTile().getTerrainsClockwiseFromVolcano()[2];
        String tileString = terrainA.toString() + "+" + terrainB.toString();

        int tileX = gameMoveTransmission.getTileMove().getCoordinate().ConvertToCube()[0];
        int tileY = gameMoveTransmission.getTileMove().getCoordinate().ConvertToCube()[1];
        int tileZ = gameMoveTransmission.getTileMove().getCoordinate().ConvertToCube()[2];

        HexagonNeighborDirection tileDirection = gameMoveTransmission.getTileMove().getDirection();
        int tileOrientation = tileDirection.directionToInt(tileDirection);

        int constructionX = gameMoveTransmission.getConstructionMoveTransmission().getCoordinate().ConvertToCube()[0];
        int constructionY = gameMoveTransmission.getConstructionMoveTransmission().getCoordinate().ConvertToCube()[1];
        int constructionZ = gameMoveTransmission.getConstructionMoveTransmission().getCoordinate().ConvertToCube()[2];

        if (gameMoveTransmission.getConstructionMoveTransmission().getBuildOption() == BuildOption.FOUNDSETTLEMENT) {
            return "GAME " + gameMoveTransmission.getGid() + " MOVE " + gameMoveTransmission.getMoveNumber() + " PLACE " + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " " + tileOrientation + " FOUND SETTLEMENT AT " + constructionX + " " + constructionY + " " + constructionZ;
        }
        else if(gameMoveTransmission.getConstructionMoveTransmission().getBuildOption() == BuildOption.EXPANDSETTLEMENT){
            ExpandSettlementMoveTransmission expandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) gameMoveTransmission.getConstructionMoveTransmission();
            String terrainToExpand =  expandSettlementMoveTransmission.getTerrain().toString();

            return "GAME " + gameMoveTransmission.getGid() + " MOVE " + gameMoveTransmission.getMoveNumber() + " PLACE " + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " " + tileOrientation + " EXPAND SETTLEMENT AT " + constructionX + " " + constructionY + " " + constructionZ + " " + terrainToExpand;
        }
        else if(gameMoveTransmission.getConstructionMoveTransmission().getBuildOption() == BuildOption.BUILDTOTORO) {
            return "GAME " + gameMoveTransmission.getGid() + " MOVE " + gameMoveTransmission.getMoveNumber() + " PLACE " + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " " + tileOrientation + " BUILD TOTORO SANCTUARY AT " + constructionX + " " + constructionY + " " + constructionZ;
        }
        else if(gameMoveTransmission.getConstructionMoveTransmission().getBuildOption() == BuildOption.BUILDTIGER) {
            return "GAME " + gameMoveTransmission.getGid() + " MOVE " + gameMoveTransmission.getMoveNumber() + " PLACE " + tileString + " AT " + tileX + " " + tileY + " " + tileZ + " " + tileOrientation + " BUILD TIGER PLAYGROUND AT " + constructionX + " " + constructionY + " " + constructionZ;
        }
        else return null;
    }
}
