package TigerIsland;

public interface ConstructionMoveInternal{
    int isValidPlace(Player player, Board board);
    void makeValidMoveAndReturnPointsGained(Player player, Board board);
    boolean canBeKilled();
    HexagonOccupationStatus getOccupyStatus();
}
