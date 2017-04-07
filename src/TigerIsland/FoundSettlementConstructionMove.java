package TigerIsland;

public class FoundSettlementConstructionMove extends ConstructionMoveJustCoordinate {
    public FoundSettlementConstructionMove(Coordinate coordinate) {
        super(coordinate);
    }

    public HexagonOccupationStatus getOccupyStatus(){
        return HexagonOccupationStatus.MEEPLE;
    }

    public boolean canBeKilled() { return true; }

    @Override
    public int numberPiecesRequiredToPreformMove(Player player, Board board) {
        Hexagon hexagon = board.getHexagon(coordinate);
        if(!hexagon.isVolcano()
                && hexagon.getLevel() == 1
                && (!hexagon.containsPieces())) {
            return 1;
        }

        return 1000;
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.subtractMeeples(1);

        Hexagon hexagon = board.getHexagon(coordinate);
        hexagon.setOccupationStatus(player.getColor(), this);

        player.addScore(1);

    }

}
