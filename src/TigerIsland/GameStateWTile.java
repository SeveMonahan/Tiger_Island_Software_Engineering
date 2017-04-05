package TigerIsland;

public class GameStateWTile extends GameState {
    private Tile tile;

    GameStateWTile(GameState original, Tile tile) {
        super(original);
        this.tile = tile;
    }

}
