import org.junit.Assert;
import org.junit.Test;

public class SettlementTest {
    @Test
    public void settlementOfSizeOne() {
        Board board = new Board(new Tile(Terrain.JUNGLE, Terrain.GRASS));
        Hexagon hexagon = board.getHexagon(new Coordinate(99,101));
        Player player = new Player(Color.WHITE);
        player.placeMeepleOnHexagon(hexagon);
        Settlement settlement = new Settlement(hexagon);
        Assert.assertTrue(settlement.getHexagons().size() == 1);
    }
    @Test
    public void settlementOfSizeTwo() {

    }
    @Test
    public void settlementOfSizeOneShouldPreventTilePlacement() {

    }
}
