package TigerIsland;

public class GameStateEndOfTurn extends GameState {

    private GameStateEndOfTurn() {
       super();
    }

    private GameStateEndOfTurn(GameStateBeforeBuildAction original, ConstructionMoveInternal move) {
        super(original);
        move.makePreverifiedMove(playerWhoseTurn, board);
        this.changeTurn();
        lastConstructionMove = move;
    }

    public static GameStateEndOfTurn createInitalGameState() {
        return new GameStateEndOfTurn();
    }

    public GameStateWTile getChild(Tile tile){
        return new GameStateWTile(this, tile);
    }

    public static GameStateEndOfTurn createGameStateFromConstructionMove(GameStateBeforeBuildAction original, ConstructionMoveInternal move) {
        if(move.canPreformMove(original.playerWhoseTurn, original.board)){
            return new GameStateEndOfTurn(original, move);
        }
        return null;
    }

    public TileMove getLastTileMove(){
        return lastTileMove;
    }

    public ConstructionMoveInternal getLastConstructionMove(){
        return lastConstructionMove;
    }

}
