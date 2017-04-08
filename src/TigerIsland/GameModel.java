package TigerIsland;

// The interface between the AI / network player and the GameState
public class GameModel {
    private GameState game;
    private Player player;

    GameModel(GameState game, Player player) {
        this.game = game;
        this.player = player;
    }

    public GameOutcome getGameOutcome(){
        return game.getGameOutcome(player);
    }
}
