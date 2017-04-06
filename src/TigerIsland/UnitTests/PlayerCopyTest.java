package TigerIsland.UnitTests;

import TigerIsland.Color;
import TigerIsland.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerCopyTest {
    @Test
    public void PlayerCopyDiffers(){
        Player testplayer = new Player(Color.BLACK);
        testplayer.setScore(100);

        Player cloneplayer = Player.clonePlayer(testplayer);

        testplayer.setScore(200);

        assertEquals(200, testplayer.getScore());
        assertEquals(100, cloneplayer.getScore());

    }
}
