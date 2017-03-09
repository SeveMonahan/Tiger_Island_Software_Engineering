import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by jayC on 3/8/17.
 */
public class MeeplesTest {
    @Test
    public void shouldInitializeToTwentyMeeples() throws Exception {
        Player player = new Player();
        assertEquals(20, player.getMeeplesCount());
    }
    @Test
    public void placeMeepleOnLevelZero() throws Exception {
        Hexagon hexagon = new Hexagon();
        Player player = new Player();
        player.placeMeepleOnHexagon(hexagon);
        Assert.assertTrue(player.getScore() == 0);
        Assert.assertTrue(player.getMeeplesCount() == 20);
        Assert.assertTrue(hexagon.getPieces().size() == 0);
    }
    @Test
    public void placeMeepleOnLevelOne() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Coordinate coordinate = board.getHexagonNeighborCoordinate(new Coordinate(100,100), DirectionsHex.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player();
        player.placeMeepleOnHexagon(hexagon);
        Assert.assertTrue(player.getScore() == 1);
        Assert.assertTrue(player.getMeeplesCount() == 19);
        Assert.assertTrue(hexagon.getPieces().size() == 1);
    }
    @Test
    public void placeMeepleOnLevelTwo() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        board.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE), DirectionsHex.RIGHT, new Coordinate(100,101));
        board.placeTile(new Tile(Terrain.ROCK, Terrain.ROCK), DirectionsHex.LOWERRIGHT, new Coordinate(100,101));
        Hexagon hexagon = board.getHexagon(new Coordinate(100, 100));
        Player player = new Player();
        player.placeMeepleOnHexagon(hexagon);
        Assert.assertTrue(player.getMeeplesCount() == 18);
        Assert.assertTrue(player.getScore() == 4);
        Assert.assertTrue(hexagon.getPieces().size() == 2);
    }
    @Test
    public void volcanoShouldPreventMeeplePlacement() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Hexagon hexagon = board.getHexagon(new Coordinate(100,100));
        Player player = new Player();
        player.placeMeepleOnHexagon(hexagon);
        Assert.assertTrue(player.getScore() == 0);
        Assert.assertTrue(player.getMeeplesCount() == 20);
        Assert.assertTrue(hexagon.getPieces().size() == 0);
    }
    @Test
    public void meeplesShouldBeEliminatedWhenNuked() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        board.placeTile(new Tile(Terrain.JUNGLE, Terrain.ROCK), DirectionsHex.LOWERLEFT, new Coordinate(98,101));
        Hexagon hexagonOne = board.getHexagon(new Coordinate(99,101));
        Player player = new Player();
        player.placeMeepleOnHexagon(hexagonOne);
        Hexagon hexagonTwo = board.getHexagon(new Coordinate(99,100));
        player.placeMeepleOnHexagon(hexagonTwo);
        board.placeTile(new Tile(Terrain.JUNGLE, Terrain.JUNGLE), DirectionsHex.RIGHT, new Coordinate(98,101));
        Assert.assertTrue(hexagonOne.getPieces().size() == 0);
        Assert.assertTrue(hexagonTwo.getPieces().size() == 0);
        Assert.assertTrue(player.getMeeplesCount() == 18);
        Assert.assertTrue(player.getScore() == 2);
    }
}
