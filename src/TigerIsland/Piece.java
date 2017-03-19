package TigerIsland;

public class Piece {
    private Color color;
    public boolean canBeKilled() {
        return false;
    }
    private HexagonOccupationStatus occupyStatus;

    public Piece(Color color) {
        this.color = color;
    }

    public HexagonOccupationStatus getOccupyStatus() {
        return occupyStatus;
    }

    Color getPieceColor() { return color; }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return 0;
    }

    public int populationRequirements(Hexagon hexagonYouWishToOccupy) {
        return 0;
    }

    public boolean isPlacementValid(Hexagon hexagon) {
        return false;
    }
}
