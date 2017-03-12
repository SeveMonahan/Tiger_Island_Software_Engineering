import org.junit.Assert;
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
        assertEquals(1, hexagon.getPopulation());
    }
    @Test
    public void settlementOfSizeTwo() {
        //Need to implement algorithm above
        Player player = new Player(Color.WHITE);
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Coordinate coordinate1 = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.UPPERLEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Hexagon hexagon1 = board.getHexagon(coordinate1);
        player.placeMeepleOnHexagon(hexagon);
        player.placeMeepleOnHexagon(hexagon1);

    }
    @Test
    public void settlementOfSizeOneShouldPreventTilePlacement() {

    }
}
