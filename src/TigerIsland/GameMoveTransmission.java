package TigerIsland;

public class GameMoveTransmission {
    private String gid;
    private int moveNumber;
    private TileMove tileMove;
    private ConstructionMoveTransmission constructionMoveTransmission;

    public String getGid() {return gid;}

    public int getMoveNumber() {return moveNumber;}

    public TileMove getTileMove() {return tileMove;}

    public ConstructionMoveTransmission getConstructionMoveTransmission() { return constructionMoveTransmission;}

    public GameMoveTransmission(String gid, int moveNumber, TileMove tileMove, ConstructionMoveTransmission constructionMoveTransmission){
        this.gid = gid;
        this.moveNumber = moveNumber;
        this.tileMove = tileMove;
        this.constructionMoveTransmission = constructionMoveTransmission;
    }
}
