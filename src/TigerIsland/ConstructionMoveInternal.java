package TigerIsland;

public interface ConstructionMoveInternal{
    public void makePreverifiedMove(Player player, Board board);
    public boolean canPerformMove(Player player, Board board);
    Coordinate getCoordinate();
}
