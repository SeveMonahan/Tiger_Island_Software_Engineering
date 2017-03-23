package TigerIsland.test;

import TigerIsland.Color;
import TigerIsland.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class TigersTest {
    @Test
    public void shouldInitializeToTwoTigers() throws Exception {
        Player player = new Player(Color.WHITE);
        assertEquals(2, player.getTigerCount());
    }
}