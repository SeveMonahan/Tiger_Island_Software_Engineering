package TigerIsland;

public class GameMoveIncomingTransmission {
    private String gid;
    private String moveNumber;
    private String pid;
    private TileMove tileMove;
    private ConstructionMoveTransmission constructionMoveTransmission;

    public String getGid() {return gid;}

    public String getMoveNumber() {return moveNumber;}

    public String getPid() {return pid;}

    public TileMove getTileMove() {return tileMove;}

    public ConstructionMoveTransmission getConstructionMoveTransmission() { return constructionMoveTransmission;}

    public GameMoveIncomingTransmission(String gid, String moveNumber, String pid, TileMove tileMove, ConstructionMoveTransmission constructionMoveTransmission){
        this.gid = gid;
        this.moveNumber = moveNumber;
        this.pid = pid;
        this.tileMove = tileMove;
        this.constructionMoveTransmission = constructionMoveTransmission;
    }
}
