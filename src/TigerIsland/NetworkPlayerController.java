package TigerIsland;

public class NetworkPlayerController implements PlayerController {
    Color color;
    String gameID;
    PostMan postMan;

    public NetworkPlayerController(Color color, String gameID, PostMan postMan){
        this.color = color;
        this.gameID = gameID;
        this.postMan = postMan;
    }

    public GameStateEndOfTurn newGameState(GameStateWTile gameStateWTile){
        MoveInGameIncoming moveInGameIncoming = postMan.accessNetworkMailBox(gameID);

        while(moveInGameIncoming == null) {
            try {
                synchronized (postMan) {
                    postMan.wait();
                    moveInGameIncoming = postMan.accessNetworkMailBox(gameID);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Tile tile = moveInGameIncoming.getTileMove().getTile();
        HexagonNeighborDirection hexagonNeighborDirection = moveInGameIncoming.getTileMove().getDirection();
        Coordinate coordinateTile = moveInGameIncoming.getTileMove().getCoordinate();
        TileMove tileMove = new TileMove(tile, hexagonNeighborDirection, coordinateTile);
        GameStateBeforeBuildAction gameStateBeforeBuildAction = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(gameStateWTile, tileMove);

        Coordinate coordinateMove = moveInGameIncoming.getConstructionMoveTransmission().getCoordinate();
        ConstructionMoveInternal constructionMove = null;

        switch ( moveInGameIncoming.getConstructionMoveTransmission().getBuildOption() ) {
            case BUILDTIGER:
                constructionMove = new TigerConstructionMove(coordinateMove);
                break;
            case EXPANDSETTLEMENT:
                ExpandSettlementMoveTransmission expandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) moveInGameIncoming.getConstructionMoveTransmission();
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