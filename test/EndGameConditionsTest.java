import TigerIsland.Game;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EndGameConditionsTest {
    @Test
    public void tieGame() {
        Game game = new Game();
        game.endGameConditions();

        assertNull( game.winner );
    }

    @Test
    public void pointBasedWin() {
        Game game = new Game();
        game.player1.setScore(4);
        game.endGameConditions();

        assertEquals(game.player1, game.winner);
    }

    @Test
    public void autoLoseEndGame() {
        Game game = new Game();

        game.player1.setAutoLoseScore();

        game.endGameConditions();

        assertEquals(game.player2, game.winner);

    }
}
