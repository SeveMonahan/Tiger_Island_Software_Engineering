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

    public void Execute(){
        GameStateEndOfTurn gameEndOfTurn = GameStateEndOfTurn.createInitalGameState();

        while(true){
            Tile tile = tileBag.drawTile();
            GameStateWTile gameStateWithTile = gameEndOfTurn.getChild(tile);
            gameEndOfTurn = controller_1.newGameState(gameStateWithTile);
            output.dispatchInformation(gameEndOfTurn);

            tile = tileBag.drawTile();
            gameStateWithTile = gameEndOfTurn.getChild(tile);
            gameEndOfTurn = controller_2.newGameState(gameStateWithTile);
            output.dispatchInformation(gameEndOfTurn);
        }
    }
}
