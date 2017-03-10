import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PlayerTurnTest {
    @Test
    public void isTurn() throws Exception {
        Player testPlayer = new Player(Color.BLACK, true);
        assertEquals(true, testPlayer.isTurn());
    }
}
