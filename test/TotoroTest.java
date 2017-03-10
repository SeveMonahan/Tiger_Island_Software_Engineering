import org.junit.Assert;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class TotoroTest {
    @Test
    public void shouldInitializeToThreeTotoro() {
        Player player = new Player(Color.WHITE);
        assertEquals(3, player.getTotoroCount());
    }
    /*
    @Test
    public void totoroShouldStopTilePlacement() {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        board.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE), DirectionsHex.RIGHT, new Coordinate(100,101));
        Hexagon hexagon = board.getHexagon(new Coordinate(100,100));
        hexagon.addPiece(new Piece(PieceType.TOTORO, Color.WHITE));
        boolean isValidMove = board.placeTile(new Tile(Terrain.ROCK, Terrain.ROCK), DirectionsHex.LOWERRIGHT, new Coordinate(100,101));
        Assert.assertFalse(isValidMove);
    }
    */
}
