package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TigersTest {
    @Test
    public void shouldInitializeToTwoTigers() throws Exception {
        Player player = new Player(Color.WHITE);
        assertEquals(2, player.getTigerCount());
    }

    @Test
    public void placeTigerOnLevelZero() throws Exception {
        Hexagon hexagon = new Hexagon();
        Player player = new Player(Color.WHITE);
        player.placeTigerOnHexagon(hexagon);

        Assert.assertEquals(0, player.getScore());
        Assert.assertEquals(2, player.getTigerCount());
        assertEquals(HexagonOccupationStatus.empty, hexagon.getOccupationStatus());
    }

    @Test
    public void placeTigerOnLevelOne() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player(Color.WHITE);

        player.placeTigerOnHexagon(hexagon);

        Assert.assertEquals(0, player.getScore());
        Assert.assertEquals(2, player.getTigerCount());
        assertEquals(HexagonOccupationStatus.empty, hexagon.getOccupationStatus());
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
        assertEquals(HexagonOccupationStatus.empty, hexagon.getOccupationStatus());
    }
}