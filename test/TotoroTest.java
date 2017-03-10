import org.junit.Assert;
import org.junit.Test;

public class TotoroTest {
    @Test
    public void shouldInitializeToThreeTotoro() {
        Player player = new Player(Color.WHITE);
        Assert.assertTrue(player.getTotoroCount() == 3);
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
