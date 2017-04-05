package TigerIsland.UnitTests;

import TigerIsland.Color;
import TigerIsland.GameOutcome;
import TigerIsland.GameState;
import TigerIsland.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerCopyTest {
    @Test
    public void PlayerCopyDiffers(){
        Player testplayer = new Player(Color.BLACK);
        testplayer.setScore(100);

        Player cloneplayer = new Player(testplayer);

        testplayer.setScore(200);

        assertEquals(200, testplayer.getScore());
        assertEquals(100, cloneplayer.getScore());

    }
}
