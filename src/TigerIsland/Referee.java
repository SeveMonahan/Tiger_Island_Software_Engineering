package TigerIsland;

public class Referee implements Runnable {
    PlayerController controller_1;
    PlayerController controller_2;
    OutputPlayerActions output;
    TileBag tileBag;
    GameStateEndOfTurn gameEndOfTurn;

    public Referee(PlayerController controller_1, PlayerController controller_2, OutputPlayerActions output,
                   TileBag tileBag) {
        this.controller_1 = controller_1;
        this.controller_2 = controller_2;
        this.output = output;
        this.tileBag = tileBag;
        gameEndOfTurn = GameStateEndOfTurn.createInitalGameState();
    }

    private void ControllerTakesTurn(PlayerController controller){
        Tile tile = tileBag.drawTile();
        GameStateWTile gameStateWithTile = gameEndOfTurn.getChild(tile);
        gameEndOfTurn = controller.newGameState(gameStateWithTile);
        output.dispatchInformation(gameEndOfTurn);
    }

    @Override
    public void run(){

        while(true){

            ControllerTakesTurn(controller_1);

            if(gameEndOfTurn.checkForGameOver() || tileBag.getNumberOfTilesInBag() == 0){
                break;
            }

            ControllerTakesTurn(controller_2);
            if(gameEndOfTurn.checkForGameOver() || tileBag.getNumberOfTilesInBag() == 0){
                break;
            }
        }
    }
}
