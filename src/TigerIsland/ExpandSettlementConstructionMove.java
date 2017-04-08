package TigerIsland;

import java.util.Queue;

public class ExpandSettlementConstructionMove implements ConstructionMoveInternal {
    private Coordinate coordinate;
    private Terrain terrain;
    private int totalMeeplesNeeded;

    public ExpandSettlementConstructionMove(Coordinate coordinate, Terrain terrain) {
        this.coordinate = coordinate;
        this.terrain = terrain;
        this.totalMeeplesNeeded = 1000;
    }

    public boolean canBeKilled() { return true; }

    @Override
    public boolean canPerformMove(Player player, Board board) {
        Settlement settlement = board.getSettlement(coordinate);
        totalMeeplesNeeded = settlement.expandSettlementFloodFill(board, player, terrain).size();

        return totalMeeplesNeeded <= player.getMeeplesCount();
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        Settlement settlement = board.getSettlement(coordinate);
        Queue<Coordinate> expansion = settlement.expandSettlementFloodFill(board, player, terrain);

        assert(totalMeeplesNeeded != 1000);

        player.subtractMeeples(totalMeeplesNeeded);

        while(!expansion.isEmpty()){
            Coordinate expansionCoordinate = expansion.remove();
            Hexagon hexagon = board.getHexagonAt(expansionCoordinate);
            hexagon.setOccupationStatus(player.getColor(), PieceStatusHexagon.MEEPLE);
            player.addScore(hexagon.getLevel() * hexagon.getLevel());
        }


    }

}
