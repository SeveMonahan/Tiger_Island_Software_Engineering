package TigerIsland;

public class TigerConstructionMove extends ConstructionMoveJustCoordinate {
    public TigerConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public boolean canPerformMove(Player player, Board board) {
        Hexagon hexagon = board.getHexagonAt(coordinate);

        if (hexagon.isVolcano()
            || hexagon.containsPieces()
            || hexagon.getLevel() < 3 ) {
            return false;
        }

        Coordinate[] neighbors = coordinate.getNeighboringCoordinates();
        for (Coordinate neighbor: neighbors) {
            Settlement settlement = board.getSettlement(neighbor);

            if(!settlement.getSettlementContainsTiger(board) &&
                    player.getColor() == board.getHexagonAt(neighbor).getOccupationColor() ){
                return player.getTigerCount() != 0;
            }
        }
        return false;
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.subtractTiger();

        Hexagon hexagon = board.getHexagonAt(coordinate);
        hexagon.setOccupationStatus(player.getColor(), PieceStatusHexagon.TIGER);

        player.addScore(75);

    }


}
