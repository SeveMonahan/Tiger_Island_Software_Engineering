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
            String moveNumber = "**********move_id**********";
            TileMove tileMove = gameStateEndOfTurn.getLastTileMove();
            ConstructionMoveInternal constructionMoveInternal = gameStateEndOfTurn.getLastConstructionMove();
            GameMoveOutgoingTransmission gameMoveOutgoingTransmission = new GameMoveOutgoingTransmission(gid, moveNumber, tileMove, constructionMoveInternal);

            postMan.mailAIMessages(gameMoveOutgoingTransmission);
        }
    }

}
