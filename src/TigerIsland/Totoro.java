package src.TigerIsland;

public class Totoro implements Piece {
    private Color color;
    public boolean canBeKilled() { return false; }
    public HexagonOccupationStatus getOccupyStatus(){
        return HexagonOccupationStatus.TOTORO;
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

    public boolean isPlacementValid(Coordinate coordinate, Board board) {
        Hexagon hexagon = board.getHexagon(coordinate);
        if( !hexagon.isVolcano() && (hexagon.isEmpty()) ) {
            return true;
        } else {
            return false;
        }
    }
}
