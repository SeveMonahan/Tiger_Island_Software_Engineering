package TigerIsland.UnitTests;

import TigerIsland.Color;
import TigerIsland.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerScoreTest {
    @Test
    public void scoreOfInitializedPlayer() {
        Player myPlayer = new Player(Color.WHITE);
        assertEquals(0, myPlayer.getScore() );
    }

    @Test
    public void scoreOfPlayerAfterAddition() {
        Player myPlayer = new Player(Color.WHITE);

        myPlayer.setScore(5);

        assertEquals(5, myPlayer.getScore() );
    }

    @Test
    public void scoreOfPlayerSettingNegative() {
        Player myPlayer = new Player(Color.WHITE);

        myPlayer.setAutoLoseScore();

        assertEquals(-1, myPlayer.getScore() );
    }

}
