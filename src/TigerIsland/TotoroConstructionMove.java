package TigerIsland;

public class TotoroConstructionMove extends ConstructionMoveJustCoordinate {
    public TotoroConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    public HexagonOccupationStatus getOccupyStatus(){
        return HexagonOccupationStatus.TOTORO;
    }

    public boolean canBeKilled() { return false; }

    public int getPointsAfterPlacement(Hexagon occupiedHexagon) {
        return 200;
    }

    @Override
    public int isValidPlace(Color color, Board board) {
        Hexagon hexagon = board.getHexagon(coordinate);

        // TODO test level placement... make sure not placing on 0 level
        if( hexagon.isVolcano() || hexagon.isOccupied() || hexagon.getLevel() == 0 ) {
            return 1000;
        }

        Coordinate[] neighbors = coordinate.getNeighboringCoordinates();

        for(int i = 0; i < 6; i++){
            // TODO Need to test case where we place next to another players settlement...
            if(board.getSettlementSize(neighbors[i]) >= 5 && !board.getSettlementContainsTotoro(neighbors[i]) &&
                    color == board.getHexagon(neighbors[i]).getOccupationColor() ){
                return 1;
            }
        }

        return 1000;
    }

    @Override
    public void makeValidPlaceIfAble(Color color, Board board) {

    }


}
