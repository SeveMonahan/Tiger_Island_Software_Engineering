package TigerIsland;

public class GameMoveTransmission {
    private int gid;
    private int moveNumber;
    private TileMove tileMove;
    private ConstructionMoveTransmission constructionMoveTransmission;

    public GameMoveTransmission(int gid, int moveNumber, TileMove tileMove, ConstructionMoveTransmission constructionMoveTransmission){
        this.gid = gid;
        this.moveNumber = moveNumber;
        this.tileMove = tileMove;
        this.constructionMoveTransmission = constructionMoveTransmission;
    }
}
