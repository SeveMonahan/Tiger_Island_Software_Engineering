import org.junit.Assert;
import java.util.*;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class SettlementTest {
    /*
    Pseudocode for the Hex.settlementSize():
    hashmap.add(hex) //I think a hashmap could work as a set of visited hexagons
    queue.add(hex)
    while (queue not empty) {
        current = queue.dequeue();
        size++;
        for (every unvisited neighbor of current with population > 0) {
            enqueue neighbor
        }
     }
     */

    @Test
    public void settlementOfSizeOne() {
        //Not really a real settlement test
        Player player = new Player(Color.WHITE);
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        player.placeMeepleOnHexagon(hexagon);
        assertEquals(1,board.settlementSize(coordinate, player.getColor()));
    }
    @Test
    public void settlementOfSizeTwo() {
        //player One and Two have settlements that are adjacent to each other

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
