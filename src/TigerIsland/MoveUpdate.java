package TigerIsland;

public class MoveUpdate
{
    // Members
    private String gid;
    private String moveID;
    private String pid;
    private TileMove tileMove;
    private ConstructionMoveTransmission constructionMoveTransmission;

    // Getters
    public String getGid() { return gid; }
    public String getMoveID() { return moveID; }
    public String getPid() { return pid; }
    public TileMove getTileMove() { return tileMove; }
    public ConstructionMoveTransmission getConstructionMoveTransmission() { return constructionMoveTransmission; }

    // Setters
    public void setGid(String gid) {
        this.gid = gid;
    }

    // Constructor
    public MoveUpdate(String gid, String moveID, String pid, TileMove tileMove, ConstructionMoveTransmission constructionMoveTransmission){
        this.gid = gid;
        this.moveID = moveID;
        this.pid = pid;
        this.tileMove = tileMove;
        this.constructionMoveTransmission = constructionMoveTransmission;
    }
}
