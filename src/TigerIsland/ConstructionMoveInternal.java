package TigerIsland;

public interface ConstructionMoveInternal{
    int isValidPlace(Color color, Board board);
    void makeValidPlaceIfAble(Color color, Board board);
    boolean canBeKilled();
    HexagonOccupationStatus getOccupyStatus();
}
