package TigerIsland;

public class Match {
    // Members
    // TODO: Match probably doesnt need a postman anymore

    private Referee referee;
    private PostMan postMan;
    private TileBag tileBag;
    String gameID;

    // Constructor
    Match(PostMan postMan, PlayerController player_01, PlayerController player_02, String gameID, OutputPlayerAI output) {
        this.postMan = postMan;
        this.gameID = gameID;
        this.tileBag = new NetworkTileBag(this.postMan, this.gameID);
        this.referee = new Referee(player_01, player_02, output, tileBag);
    }

    //I am Assuming that PlayerController player_02 represents the networkplayer aka the one receiving the opponent's moves

    public void makeMove(Tile tileFromPostMan) { //this signals referee "hey bro it's our turn to make a move in your game!"
        this.referee.ControllerTakesTurn(false, tileFromPostMan);
    }

    public void opponentMove(Tile tileFromPostMan) { //this signals referee "hey bro this is what our opponent did!"
        this.referee.ControllerTakesTurn(true, tileFromPostMan);
    }


}