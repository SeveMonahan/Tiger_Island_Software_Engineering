package TigerIsland.UnitTests;

import TigerIsland.Game;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTurnTest {
    @Test
    public void defaultGameStartsWithPlayer1Test() throws Exception {
        Game game = new Game();
        assertEquals(game.player1, game.getTurn());
    }

    @Test
    public void changeTurnsTest() throws Exception {
        Game game = new Game();
        assertEquals(game.player1, game.getTurn());
        game.changeTurn();
        assertEquals(game.player2,game.getTurn());
        game.changeTurn();
        assertEquals(game.player1,game.getTurn());
    }

    @Test
    public void placeHexTest() throws Exception {


    }
}
