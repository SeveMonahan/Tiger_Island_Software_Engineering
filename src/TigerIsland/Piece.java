package TigerIsland;

public interface Piece {
    boolean canBeKilled();
    HexagonOccupationStatus getOccupyStatus();
    Color getPieceColor();
    int getPointsAfterPlacement(Hexagon occupiedHexagon);
    int populationRequirements(Hexagon hexagonYouWishToOccupy);
    boolean isPlacementValid(Coordinate coordinate, Board board);
}
