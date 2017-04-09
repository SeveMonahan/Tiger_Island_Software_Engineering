package TigerIsland;

public class OutputPlayerAI implements OutputPlayerActions{
    private Color color;
    private String gid;
    private int moveNumber;

    public void setMoveNumber(int moveNumber) {
        this.moveNumber = moveNumber;
    }

    OutputPlayerAI(String gid, Color colors){
        this.gid = gid;
        this.color = color;
    }

    public void dispatchInformation(GameStateEndOfTurn gameStateEndOfTurn){}

    public void dispatchInformation(GameStateEndOfTurn gameStateEndOfTurn, PostMan postMan){
        if(gameStateEndOfTurn.getActivePlayer().getColor() == color) {
            int moveNumber = 0;
            TileMove tileMove = gameStateEndOfTurn.lastTileMove;
            ConstructionMoveInternal constructionMoveInternal = gameStateEndOfTurn.getLastConstructionMove();
            GameMoveOutgoingTransmission gameMoveOutgoingTransmission = new GameMoveOutgoingTransmission(gid, moveNumber, tileMove, constructionMoveInternal);

            postMan.mailAIMessages(gameMoveOutgoingTransmission);
        }
    }

}
