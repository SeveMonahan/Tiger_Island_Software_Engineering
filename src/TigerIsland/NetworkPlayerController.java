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
        System.out.println(gameID + " is trying to look for moves in the mailbox!");
        GameMoveIncomingTransmission gameMoveIncomingTransmission = postMan.accessNetworkMailBox(gameID);
        while(gameMoveIncomingTransmission == null) {
            try {
                synchronized (postMan) {
                    System.out.println(gameID + " cant find a move, going to sleep!");
                    postMan.wait();
                    gameMoveIncomingTransmission = postMan.accessNetworkMailBox(gameID);
                    System.out.println(gameID + " found a move!");
                }
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

        switch ( gameMoveIncomingTransmission.getConstructionMoveTransmission().getBuildOption() ) {
            case BUILDTIGER:
                constructionMove = new TigerConstructionMove(coordinateMove);
                break;
            case EXPANDSETTLEMENT:
                ExpandSettlementMoveTransmission expandSettlementMoveTransmission = (ExpandSettlementMoveTransmission) gameMoveIncomingTransmission.getConstructionMoveTransmission();
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