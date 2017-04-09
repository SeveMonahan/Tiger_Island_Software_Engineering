package TigerIsland;

public class NetworkPlayerController implements PlayerController {
    Color color;
    String gameID;

    public NetworkPlayerController(Color color){
        this.color = color;
    }

    @Deprecated
    public GameStateEndOfTurn newGameState(GameStateWTile gameStateWTile){
        Tile tile = null;
        HexagonNeighborDirection hexagonNeighborDirection = null;
        Coordinate coordinate = null;
        TileMove tileMove = new TileMove(tile, hexagonNeighborDirection, coordinate);
        GameStateBeforeBuildAction gameStateBeforeBuildAction = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(gameStateWTile, tileMove);

        ConstructionMoveInternal constructionMove = null; // Most likely will come from Parser somehow
        GameStateEndOfTurn gameStateEndOfTurn = GameStateEndOfTurn.createGameStateFromConstructionMove(gameStateBeforeBuildAction, constructionMove);

        return gameStateEndOfTurn;
    }

    // PostMan constructor
    public GameStateEndOfTurn newGameState(GameStateWTile gameStateWTile, PostMan postMan, String gameID){
        GameMoveIncomingTransmission gameMoveIncomingTransmission = postMan.accessNetworkMailBox(gameID);
        while(gameMoveIncomingTransmission == null) {
            try {
                postMan.wait();
                gameMoveIncomingTransmission = postMan.accessNetworkMailBox(gameID);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Tile tile = gameMoveIncomingTransmission.getTileMove().getTile();
        HexagonNeighborDirection hexagonNeighborDirection = gameMoveIncomingTransmission.getTileMove().getDirection();
        Coordinate coordinateTile = gameMoveIncomingTransmission.getTileMove().getCoordinate();
        TileMove tileMove = new TileMove(tile, hexagonNeighborDirection, coordinateTile);
        GameStateBeforeBuildAction gameStateBeforeBuildAction = GameStateBeforeBuildAction.createGameStateBeforeBuildAction(gameStateWTile, tileMove);

        Coordinate coordinateMove = gameMoveIncomingTransmission.getConstructionMoveTransmission().getCoordinate();
        ConstructionMoveInternal constructionMove = null;

        switch (gameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption()){
            case BUILDTIGER:
                constructionMove = new TigerConstructionMove(coordinateMove);
            case EXPANDSETTLEMENT:
                ExpandSettlementMoveTransmission expandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) gameMoveIncomingTransmission.getConstructionMoveTransmission();
                Terrain terrain = expandSettlementMoveTransmission.getTerrain();
                constructionMove = new ExpandSettlementConstructionMove(coordinateMove, terrain);
            case FOUNDSETTLEMENT:
                constructionMove = new FoundSettlementConstructionMove(coordinateMove);
            case BUILDTOTORO:
                constructionMove = new TotoroConstructionMove(coordinateMove);
            case UNABLETOBUILD:
                constructionMove = null;
        }
        GameStateEndOfTurn gameStateEndOfTurn = GameStateEndOfTurn.createGameStateFromConstructionMove(gameStateBeforeBuildAction, constructionMove);

        return gameStateEndOfTurn;
    }
}