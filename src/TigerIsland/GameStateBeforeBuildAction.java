package TigerIsland;

public class GameStateBeforeBuildAction extends GameState {
    boolean success;

    public GameStateBeforeBuildAction(GameState original, TileMove tilemove) {
        super(original);
        success = board.placeTile(tilemove);
    }

    public boolean getSuccess(){
        return success;
    }

}

