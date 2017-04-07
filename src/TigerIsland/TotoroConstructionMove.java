package TigerIsland;

public class TotoroConstructionMove extends ConstructionMoveJustCoordinate {
    public TotoroConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    public HexagonOccupationStatus getOccupyStatus(){
        return HexagonOccupationStatus.TOTORO;
    }

    public boolean canBeKilled() { return false; }

    @Override
    public int numberPiecesRequiredToPreformMove(Player player, Board board) {
        Color color = player.getColor();

        Hexagon hexagon = board.getHexagon(coordinate);

        // TODO test level placement... make sure not placing on 0 level
        if( hexagon.isVolcano() || hexagon.containsPieces() || hexagon.getLevel() == 0 ) {
            return 1000;
        }

        Coordinate[] neighbors = coordinate.getNeighboringCoordinates();

        for(int i = 0; i < 6; i++){
            // TODO Need to test case where we place next to another players settlement...
            Settlement settlement = board.getSettlement(neighbors[i]);
            if(settlement.getSettlementSize() >= 5 && !settlement.getSettlementContainsTotoro(board) &&
            // if(board.getSettlementSize(neighbors[i]) >= 5 && !board.getSettlementContainsTotoro(neighbors[i]) &&
                    color == board.getHexagon(neighbors[i]).getOccupationColor() ){
                return 1;
            }
        }

        return 1000;
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.substractTotoro();

        Hexagon hexagon = board.getHexagon(coordinate);
        hexagon.setOccupationStatus(player.getColor(), this);

        player.addScore(200);

    }


}
