package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GameTest {
    Player player_1 = null;
    Player player_2 = null;
    Game TestGameState = null;

    @Before
    public void createGame(){
       TestGameState = new Game();

       player_1 = TestGameState.getPlayerOne();
       player_2 = TestGameState.getPlayerTwo();

    }

    @Test
    public void gameNotOver(){
        assertEquals(GameOutcome.UNDETERMINED, TestGameState.getGameOutcome(player_1));
        assertEquals(GameOutcome.UNDETERMINED, TestGameState.getGameOutcome(player_2));
    }

    @Test
    public void playerOneWins(){
        player_1.setScore(100);

        TestGameState.setGameIsOver();

        assertEquals(GameOutcome.WIN, TestGameState.getGameOutcome(player_1));
        assertEquals(GameOutcome.LOSS, TestGameState.getGameOutcome(player_2));
    }

    @Test
    public void gameIsTied(){
        TestGameState.setGameIsOver();

        assertEquals(GameOutcome.TIE, TestGameState.getGameOutcome(player_1));
        assertEquals(GameOutcome.TIE, TestGameState.getGameOutcome(player_2));
    }

    @Test
    public void playerTwoWinsDueToPlayerOneHavingNegativeScore(){
        player_1.setScore(-1);

        TestGameState.setGameIsOver();

        assertEquals(GameOutcome.LOSS, TestGameState.getGameOutcome(player_1));
        assertEquals(GameOutcome.WIN, TestGameState.getGameOutcome(player_2));
    }
}
