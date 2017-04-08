package TigerIsland;

public class Tiger implements Piece {
    private Color color;
    public boolean canBeKilled() { return true; }
    public HexagonOccupationStatus getOccupyStatus() { return HexagonOccupationStatus.TIGERS; }

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
        Hexagon hexagon = board.getHexagonAt(coordinate);

        if (hexagon.isVolcano()) {
            return false;
        }
        if (hexagon.getLevel() < 3) {
            return false;
        }
        if (hexagon.isEmpty()) {
            return false;
        }
        // TODO: Check that there's an adjacent settlement of the same player color.
        return true;
    }
}
