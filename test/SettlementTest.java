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
        assertEquals(1,hexagon.settlementSize(board,coordinate));
    }
    @Test
    public void settlementOfSizeTwo() { //need to implement function into the class, but it works!
        Player player = new Player(Color.WHITE);
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        player.placeMeepleOnHexagon(hexagon);
        coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.UPPERLEFT);
        hexagon = board.getHexagon(coordinate);
        player.placeMeepleOnHexagon(hexagon);
        assertEquals(2,hexagon.settlementSize(board,coordinate));
    }
    @Test
    public void settlementOfSizeOneShouldPreventTilePlacement() {

    }
}
