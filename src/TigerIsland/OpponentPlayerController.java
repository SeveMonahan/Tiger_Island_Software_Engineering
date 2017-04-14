package TigerIsland;

public class OpponentPlayerController implements PlayerController {
    Color color;
    String gameID;
    PostMan postMan;

    public OpponentPlayerController(Color color, String gameID, PostMan postMan) {
        this.color = color;
        this.gameID = gameID;
        this.postMan = postMan;
    }

    // Note: This is a consequence of me not knowing how to make these functions polymorphic.
    public GameStateEndOfTurn newGameState(GameStateWTile gameStateWTile) {
        return null;
    }
    public GameStateEndOfTurn updateGameState(GameStateWTile gameStateWTile, MoveUpdate moveUpdate) {
        Tile tile = moveUpdate.getTileMove().getTile();
        HexagonNeighborDirection hexagonNeighborDirection = moveUpdate.getTileMove().getDirection();
        Coordinate coordinateTile = moveUpdate.getTileMove().getCoordinate();
        TileMove tileMove = new TileMove(tile, hexagonNeighborDirection, coordinateTile);

        String original_tile_string = gameStateWTile.getTile().toString();
        String tilemove_string = tileMove.getTile().toString();
        assert(original_tile_string.equals(tilemove_string));

        GameStateBeforeBuildAction gameStateBeforeBuildAction = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(gameStateWTile, tileMove);

        Coordinate coordinateMove = moveUpdate.getConstructionMoveTransmission().getCoordinate();
        ConstructionMoveInternal constructionMove = null;

        switch ( moveUpdate.getConstructionMoveTransmission().getBuildOption() ) {
            case BUILDTIGER:
                constructionMove = new TigerConstructionMove(coordinateMove);
                break;
            case EXPANDSETTLEMENT:
                ExpandSettlementMoveTransmission expandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) moveUpdate.getConstructionMoveTransmission();
                Terrain terrain = expandSettlementMoveTransmission.getTerrain();
                constructionMove = new ExpandSettlementConstructionMove(coordinateMove, terrain);
                break;
            case FOUNDSETTLEMENT:
                constructionMove = new FoundSettlementConstructionMove(coordinateMove);
                break;
            case BUILDTOTORO:
                constructionMove = new TotoroConstructionMove(coordinateMove);
                break;
            case UNABLETOBUILD:
                constructionMove = new UnableToBuildConstructionMove();
                break;
        }
        GameStateEndOfTurn gameStateEndOfTurn = GameStateEndOfTurn.createGameStateFromConstructionMove(gameStateBeforeBuildAction, constructionMove);

        return gameStateEndOfTurn;
    }
}