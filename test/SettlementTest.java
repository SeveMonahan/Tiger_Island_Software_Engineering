import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class SettlementTest {
    @Test
    public void settlementOfSizeOne() {
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player(Color.WHITE);
        Piece newMeeple = new Meeple(Color.WHITE);

        player.startSettlement(hexagon);

        assertEquals(1, player.getScore());
        assertEquals(19, player.getMeeplesCount());
        assertEquals(1, hexagon.getPopulation());
    }
    @Test
    public void settlementOfSizeTwo() {

    }
    @Test
    public void settlementOfSizeOneShouldPreventTilePlacement() {

    }
}
