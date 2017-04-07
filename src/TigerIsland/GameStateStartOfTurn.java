package TigerIsland;

public class GameStateStartOfTurn extends GameState {

    private GameStateStartOfTurn() {
       super();
    }

    private GameStateStartOfTurn(GameStateBeforeBuildAction original, ConstructionMoveInternal move) {
        super(original);
        move.makePreverifiedMove(playerWhoseTurn, board);
    }

    public static GameStateStartOfTurn createInitalGameState() {
        return new GameStateStartOfTurn();
    }

    public GameStateWTile getChild(Tile tile){
        return new GameStateWTile(this, tile);
    }

    public static GameStateStartOfTurn createGameStateFromConstructionMove(GameStateBeforeBuildAction original, ConstructionMoveInternal move) {
        if(move.canPreformMove(original.playerWhoseTurn, original.board)){
            return new GameStateStartOfTurn(original, move);
        }
        return null;
    }

}
