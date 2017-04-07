package TigerIsland;

public class TotoroConstructionMove extends ConstructionMoveJustCoordinate {
    public TotoroConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    public PieceStatusHexagon getOccupyStatus(){
        return PieceStatusHexagon.TOTORO;
    }

    public boolean canBeKilled() { return false; }

    @Override
    public boolean canPreformMove(Player player, Board board) {
        Color color = player.getColor();

        Hexagon hexagon = board.getHexagonAt(coordinate);

        // TODO test level placement... make sure not placing on 0 level
        if( hexagon.isVolcano() || hexagon.containsPieces() || hexagon.getLevel() == 0 ) {
            return false;
        }

        Coordinate[] neighbors = coordinate.getNeighboringCoordinates();

        for(int i = 0; i < 6; i++){
            // TODO Need to test case where we place next to another players settlement...
            Settlement settlement = board.getSettlement(neighbors[i]);
            if(settlement.getSettlementSize() >= 5 && !settlement.getSettlementContainsTotoro(board) &&
                    color == board.getHexagonAt(neighbors[i]).getOccupationColor() ){
                return player.getTotoroCount() != 0;
            }
        }

        return false;
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.substractTotoro();

        Hexagon hexagon = board.getHexagonAt(coordinate);
        hexagon.setOccupationStatus(player.getColor(), this);

        player.addScore(200);

    }


}
