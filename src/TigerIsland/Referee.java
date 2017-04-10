package TigerIsland;

public class Referee {
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

    public void execute(){

        while(true){

            ControllerTakesTurn(controller_1);

            if(gameEndOfTurn.checkForGameOver() || tileBag.getNumberOfTilesInBag() == 0){
                if (tileBag.getNumberOfTilesInBag() == 0) {
                    System.out.println("WTF");
                }
                break;
            }

            ControllerTakesTurn(controller_2);
            if(gameEndOfTurn.checkForGameOver() || tileBag.getNumberOfTilesInBag() == 0){
                System.out.println("wat2");
                break;
            }
        }
    }
}
