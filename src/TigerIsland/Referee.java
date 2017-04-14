package TigerIsland;

public class Referee {
    // Members
    PlayerController ai;
    OpponentPlayerController opponent;
    OutputPlayerActions output;
    TileBag tileBag;
    GameStateEndOfTurn gameEndOfTurn;

    // Constructors
    public Referee(PlayerController ai, OpponentPlayerController opponent, OutputPlayerActions output,
                   TileBag tileBag) {
        this.ai = ai;
        this.opponent = opponent;
        this.output = output;
        this.tileBag = tileBag;
        gameEndOfTurn = GameStateEndOfTurn.createInitalGameState();
    }

    // Methods
    public void tellAIToMakeAMoveUsingGivenTile(Tile tile) {
        // "Draw tile."
        tileBag.drawTile();
        GameStateWTile gameStateWithTile = gameEndOfTurn.getChild(tile);

        gameEndOfTurn = ai.newGameState(gameStateWithTile);

        if (gameEndOfTurn.getLastTileMove().getTile() != tile) throw new AssertionError();
        output.dispatchInformation(gameEndOfTurn);
        if(gameEndOfTurn.checkForGameOver() || tileBag.getNumberOfTilesInBag() == 0) {
            // TODO: Report that the game is over.
        }
    }
    public void updateGame(MoveUpdate moveUpdate) {
        // "Draw tile."
        Tile tile = moveUpdate.getTileMove().getTile();
        tileBag.drawTile();

        GameStateWTile gameStateWithTile = gameEndOfTurn.getChild(tile);

        gameEndOfTurn = opponent.updateGameState(gameStateWithTile, moveUpdate);

        if (gameEndOfTurn.getLastTileMove().getTile() != tile) throw new AssertionError();
        output.dispatchInformation(gameEndOfTurn);
        if(gameEndOfTurn.checkForGameOver() || tileBag.getNumberOfTilesInBag() == 0) {
            // TODO: Report that the game is over.
        }
    }
}
