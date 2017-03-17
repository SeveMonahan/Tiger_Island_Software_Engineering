package TigerIsland.test;

import TigerIsland.*;
import org.junit.Test;
import static junit.framework.TestCase.assertEquals;

public class TotoroTest {
    @Test
    public void shouldInitializeToThreeTotoro() {
        Player player = new Player(Color.WHITE);
        assertEquals(3, player.getTotoroCount());
    }

    @Test
    public void totoroShouldStopTilePlacement() {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        assertEquals(true, board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101))));
        Hexagon hexagon = board.getHexagon(new Coordinate(101,100));

        Player player = new Player(Color.WHITE);
        Piece newTotoro = new Totoro(Color.WHITE);

        assertEquals(true, player.attemptToPlacePiece(newTotoro, hexagon));

        boolean isValidMove = board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));

        assertEquals(false, isValidMove);
    }
}
