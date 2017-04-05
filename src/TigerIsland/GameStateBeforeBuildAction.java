package TigerIsland;

public class GameStateBeforeBuildAction extends GameState {

    GameStateBeforeBuildAction(GameState original, TileMove tilemove) {
        super(original);
        board.placeTile(tilemove);
    }

}

