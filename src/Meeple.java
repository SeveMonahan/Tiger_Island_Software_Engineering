public class Meeple extends Piece {

    private HexagonOccupationStatus occupyStatus = HexagonOccupationStatus.Meeples;

    Meeple( Color color ) {
        super(color);
    }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return (int) Math.pow( occupiedHexagon.getLevel(), 2);
    }

    public int populationRequirements(Hexagon hexagonYouWishToOccupy) {
        return hexagonYouWishToOccupy.getLevel();
    }

}
