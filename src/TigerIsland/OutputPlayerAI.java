package TigerIsland;

public class OutputPlayerAI implements OutputPlayerActions{
    private Color color;
    private String gid;
    private PostMan postMan;

    OutputPlayerAI(String gid, Color color, PostMan postMan){
        this.gid = gid;
        this.color = color;
        this.postMan = postMan;
    }

    public void dispatchInformation(GameStateEndOfTurn gameStateEndOfTurn){
        if(gameStateEndOfTurn.getActivePlayer().getColor() == color) {
            int moveNumber = 0;
            TileMove tileMove = gameStateEndOfTurn.lastTileMove;
            ConstructionMoveInternal constructionMoveInternal = gameStateEndOfTurn.getLastConstructionMove();
            GameMoveOutgoingTransmission gameMoveOutgoingTransmission = new GameMoveOutgoingTransmission(gid, moveNumber, tileMove, constructionMoveInternal);

            postMan.mailAIMessages(gameMoveOutgoingTransmission);
        }
    }

}
