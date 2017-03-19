package TigerIsland;

public class Totoro implements Piece {
    private Color color;

    public boolean canBeKilled() { return false; }
    public HexagonOccupationStatus getOccupyStatus(){
        return HexagonOccupationStatus.Totoro;
    }

    public Totoro( Color color ) { this.color = color; }

    public Color getPieceColor() {
        return this.color;
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
