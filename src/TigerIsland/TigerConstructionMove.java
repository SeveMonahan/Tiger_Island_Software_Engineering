package TigerIsland;

public class TigerConstructionMove extends ConstructionMoveJustCoordinate {
    public TigerConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    public HexagonOccupationStatus getOccupyStatus(){
        return HexagonOccupationStatus.TIGER;
    }

    public boolean canBeKilled() { return true; }

    @Override
    public int numberPiecesRequiredToPreformMove(Player player, Board board) {
        Hexagon hexagon = board.getHexagon(coordinate);

        if (hexagon.isVolcano()) {
            return 1000;
        }

        if (hexagon.getLevel() < 3) {
            return 1000;
        }

        if (hexagon.containsPieces()) {
            return 1000;
        }

        Coordinate[] neighbors = coordinate.getNeighboringCoordinates();
        for (Coordinate neighbor: neighbors) {
            Hexagon hexagonNeighbor = board.getHexagon(neighbor);
            if( hexagonNeighbor.containsPieces() ) {
                return 1;
            }
        }
        // TODO: Multiple tigers can't exist in the same settlement
        // TODO: Need a test to check the above functionality... if we aren't adjacent to a settlement
        return 1000;
    }

    @Override
    public void makeValidMoveAndReturnPointsGained(Player player, Board board) {
        player.substractTiger();

        Hexagon hexagon = board.getHexagon(coordinate);
        hexagon.setOccupationStatus(player.getColor(), this);

        player.addScore(75);

    }


}
