package TigerIsland;

public class Match {
    private Referee referee;
    private String gid;

    public String getGid() {
        return gid;
    }

    // Constructor
    Match(PlayerController ai, OpponentPlayerController opponent, String gid, OutputPlayerAI output) {
        this.gid = gid;
        TileBag tileBag = new NetworkTileBag(this.gid);
        this.referee = new Referee(ai, opponent, output, tileBag);
    }

    // Methods
    public void sendTileToAI(Tile tile) {
        referee.tellAIToMakeAMoveUsingGivenTile(tile);
    }
    public void updateGameStateUsingOpponentMove(MoveUpdate moveUpdate) {
        referee.updateGame(moveUpdate);
    }
}