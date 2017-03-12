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
        int size = 0;
        HashMap map = new HashMap();
        Queue<Hexagon> hexagonQueue = new LinkedList<>();
        Coordinate currentCoordinate;
        Hexagon currentHexagon;
        Player player = new Player(Color.WHITE);
        Board board = new Board(new Tile(Terrain.BEACH, Terrain.GRASS));
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        player.placeMeepleOnHexagon(hexagon);
        if (hexagon.getPopulation() == 0) {
            return;
        }
        hexagonQueue.add(hexagon);
        while(!hexagonQueue.isEmpty()) {
            currentHexagon = hexagonQueue.remove();
            map.put(currentHexagon.hashCode(),true);
            size++;
            //have to create for loop for all neighbors
            currentCoordinate = coordinate.getHexagonNeighborCoordinate(HexagonNeighborDirection.UPPERLEFT);
            currentHexagon = board.getHexagon(currentCoordinate);
            if (currentHexagon.getPopulation() > 0 && map.containsKey(currentHexagon.hashCode())) {
                map.put(currentHexagon.hashCode(),1);
            }

        }
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
