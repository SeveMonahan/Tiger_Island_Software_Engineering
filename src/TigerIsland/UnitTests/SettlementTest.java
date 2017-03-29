import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SettlementTest {
    @Test
    public void settlementOfSizeOne() {
        Player player = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        Coordinate coordinateOne = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinateOne);
        player.placeMeepleOnHexagon(hexagon);
        Coordinate coordinateTwo = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.UPPERLEFT);
        hexagon = board.getHexagon(coordinateTwo);
        playerTwo.placeMeepleOnHexagon(hexagon);
        assertEquals(1,board.settlementSize(coordinateOne, player.getColor()));
    }
    @Test
    public void settlementOfSizeTwo() {
        Player player = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.RIGHT, new Coordinate(97, 101)));
        Coordinate playerOneMeepleOne = new Coordinate(99,100);
        Coordinate playerOneMeepleTwo = new Coordinate (99,101);
        Coordinate playerTwoMeepleOne = new Coordinate(98,100);
        Coordinate playerTwoMeepleTwo = new Coordinate (98,101);
        Hexagon hexagon = board.getHexagon(playerOneMeepleOne);
        player.placeMeepleOnHexagon(hexagon);
        hexagon = board.getHexagon(playerOneMeepleTwo);
        player.placeMeepleOnHexagon(hexagon);
        hexagon = board.getHexagon(playerTwoMeepleOne);
        playerTwo.placeMeepleOnHexagon(hexagon);
        hexagon = board.getHexagon(playerTwoMeepleTwo);
        playerTwo.placeMeepleOnHexagon(hexagon);
        assertEquals(2,board.settlementSize(playerOneMeepleOne,player.getColor()));
    }

    @Test
    public void settlementOfSizeOneShouldPreventTilePlacement() {

    }
}
