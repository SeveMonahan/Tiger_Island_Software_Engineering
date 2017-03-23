package TigerIsland.test;

import TigerIsland.*;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class TigersTest {
    @Test
    public void shouldInitializeToTwoTigers() throws Exception {
        Player player = new Player(Color.WHITE);
        assertEquals(2, player.getTigerCount());
    }


    @Test
    public void placeTigerOnVolcano() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        Coordinate coordinate = new Coordinate(101, 100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player(Color.WHITE);

        player.placeTigerOnHexagon(hexagon);

        assertEquals(0, player.getScore());
        assertEquals(2, player.getTigerCount());
        assertEquals(0, hexagon.getPopulation());
    }
}