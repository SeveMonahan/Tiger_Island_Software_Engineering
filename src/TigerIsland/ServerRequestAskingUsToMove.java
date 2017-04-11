package TigerIsland;

public class ServerRequestAskingUsToMove {
    private String gid;
    private String moveID;
    private double time;
    private Tile tile;
    public void setGid(String gid) {
        this.gid = gid;
    }
    public String getGid() {return gid;}

    public String getMoveNumber() {return moveID;}

    public double getTime () {return time;}

    public Tile getTile() {return tile;}

    public ServerRequestAskingUsToMove(String gid, double time, String moveNumber, Tile tile){
        this.gid = gid;
        this.time = time;
        this.moveID = moveNumber;
        this.tile = tile;
    }
}
