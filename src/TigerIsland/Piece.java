package TigerIsland;

public class Piece {
    private Color color;
    private boolean canBeKilled;
    private int points;
    private HexagonOccupationStatus occupyStatus;

    public Piece(Color color) {
        // this.type = type;
        this.color = color;
    }

    public HexagonOccupationStatus getOccupyStatus() {
        return occupyStatus;
    }

    public boolean canThisBeKilled() {
        return this.canBeKilled;
    }

    Color getPieceColor() { return color; }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return points;
    }

    public int populationRequirements(Hexagon hexagonYouWishToOccupy) {
        return 0;
    }

    public boolean isPlacementValid(Hexagon hexagon) {
        return false;
    }
}
