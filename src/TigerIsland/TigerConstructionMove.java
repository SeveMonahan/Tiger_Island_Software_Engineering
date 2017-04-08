package TigerIsland;

public class TigerConstructionMove extends ConstructionMoveJustCoordinate {
    public TigerConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    public boolean canPreformMove(Player player, Board board) {
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
        // TODO: Need a test to check the we can't add a Tiger to a settlement already containing one... if we aren't adjacent to a settlement
        return false;
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.substractTiger();

        Hexagon hexagon = board.getHexagonAt(coordinate);
        hexagon.setOccupationStatus(player.getColor(), PieceStatusHexagon.TIGER);

        player.addScore(75);

    }


}
