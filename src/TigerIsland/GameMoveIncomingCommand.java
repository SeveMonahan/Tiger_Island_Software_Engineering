package TigerIsland;

public class GameMoveIncomingCommand {
    private String gid;
    private int moveNumber;
    private int time;
    private Tile tile;

    public String getGid() {return gid;}

    public int getMoveNumber() {return moveNumber;}

    public Tile getTile() {return tile;}

    public GameMoveIncomingCommand(String gid, int time, int moveNumber, Tile tile){
        this.gid = gid;
        this.time = time;
        this.moveNumber = moveNumber;
        this.tile = tile;
    }
}
