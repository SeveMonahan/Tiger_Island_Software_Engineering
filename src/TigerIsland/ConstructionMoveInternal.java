package TigerIsland;

public interface ConstructionMoveInternal{
    int isValidPlace(Color color, Board board);
    int makeValidMoveAndReturnPointsGained(Color color, Board board);
    boolean canBeKilled();
    HexagonOccupationStatus getOccupyStatus();
}
