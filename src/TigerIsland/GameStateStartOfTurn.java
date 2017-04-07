package TigerIsland;

public class GameStateStartOfTurn extends GameState {

    private GameStateStartOfTurn() {
       super();
    }

    public static GameStateStartOfTurn createInitalGameState() {
        return new GameStateStartOfTurn();
    }

    public GameStateWTile getChild(Tile tile){
        return new GameStateWTile(this, tile);
    }

    public static GameStateStartOfTurn createGameStateFromConstructionMove(GameStateBeforeBuildAction original, ConstructionMoveInternal move) {
        move.canPreformMove(original.playerWhoseTurn, original.board);
        return null;
    }

}
