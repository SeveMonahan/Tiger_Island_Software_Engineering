package TigerIsland;

public class FoundSettlementConstructionMove extends ConstructionMoveJustCoordinate {
    public FoundSettlementConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    @Override
    protected String getMoveTypeName() {
        return "FOUND SETTLEMENT AT";
    }

    public boolean canBeKilled() { return true; }

    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    public boolean canPerformMove(Player player, Board board) {
        Hexagon hexagon = board.getHexagonAt(coordinate);
        if(!hexagon.isVolcano()
                && hexagon.getLevel() == 1
                && (!hexagon.containsPieces())) {
            return player.getMeeplesCount() != 0;
        }

        return false;
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.subtractMeeples(1);

        Hexagon hexagon = board.getHexagonAt(coordinate);
        hexagon.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);

        player.addScore(1);

    }

}
