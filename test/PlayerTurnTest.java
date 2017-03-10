import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTurnTest {
    @Test
    public void defaultGameStartsWithPlayer1() throws Exception {
        Game game = new Game();
        assertEquals(game.player1, game.getTurn());
    }


}
