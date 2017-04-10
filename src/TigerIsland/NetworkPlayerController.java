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