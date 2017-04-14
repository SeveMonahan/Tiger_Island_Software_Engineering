package TigerIsland;

public class Referee {
    PlayerController currentTurnTaker;
    PlayerController controller_1;
    PlayerController controller_2;
    OutputPlayerActions output;
    TileBag tileBag;
    GameStateEndOfTurn gameEndOfTurn;

    // Constructors
    public Referee(PlayerController controller_1, PlayerController controller_2, OutputPlayerActions output,
                   TileBag tileBag) {
        this.currentTurnTaker = controller_1;
        this.controller_1 = controller_1;
        this.controller_2 = controller_2;
        this.output = output;
        this.tileBag = tileBag;
        gameEndOfTurn = GameStateEndOfTurn.createInitalGameState();
    }

    public void ControllerTakesTurn(MoveInGameIncoming moveInGameIncoming){
        GameStateWTile gameStateWithTile = gameEndOfTurn.getChild(moveInGameIncoming.getTileMove().getTile());
        gameEndOfTurn = currentTurnTaker.newGameState(gameStateWithTile);
        output.dispatchInformation(gameEndOfTurn);

        // Swap turn takers
        if(currentTurnTaker == controller_1) {
            currentTurnTaker = controller_2;
        } else {
            currentTurnTaker = controller_1;
        }
    }
}
