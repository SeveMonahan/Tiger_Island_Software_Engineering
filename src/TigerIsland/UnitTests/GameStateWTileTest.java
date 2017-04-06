package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GameStateWTileTest {
    @Test
    public void testChildrenStart(){
        GameState testGamestate = new GameState();

        GameStateWTile testGamestateTile = testGamestate.getChildren(new Tile(Terrain.JUNGLE, Terrain.JUNGLE));

        ArrayList<GameStateBeforeBuildAction> final_children = testGamestateTile.getChildren();

        int num_children = final_children.toArray().length;

        assertEquals(90, num_children);

    }
}
