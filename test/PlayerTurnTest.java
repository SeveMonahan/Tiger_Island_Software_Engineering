import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTurnTest {
    @Test
    public void isTurn() throws Exception {
        Player testPlayer = new Player(Color.BLACK, true);
        assertEquals(true, testPlayer.isTurn());
    }

    @Test
    public void turnSwitchTest() throws Exception {
        Player player1 = new Player(Color.WHITE, false);
        Player player2 = new Player(Color.BLACK, true);
        player1.turnSwitch(player2);
        assertEquals(true, player1.getTurn());
        assertEquals(false, player2.getTurn());
    }

    @Test
    public void canDrawFromTest() throws Exception {
        TileBag testBag = new TileBag();
        Player player1 = new Player(Color.BLACK, true);
        assertEquals(true, player1.canDrawFrom(testBag));
    }

    @Test
    public void canNotDrawFromTwice() throws Exception {
        TileBag testBag = new TileBag();
        Player player1 = new Player(Color.BLACK, true);
        Player player2 = new Player(Color.WHITE, false);
        //player1 draws and turn is over
        assertEquals(true, player1.canDrawFrom(testBag));
        player1.turnSwitch(player2);
        //player1 tries to draw again but is not allowed
        assertEquals(false, player1.canDrawFrom(testBag));

    }
}
