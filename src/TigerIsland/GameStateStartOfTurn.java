package TigerIsland;

import java.util.ArrayList;

public class GameStateStartOfTurn extends GameState {

    private GameStateStartOfTurn() {
       super();
    }

    public static GameStateStartOfTurn createInitalGameState() {
        return new GameStateStartOfTurn();
    }

    public GameStateWTile getChild(Tile tile){
        return new GameStateWTile(this, tile);
    }

}
