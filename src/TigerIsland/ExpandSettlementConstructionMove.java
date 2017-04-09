package TigerIsland;

import java.util.Queue;

public class ExpandSettlementConstructionMove extends ConstructionMoveJustCoordinate{
    private Terrain terrain;
    private int totalMeeplesNeeded;

    @Override
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    @Override
    protected String getMoveTypeName() {
        return "EXPAND SETTLEMENT AT";
    }

    @Override
    public String marshallMove() {
        return getMoveTypeName() + get_coordinate_marshalling()
                + " " + terrain.toString();
    }

    public ExpandSettlementConstructionMove(Coordinate coordinate, Terrain terrain) {
        super(coordinate);
        this.terrain = terrain;
        this.totalMeeplesNeeded = 1000;
    }

    @Override
    public boolean canPerformMove(Player player, Board board) {
        if (board.getHexagonAt(coordinate).getOccupationColor() != player.getColor()){
            return false;
        }

        Settlement settlement = board.getSettlement(coordinate);
        totalMeeplesNeeded = settlement.expandSettlementFloodFill(board, player, terrain).size();

        return totalMeeplesNeeded != 0 && totalMeeplesNeeded <= player.getMeeplesCount();
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        Settlement settlement = board.getSettlement(coordinate);
        Queue<Coordinate> expansion = settlement.expandSettlementFloodFill(board, player, terrain);

        assert(totalMeeplesNeeded != 1000);

        player.subtractMeeples(totalMeeplesNeeded);

        while(!expansion.isEmpty()) {
            Coordinate expansionCoordinate = expansion.remove();
            Hexagon hexagon = board.getHexagonAt(expansionCoordinate);
            hexagon.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
            player.addScore(hexagon.getLevel() * hexagon.getLevel());
        }
    }
}