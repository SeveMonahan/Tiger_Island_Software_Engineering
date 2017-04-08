package TigerIsland;

public class NetworkPlayerController implements PlayerController {
    Color color;

    public NetworkPlayerController(Color color){
        this.color = color;
    }

    public GameStateEndOfTurn newGameState(GameStateWTile gameStateWTile){
        Tile tile = null; // Can I get this from: gameStateWTile.tile;   ?
        HexagonNeighborDirection hexagonNeighborDirection = null;
        Coordinate coordinate = null;
        TileMove tileMove = new TileMove(tile, hexagonNeighborDirection, coordinate);
        GameStateBeforeBuildAction gameStateBeforeBuildAction = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(gameStateWTile, tileMove);

        ConstructionMoveInternal constructionMove = null; // Most likely will come from Parser somehow
        GameStateEndOfTurn gameStateEndOfTurn = GameStateEndOfTurn.createGameStateFromConstructionMove(gameStateBeforeBuildAction, constructionMove);

        return gameStateEndOfTurn;
    }
}
