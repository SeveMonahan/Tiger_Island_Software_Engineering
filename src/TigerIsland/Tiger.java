package TigerIsland;

public class Tiger extends Piece{

    private HexagonOccupationStatus occupyStatus = HexagonOccupationStatus.Tiger;

    public Tiger( Color color ) {
        super(color);
    }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return (int) Math.pow( occupiedHexagon.getLevel(), 75);
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
