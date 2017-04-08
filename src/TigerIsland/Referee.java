package TigerIsland;

public class Referee {
    PlayerController controller_1;
    PlayerController controller_2;
    OutputPlayerActions output;
    TileBag tileBag;

    public Referee(PlayerController controller_1, PlayerController controller_2, OutputPlayerActions output, TileBag tileBag) {
        this.controller_1 = controller_1;
        this.controller_2 = controller_2;
        this.output = output;
        this.tileBag = tileBag;
    }

    private void ControllerTakesTurn(GameStateEndOfTurn gameEndOfTurn, PlayerController controller){
        Tile tile = tileBag.drawTile();
        GameStateWTile gameStateWithTile = gameEndOfTurn.getChild(tile);
        gameEndOfTurn = controller.newGameState(gameStateWithTile);
        output.dispatchInformation(gameEndOfTurn);
    }

    public void Execute(){
        GameStateEndOfTurn gameEndOfTurn = GameStateEndOfTurn.createInitalGameState();

        while(true){

            ControllerTakesTurn(gameEndOfTurn, controller_1);

            if(gameEndOfTurn.checkForGameOver() || tileBag.getNumberOfTilesInBag() == 0){
                break;
            }

            ControllerTakesTurn(gameEndOfTurn, controller_2);
            if(gameEndOfTurn.checkForGameOver() || tileBag.getNumberOfTilesInBag() == 0){
                break;
            }
        }
    }
}
