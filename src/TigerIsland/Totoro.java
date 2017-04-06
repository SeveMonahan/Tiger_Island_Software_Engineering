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

        // TODO test level placement... make sure not placing on 0 level
        if( hexagon.isVolcano() || hexagon.isOccupied() || hexagon.getLevel() == 0 ) {
            return false;
        }

        boolean result = false;

        Coordinate[] neighbors = coordinate.getNeighboringCoordinates();

        for(int i = 0; i < 6; i++){
            // TODO Need to test case where we place next to another players settlement...
            if(board.getSettlementSize(neighbors[i]) >= 5 && !board.getSettlementContainsTotoro(neighbors[i]) &&
                    this.color == board.getHexagon(neighbors[i]).getOccupationColor() ){
                result = true;
            }
        }

        return result;

    }
}