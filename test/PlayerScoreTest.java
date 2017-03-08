import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerScoreTest {
    @Test
    public void scoreOfInitializedPlayer() {
        Player myPlayer = new Player();
        assertEquals(0, myPlayer.getScore() );
    }

    @Test
    public void scoreOfPlayerAfterAddition() {
        Player myPlayer = new Player();

        myPlayer.modifyScore(5);

        assertEquals(5, myPlayer.getScore() );
    }

    @Test
    public void scoreOfPlayerSettingNegative() {
        Player myPlayer = new Player();

        myPlayer.setAutoLoseScore();

        assertEquals(-1, myPlayer.getScore() );
    }

}
