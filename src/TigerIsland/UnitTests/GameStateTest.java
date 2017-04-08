package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GameStateTest {
    private GameModel player_1_model = null;
    private GameModel player_2_model = null;

    private Player player_1 = null;
    private Player player_2 = null;

    GameState TestGameState = null;

    @Before
    public void createGame(){
        player_1 = new Player(Color.WHITE);
        player_2 = new Player(Color.BLACK);

        TestGameState = GameState.createGameStateWithInjectedPlayersForTesting(player_1, player_2);

        //player_1_model = TestGameState.getPlayerOneModel();
        //player_2_model = TestGameState.getPlayerTwoModel();

    }

    @Test
    public void gameNotOver(){
        assertEquals(GameOutcome.UNDETERMINED, player_1_model.getGameOutcome());
        assertEquals(GameOutcome.UNDETERMINED, player_2_model.getGameOutcome());
    }

    @Test
    public void playerOneWins(){
        player_1.setScore(100);

        TestGameState.setGameIsOver();

        assertEquals(GameOutcome.WIN, player_1_model.getGameOutcome());
        assertEquals(GameOutcome.LOSS, player_2_model.getGameOutcome());
    }

    @Test
    public void gameIsTied(){
        TestGameState.setGameIsOver();

        assertEquals(GameOutcome.TIE, player_1_model.getGameOutcome());
        assertEquals(GameOutcome.TIE, player_2_model.getGameOutcome());
    }

    @Test
    public void playerTwoWinsDueToPlayerOneHavingNegativeScore(){
        player_1.setScore(-1);

        TestGameState.setGameIsOver();

        assertEquals(GameOutcome.LOSS, player_1_model.getGameOutcome());
        assertEquals(GameOutcome.WIN, player_2_model.getGameOutcome());
    }
}
