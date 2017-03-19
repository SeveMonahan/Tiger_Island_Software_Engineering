package TigerIsland;

public class Totoro extends Piece {
    public boolean canBeKilled() { return false; }
    public HexagonOccupationStatus getOccupyStatus(){
        return HexagonOccupationStatus.Totoro;
    }

    public Totoro( Color color ) { super(color); }

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
