package TigerIsland;

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

        if( hexagon.isVolcano() || !(hexagon.isEmpty()) ) {
            return false;
        }

        boolean result = false;

        Coordinate[] neighbors = coordinate.getNeighboringCoordinates();

        for(int i = 0; i < 6; i++){
            // XXX needs to handle the case where we don't own the adjacent settlement
            if(board.getSettlementSize(neighbors[i]) >= 5 && !board.getSettlementContainsTotoro(neighbors[i])){
                result = true;
            }
        }

        return result;

    }
}
