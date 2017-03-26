package TigerIsland;

public class Tiger implements Piece {
    private Color color;
    public boolean canBeKilled() { return true; }
    public HexagonOccupationStatus getOccupyStatus() { return HexagonOccupationStatus.Tigers; }

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

    public boolean isPlacementValid(Hexagon hexagon) {
        if (hexagon.isVolcanoHex()) {
            return false;
        }
        if (hexagon.getLevel() < 3) {
            return false;
        }
        if (hexagon.getOccupationStatus() != HexagonOccupationStatus.empty) {
            return false;
        }
        // TODO: Check that there's an adjacent settlement of the same player color.
        return true;
    }
}
