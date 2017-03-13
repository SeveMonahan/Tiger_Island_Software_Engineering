public class Totoro extends Piece {

    public HexagonOccupationStatus getOccupyStatus(){
        return HexagonOccupationStatus.Totoro;
    }

    Totoro( Color color ) {
        super(color);
    }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return 200;
    }

    public int populationRequirements(Hexagon hexagonYouWishToOccupy) {
        return 1;
    }

    public boolean isPlacementValid(Hexagon hexagon) {
        if( !hexagon.isVolcanoHex() ) {
            return true;
        } else {
            return false;
        }
    }
}
