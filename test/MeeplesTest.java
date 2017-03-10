import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class MeeplesTest {

    @Test
    public void shouldInitializeToTwentyMeeples() throws Exception {
        Player player = new Player(Color.WHITE);
        assertEquals(20, player.getMeeplesCount());
    }

    @Test
    public void placeMeepleOnLevelZero() throws Exception {
        Hexagon hexagon = new Hexagon();
        Player player = new Player(Color.WHITE);
        Piece newMeeple = new Meeple(Color.WHITE);
        player.attemptToPlacePiece(newMeeple, hexagon);

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, hexagon.getPieces().size());
        assertEquals(0 , hexagon.getPopulation());
    }

    @Test
    public void placeMeepleOnLevelOne() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Coordinate coordinate = board.getHexagonNeighborCoordinate(new Coordinate(100,100), DirectionsHex.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player(Color.WHITE);
        Piece newMeeple = new Meeple(Color.WHITE);

        player.attemptToPlacePiece(newMeeple, hexagon);

        assertEquals(1, player.getScore());
        assertEquals(19, player.getMeeplesCount());
        assertEquals(1, hexagon.getPopulation());
    }

    @Test
    public void placeMeepleOnLevelTwo() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        board.placeTile(new Tile(Terrain.ROCK, Terrain.JUNGLE), DirectionsHex.RIGHT, new Coordinate(100,101));
        board.placeTile(new Tile(Terrain.ROCK, Terrain.ROCK), DirectionsHex.LOWERRIGHT, new Coordinate(100,101));
        Hexagon hexagon = board.getHexagon(new Coordinate(100, 100));
        Player player = new Player(Color.WHITE);
        Piece newMeeple = new Meeple(Color.WHITE);
        player.attemptToPlacePiece(newMeeple, hexagon);

        assertEquals(18, player.getMeeplesCount());
        assertEquals(4, player.getScore());
        assertEquals(2, hexagon.getPopulation());
    }

    /*
    @Test
    public void volcanoShouldPreventMeeplePlacement() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Hexagon hexagon = board.getHexagon(new Coordinate(100,100));
        Player player = new Player(Color.WHITE);
        Piece newMeeple = new Meeple(Color.WHITE);

        player.attemptToPlacePiece(newMeeple, hexagon);

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0,  hexagon.getPopulation());
    }
    */

    @Test
    public void meeplesShouldBeEliminatedWhenNuked() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        board.placeTile(new Tile(Terrain.JUNGLE, Terrain.ROCK), DirectionsHex.LOWERLEFT, new Coordinate(98,101));
        Hexagon hexagonOne = board.getHexagon(new Coordinate(99,101));
        Hexagon hexagonTwo = board.getHexagon(new Coordinate(99,100));
        Player player = new Player(Color.WHITE);
        Piece newMeeple = new Meeple(Color.WHITE);
        Piece newMeeple2 = new Meeple(Color.WHITE);

        player.attemptToPlacePiece(newMeeple, hexagonOne);
        player.attemptToPlacePiece(newMeeple2, hexagonTwo);

        board.placeTile(new Tile(Terrain.JUNGLE, Terrain.JUNGLE), DirectionsHex.RIGHT, new Coordinate(98,101));

        // Assert.assertTrue(hexagonOne.getPieces().size() , 0);
        // Assert.assertTrue(hexagonTwo.getPieces().size() , 0);
        assertEquals(0, hexagonOne.getPopulation());
        assertEquals(0, hexagonTwo.getPopulation());
        assertEquals(18, player.getMeeplesCount());
        assertEquals(2, player.getScore());
    }
}
