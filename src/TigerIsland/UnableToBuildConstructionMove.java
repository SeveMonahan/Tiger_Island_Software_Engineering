package TigerIsland;

public class UnableToBuildConstructionMove implements ConstructionMoveInternal {

    @Override
    public boolean canPreformMove(Player player, Board board) {
        return true;
    }

    @Override
    public void makePreverifiedMove(Player player, Board board) {
        player.setAutoLoseScore();
    }
}
