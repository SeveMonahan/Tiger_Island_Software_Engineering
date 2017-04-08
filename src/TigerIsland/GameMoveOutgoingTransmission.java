package TigerIsland;

public class GameMoveOutgoingTransmission {
    private String gid;
    private int moveNumber;
    private TileMove tileMove;
    private ConstructionMoveInternal constructionMoveTransmission;

    public String getGid() {return gid;}

    public int getMoveNumber() {return moveNumber;}

    public TileMove getTileMove() {return tileMove;}

    public ConstructionMoveInternal getConstructionMoveInternal() { return constructionMoveTransmission;}

    public GameMoveOutgoingTransmission(String gid, int moveNumber, TileMove tileMove, ConstructionMoveInternal constructionMoveTransmission){
        this.gid = gid;
        this.moveNumber = moveNumber;
        this.tileMove = tileMove;
        this.constructionMoveTransmission = constructionMoveTransmission;
    }
}
