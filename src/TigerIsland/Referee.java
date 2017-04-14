package TigerIsland;

public class Referee {
    // Members
    PlayerController controller_1;
    PlayerController controller_2;
    OutputPlayerActions output;
    TileBag tileBag;
    GameStateEndOfTurn gameEndOfTurn;

    // Constructors
    public Referee(PlayerController controller_1, PlayerController controller_2, OutputPlayerActions output,
                   TileBag tileBag) {
        this.controller_1 = controller_1;
        this.controller_2 = controller_2;
        this.output = output;
        this.tileBag = tileBag;
        gameEndOfTurn = GameStateEndOfTurn.createInitalGameState();
    }

    public void ControllerTakesTurn(boolean pickingControllerTwo, Tile tile){
        PlayerController controller = this.controller_1;
        if (pickingControllerTwo) {
            controller = this.controller_2;
        }
        GameStateWTile gameStateWithTile = gameEndOfTurn.getChild(tile);
        gameEndOfTurn = controller.newGameState(gameStateWithTile);
        if (gameEndOfTurn.getLastTileMove().getTile() != tile) throw new AssertionError();
        output.dispatchInformation(gameEndOfTurn);
    }
}
