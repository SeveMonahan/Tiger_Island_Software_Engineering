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

    public void ControllerTakesTurn(){
        Tile tile = tileBag.drawTile();
        GameStateWTile gameStateWithTile = gameEndOfTurn.getChild(tile);
        gameEndOfTurn = currentTurnTaker.newGameState(gameStateWithTile);
        output.dispatchInformation(gameEndOfTurn, currentTurnTaker);

       // System.out.println("Active player meeples: " + gameEndOfTurn.getActivePlayer().getMeeplesCount() );
       // System.out.println("Inactive player meeples: " + gameEndOfTurn.getInactivePlayer().getMeeplesCount() );

       boolean trigger = gameEndOfTurn.getActivePlayer().triggeredGameEnd();
       if(trigger) {
           System.out.println("########### Game Should have Ended #############");
       }

        // Swap turn takers
        if(currentTurnTaker == controller_1) {
            currentTurnTaker = controller_2;
        } else {
            currentTurnTaker = controller_1;
        }
    }
}
