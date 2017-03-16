package TigerIsland;

public class Meeple extends Piece {

    private HexagonOccupationStatus occupyStatus = HexagonOccupationStatus.Meeples;

    public Meeple( Color color ) {
        super(color);
    }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return (int) Math.pow( occupiedHexagon.getLevel(), 2);
    }

    public int populationRequirements(Hexagon hexagonYouWishToOccupy) {
        return hexagonYouWishToOccupy.getLevel();
    }

    public boolean isPlacementValid(Hexagon hexagon) {
        if( !hexagon.isVolcanoHex() ) {
            return true;
        } else {
            return false;
        }
    }

}
