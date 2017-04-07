package TigerIsland;

public interface ConstructionMoveInternal{
    public boolean isValidPlace(Color color, Board board);
    public boolean makeValidPlaceIfAble(Color color, Board board);
}
