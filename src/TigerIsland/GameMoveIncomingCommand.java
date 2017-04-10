package TigerIsland;

public class GameMoveIncomingCommand {
    private String gid;
    private int moveNumber;
    private double time;
    private Tile tile;
    public void setGid(String gid) {
        this.gid = gid;
    }
    public String getGid() {return gid;}

    public int getMoveNumber() {return moveNumber;}

    public double getTime () {return time;}

    public Tile getTile() {return tile;}

    public GameMoveIncomingCommand(String gid, double time, int moveNumber, Tile tile){
        this.gid = gid;
        this.time = time;
        this.moveNumber = moveNumber;
        this.tile = tile;
    }
}
