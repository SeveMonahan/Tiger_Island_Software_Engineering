package TigerIsland;

public class TigerConstructionMove extends ConstructionMoveJustCoordinate {
    public TigerConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    public PieceStatusHexagon getOccupyStatus(){
        return PieceStatusHexagon.TIGER;
    }

    public boolean canBeKilled() { return true; }

    @Override
    public boolean canPreformMove(Player player, Board board) {
        Hexagon hexagon = board.getHexagonAt(coordinate);

        if (hexagon.isVolcano()) {
            return false;
        }

        if (hexagon.getLevel() < 3) {
            return false;
        }

        if (hexagon.containsPieces()) {
            return false;
        }

        Coordinate[] neighbors = coordinate.getNeighboringCoordinates();
        for (Coordinate neighbor: neighbors) {
            Hexagon hexagonNeighbor = board.getHexagonAt(neighbor);
            if( hexagonNeighbor.containsPieces() ) {
                return player.getTigerCount() != 0;
            }
        }
        // TODO: Multiple tigers can't exist in the same settlement
        // TODO: Need a test to check the above functionality... if we aren't adjacent to a settlement
        return false;
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.substractTiger();

        Hexagon hexagon = board.getHexagonAt(coordinate);
        hexagon.setOccupationStatus(player.getColor(), this);

        player.addScore(75);

    }


}
