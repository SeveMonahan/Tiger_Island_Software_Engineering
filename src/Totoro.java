public class Totoro extends Piece {

    private final boolean canBeKilled = false;

    private final int points = 200;

    private HexagonOccupationStatus occupyStatus = HexagonOccupationStatus.Totoro;

    Totoro( Color color ) {
        super(color);
    }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return points;
    }

    public int populationRequirements(Hexagon hexagonYouWishToOccupy) {
        return 1;
    }
}
