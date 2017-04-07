package TigerIsland;

public class Meeple implements Piece {
    private Color color;
    public boolean canBeKilled() { return true; }
    public HexagonOccupationStatus getOccupyStatus() { return HexagonOccupationStatus.MEEPLE; }

    public Meeple( Color color ) {
        this.color = color;
    }

    public Color getPieceColor() {
        return this.color;
    }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return (int) Math.pow( occupiedHexagon.getLevel(), 2);
    }

    public int populationRequirements(Hexagon hexagonYouWishToOccupy) {
        return hexagonYouWishToOccupy.getLevel();
    }

    public boolean isPlacementValid(Coordinate coordinate, Board board) {
        Hexagon hexagon = board.getHexagonAt(coordinate);
        return !hexagon.isVolcano()
                && hexagon.getLevel() > 0
                && (!hexagon.containsPieces());
    }
}
