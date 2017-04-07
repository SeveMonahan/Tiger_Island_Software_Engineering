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

    public HexagonOccupationStatus getOccupyStatus(){
        return HexagonOccupationStatus.MEEPLE;
    }

    public boolean canBeKilled() { return true; }

    @Override
    public int numberPiecesRequiredToPreformMove(Player player, Board board) {

        totalMeeplesNeeded = Settlement.expandSettlementFloodFill(board, coordinate, player, terrain).size();

        return totalMeeplesNeeded;
    }

    @Override
    public void makeValidMoveAndReturnPointsGained(Player player, Board board) {
        Queue<Coordinate> expansion = Settlement.expandSettlementFloodFill(board, coordinate, player, terrain);

        assert(totalMeeplesNeeded != 1000);

        player.subtractMeeples(totalMeeplesNeeded);

        while(!expansion.isEmpty()){
            Coordinate expansionCoordinate = expansion.remove();
            Hexagon hexagon = board.getHexagon(expansionCoordinate);
            hexagon.setOccupationStatus(player.getColor(), this);
            player.addScore(hexagon.getLevel() * hexagon.getLevel());
        }


    }

}
