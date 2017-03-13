import org.junit.Assert;
import java.util.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SettlementTest {
    @Test
    public void settlementOfSizeOne() {
        Player player = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        player.placeMeepleOnHexagon(hexagon);
        coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.UPPERLEFT);
        hexagon = board.getHexagon(coordinate);
        playerTwo.placeMeepleOnHexagon(hexagon);
        assertEquals(1,board.settlementSize(coordinate, playerTwo.getColor()));

    }
    @Test
    public void settlementOfSizeTwo() {
        Player player = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.RIGHT, new Coordinate(98, 101)));
        Coordinate playerOneMeepleOne = new Coordinate(99,100);
        Coordinate playerOneMeepleTwo = new Coordinate (99,101);
        Coordinate playerTwoMeepleOne = new Coordinate(99,102);
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
