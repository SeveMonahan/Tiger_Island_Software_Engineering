package TigerIsland;

public class Tiger implements Piece {
    private Color color;
    public boolean canBeKilled() { return true; }
    public HexagonOccupationStatus getOccupyStatus() { return HexagonOccupationStatus.TIGER; }

    public Tiger( Color color ) {
        this.color = color;
    }

    public Color getPieceColor() {
        return this.color;
    }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return 75;
    }

    public int populationRequirements(Hexagon hexagonYouWishToOccupy) {
        return hexagonYouWishToOccupy.getLevel();
    }

    public boolean isPlacementValid(Coordinate coordinate, Board board) {
        Hexagon hexagon = board.getHexagon(coordinate);

        if (hexagon.isVolcano()) {
            return false;
        }
        if (hexagon.getLevel() < 3) {
            return false;
        }
        if (hexagon.isEmpty()) {
            return false;
        }
        Coordinate[] neighbors = coordinate.getNeighboringCoordinates();
        for (Coordinate neighbor: neighbors) {
            Hexagon hexagonNeighbor = board.getHexagon(neighbor);
            if( hexagonNeighbor.isOccupied() ) {
                return true;
            }
        }
        // TODO: Multiple tigers can't exist in the same settlement
        // TODO: Need a test to check the above functionality... if we aren't adjacent to a settlement
        return false;
    }
}
