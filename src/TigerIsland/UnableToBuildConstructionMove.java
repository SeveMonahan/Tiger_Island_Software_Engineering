package TigerIsland;

public class UnableToBuildConstructionMove implements ConstructionMoveInternal {

    @Override
    public boolean canPerformMove(Player player, Board board) {
        return true;
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.setAutoLoseScore();
    }

    @Override
    public Coordinate getCoordinate() {
        return new Coordinate(1000,1000);
    }
}
