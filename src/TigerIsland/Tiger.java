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
        if( !hexagon.isVolcanoHex() && (hexagon.getOccupationStatus() == HexagonOccupationStatus.empty) ) {
            return true;
        } else {
            return false;
        }
    }
}
