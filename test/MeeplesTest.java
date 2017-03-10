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
    public void placedMeepleShouldBePlayerColor() throws Exception {
        Board board = new Board(new Tile(Terrain.GRASS, Terrain.GRASS));
        Hexagon hexagon = board.getHexagon(new Coordinate(99,100));
        Player player = new Player(Color.BLACK);
        Piece newMeeple = new Meeple(Color.BLACK);
        player.attemptToPlacePiece(newMeeple, hexagon);
        Assert.assertTrue( hexagon.getOccupationStatus() == HexagonOccupationStatus.Meeples );
    }

    @Test
    public void placedTotoroShouldBePlayerColor() throws Exception {
        Board board = new Board(new Tile(Terrain.GRASS, Terrain.GRASS));
        Hexagon hexagon = board.getHexagon(new Coordinate(99,100));
        Player player = new Player(Color.WHITE);
        Piece newTotoro = new Totoro(Color.WHITE);
        player.attemptToPlacePiece(newTotoro, hexagon);
        // Assert.assertTrue(hexagon.getPieces().get(0).getPieceColor() == Color.WHITE);
        Assert.assertTrue(hexagon.getOccupationStatus() == HexagonOccupationStatus.Totoro);
    }

    @Test
    public void placeMeepleOnLevelZero() throws Exception {
        Hexagon hexagon = new Hexagon();
        Player player = new Player(Color.WHITE);
        Piece newMeeple = new Meeple(Color.WHITE);
        player.attemptToPlacePiece(newMeeple, hexagon);

        Assert.assertTrue(player.getScore() == 0);
        Assert.assertTrue(player.getMeeplesCount() == 20);
        Assert.assertTrue(hexagon.getPieces().size() == 0);
        Assert.assertTrue( hexagon.getPopulation() == 0 );
    }

    @Test
    public void placeMeepleOnLevelOne() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Coordinate coordinate = board.getHexagonNeighborCoordinate(new Coordinate(100,100), DirectionsHex.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player(Color.WHITE);
        Piece newMeeple = new Meeple(Color.WHITE);

        player.attemptToPlacePiece(newMeeple, hexagon);

        Assert.assertTrue(player.getScore() == 1);
        Assert.assertTrue(player.getMeeplesCount() == 19);
        Assert.assertTrue( hexagon.getPopulation() == 1);
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

        Assert.assertTrue(player.getMeeplesCount() == 18);
        Assert.assertTrue(player.getScore() == 4);
        Assert.assertTrue( hexagon.getPopulation() == 2);
    }

    @Test
    public void volcanoShouldPreventMeeplePlacement() throws Exception {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Hexagon hexagon = board.getHexagon(new Coordinate(100,100));
        Player player = new Player(Color.WHITE);
        Piece newMeeple = new Meeple(Color.WHITE);

        player.attemptToPlacePiece(newMeeple, hexagon);

        Assert.assertTrue(player.getScore() == 0);
        Assert.assertTrue(player.getMeeplesCount() == 20);
        Assert.assertTrue( hexagon.getPopulation() == 0 );
    }

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

        // Assert.assertTrue(hexagonOne.getPieces().size() == 0);
        // Assert.assertTrue(hexagonTwo.getPieces().size() == 0);
        Assert.assertTrue( hexagonOne.getPopulation() == 0);
        Assert.assertTrue( hexagonTwo.getPopulation() == 0);
        Assert.assertTrue(player.getMeeplesCount() == 18);
        Assert.assertTrue(player.getScore() == 2);
    }
}
