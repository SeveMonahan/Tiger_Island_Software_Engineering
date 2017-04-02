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
    private boolean allThreeAreEqual(int hexagonLevel1, int hexagonLevel2, int hexagonLevel3) {
        return hexagonLevel1 == hexagonLevel2 && hexagonLevel2 == hexagonLevel3;
    }
    private boolean volcanoesLineUp() {
        return underneathHexagons[0].isVolcano();
    }
    private Hexagon[] getHexagonsUnderneathTile() {
        Coordinate volcanoCoordinate = tileMove.getCoordinate();
        HexagonNeighborDirection neighborOneDirection = tileMove.getDirection();
        HexagonNeighborDirection neighborTwoDirection = tileMove.getDirection().getNextClockwise();

        Hexagon hexagonUnderVolcano = board.getHexagon(volcanoCoordinate);
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

        Coordinate neighborOneCoordinate = board.getNeighboringCoordinate(volcanoCoordinate, neighborOneDirection);
        Coordinate neighborTwoCoordinate = board.getNeighboringCoordinate(volcanoCoordinate, neighborTwoDirection);

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
        Hexagon hexagonUnderNeighborOne = underneathHexagons[1];
        Hexagon hexagonUnderNeighborTwo = underneathHexagons[2];
        if (hexagonUnderNeighborOne.getCanBeNuked() && hexagonUnderNeighborTwo.getCanBeNuked()) {
            return true;
        }
        else {
            return false;
        }
    }
    private boolean noEntireSettlementsUnderneath() {
        Hexagon hexagonUnderNeighborOne = underneathHexagons[1];
        Hexagon hexagonUnderNeighborTwo = underneathHexagons[2];
        // If both hexagons are occupied...
        if (hexagonUnderNeighborOne.isOccupied() && hexagonUnderNeighborTwo.isOccupied()) {
            // If both hexagons are occupied by the same player...
            if (hexagonUnderNeighborOne.getOccupationColor() == hexagonUnderNeighborTwo.getOccupationColor()) {
                int settlementSize = board.getSettlementSize(tileCoordinates[1]);
                if (settlementSize > 2) {
                    return true;
                }
                else {
                    return false;
                }
            }
            // If the 2 hexagons are occupied by 2 different players...
            else {
                int settlementSizeOfNeighborOne = board.getSettlementSize(tileCoordinates[1]);
                int settlementSizeOfNeighborTwo = board.getSettlementSize(tileCoordinates[2]);
                if (settlementSizeOfNeighborOne > 1 && settlementSizeOfNeighborTwo > 1) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        else {
            // If both hexagons are empty...
            if (hexagonUnderNeighborOne.isEmpty() && hexagonUnderNeighborTwo.isEmpty()) {
                return true;
            }
            // If 1 hexagon is empty and the other is occupied...
            else {
                Coordinate occupiedCoordinate;
                if (hexagonUnderNeighborOne.isOccupied()) {
                    occupiedCoordinate = tileCoordinates[1];
                }
                else {
                    occupiedCoordinate = tileCoordinates[2];
                }
                int settlementSize = board.getSettlementSize(occupiedCoordinate);
                if (settlementSize > 1) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
    }
}