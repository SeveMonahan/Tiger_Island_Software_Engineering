package TigerIsland;

public class Match {
    // Members
    // TODO: Match probably doesnt need a postman anymore

    private Referee referee;
    private PostMan postMan;
    private TileBag tileBag;
    String gameID;

    public String getGameID() {
        return gameID;
    }

    Match(PostMan postMan, PlayerController player_01, PlayerController player_02, String gameID, OutputPlayerAI output) {
        this.postMan = postMan;
        this.gameID = gameID;
        this.tileBag = new NetworkTileBag(this.postMan, this.gameID);
        this.referee = new Referee(player_01, player_02, output, tileBag);
    }

    public void makeMove() {
        this.referee.ControllerTakesTurn();
    }
}
