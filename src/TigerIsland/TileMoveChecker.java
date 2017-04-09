package TigerIsland;

public class TileMoveChecker {
    // Members
    private Board board;
    private TileMove tileMove;
    //// For tileCoordinates and underneathHexagons, [0] == volcano, [1] == neighbor1, [2] == neighbor2
    private Coordinate[] tileCoordinates;
    private Hexagon[] underneathHexagons;

    // Methods
    public boolean checkForValidity(TileMove tileMove, Board board) {

        if(tileMove instanceof FirstTurnTileMove){
            return true;
        }

        this.board = board;
        this.tileMove = tileMove;
        this.underneathHexagons = getHexagonsUnderneathTile();
        this.tileCoordinates = getTileCoordinates();

        if (this.board.isEmpty()) {
            return true;
        }
        else {
            if (tileIsAdjacentToBoard() && flatUnderneathTile()) {
                if (placingOnTopOfOtherTiles()) {
                    if (coveringMoreThanOneTile() && volcanoesLineUp() && allowedToNukePiecesUnderneath()) {
                        return true;
                    }
                    else {
                        return false;
                    }
                }
                else {
                    return true;
                }
            }
            else {
                return false;
            }
        }
    }
    // Helper Methods
    private boolean tileIsAdjacentToBoard() {
        boolean isAdjacent = false;
        for (int i = 0; i < 3; i++) {
            Hexagon[] neighbors = board.getNeighboringHexagons(tileCoordinates[i]);
            for(Hexagon neighbor : neighbors) {
                if(neighbor.getLevel() != 0) {
                    isAdjacent = true;
                }
            }
        }
        return isAdjacent;
    }
    //// Assumes that flatUnderneathTile() == true.
    private boolean placingOnTopOfOtherTiles() {
        return underneathHexagons[0].getLevel() >= 1;
    }
    private boolean flatUnderneathTile() {
        int levelOfVolcanoUnderneath = underneathHexagons[0].getLevel();
        int levelOfNeighborOneUnderneath = underneathHexagons[1].getLevel();
        int levelOfNeighborTwoUnderneath = underneathHexagons[2].getLevel();
        if (allThreeAreEqual(levelOfVolcanoUnderneath, levelOfNeighborOneUnderneath, levelOfNeighborTwoUnderneath)) {
            return true;
        }
        else {
            return false;
        }
    }
    private boolean coveringMoreThanOneTile() {
        int hashCodeOfVolcanoUnderneath = underneathHexagons[0].getTileHashCode();
        int hashCodeOfNeighborOneUnderneath = underneathHexagons[1].getTileHashCode();
        int hashCodeOfNeighborTwoUnderneath = underneathHexagons[2].getTileHashCode();
        if (allThreeAreEqual(hashCodeOfVolcanoUnderneath, hashCodeOfNeighborOneUnderneath, hashCodeOfNeighborTwoUnderneath)) {
            return false;
        }
        else {
            return true;
        }
    }
    private boolean allThreeAreEqual(double hexagonLevel1, double hexagonLevel2, double hexagonLevel3) {
        return hexagonLevel1 == hexagonLevel2 && hexagonLevel2 == hexagonLevel3;
    }
    private boolean volcanoesLineUp() {
        return underneathHexagons[0].isVolcano();
    }
    private Hexagon[] getHexagonsUnderneathTile() {
        Coordinate volcanoCoordinate = tileMove.getCoordinate();
        HexagonNeighborDirection neighborOneDirection = tileMove.getDirection();
        HexagonNeighborDirection neighborTwoDirection = tileMove.getDirection().getNextClockwise();

        Hexagon hexagonUnderVolcano = board.getHexagonAt(volcanoCoordinate);
        Hexagon hexagonUnderNeighborOne = board.getNeighboringHexagon(volcanoCoordinate, neighborOneDirection);
        Hexagon hexagonUnderNeighborTwo = board.getNeighboringHexagon(volcanoCoordinate, neighborTwoDirection);

        Hexagon[] underneathHexagons = new Hexagon[3];
        underneathHexagons[0] = hexagonUnderVolcano;
        underneathHexagons[1] = hexagonUnderNeighborOne;
        underneathHexagons[2] = hexagonUnderNeighborTwo;
        return underneathHexagons;
    }
    private Coordinate[] getTileCoordinates() {
        Coordinate volcanoCoordinate = tileMove.getCoordinate();
        HexagonNeighborDirection neighborOneDirection = tileMove.getDirection();
        HexagonNeighborDirection neighborTwoDirection = tileMove.getDirection().getNextClockwise();

        Coordinate neighborOneCoordinate = volcanoCoordinate.getNeighboringCoordinateAt(neighborOneDirection);
        Coordinate neighborTwoCoordinate = volcanoCoordinate.getNeighboringCoordinateAt(neighborTwoDirection);

        Coordinate[] tileCoordinates = new Coordinate[3];
        tileCoordinates[0] = volcanoCoordinate;
        tileCoordinates[1] = neighborOneCoordinate;
        tileCoordinates[2] = neighborTwoCoordinate;
        return tileCoordinates;
    }
    private boolean allowedToNukePiecesUnderneath() {
        if (noInvinciblePiecesUnderneath() && noEntireSettlementsUnderneath()) {
            return true;
        }
        else {
            return false;
        }
    }
    private boolean noInvinciblePiecesUnderneath() {
        Hexagon volcano_hex = underneathHexagons[0];

        assert(volcano_hex.getCanBeNuked());

        Hexagon hexagonUnderNeighborOne = underneathHexagons[1];
        Hexagon hexagonUnderNeighborTwo = underneathHexagons[2];

        return hexagonUnderNeighborOne.getCanBeNuked() && hexagonUnderNeighborTwo.getCanBeNuked();
    }
    private boolean noEntireSettlementsUnderneath() {

        Hexagon hexagonUnderNeighborOne = underneathHexagons[1];
        Hexagon hexagonUnderNeighborTwo = underneathHexagons[2];
        Settlement settlement1 = board.getSettlement(tileCoordinates[1]);
        Settlement settlement2 = board.getSettlement(tileCoordinates[2]);

        int settlementHexOne = settlement1.getSettlementSize();
        int settlementHexTwo = settlement2.getSettlementSize();
        // If both hexagons are occupied...
        if (hexagonUnderNeighborOne.containsPieces() && hexagonUnderNeighborTwo.containsPieces()) {
            // If both hexagons are occupied by the same player...
            if (hexagonUnderNeighborOne.getOccupationColor() == hexagonUnderNeighborTwo.getOccupationColor()) {
                if (settlementHexOne > 2) {
                    return true;
                }
                else {
                    return false;
                }
            } else { // If the 2 hexagons are occupied by 2 different players...
                if (settlementHexOne > 1 && settlementHexTwo > 1) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            // If both hexagons are empty...
            if (!hexagonUnderNeighborOne.containsPieces() && !hexagonUnderNeighborTwo.containsPieces()) {
                return true;
            }
            // If 1 hexagon is empty and the other is occupied...
            else {
                if( settlementHexOne > 1 && settlementHexTwo == 0  ) {
                    return true;
                } else if ( settlementHexOne == 0 && settlementHexTwo > 1 ) {
                    return true;
                } else {
                    return false;
                }
            }
        }
    }
}