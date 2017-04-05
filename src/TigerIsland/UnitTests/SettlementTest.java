package TigerIsland.UnitTests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SettlementTest {
    @Test
    public void settlementOfSizeOne() {
        Player player = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASSLAND), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        board.placeTile(startingTileMove);
        Coordinate coordinateOne = new Coordinate(100,100).getNeighboringCoordinate(HexagonNeighborDirection.LEFT);

        player.placeMeepleOnHexagon(coordinateOne, board);

        Coordinate coordinateTwo = new Coordinate(100,100).getNeighboringCoordinate(HexagonNeighborDirection.UPPERLEFT);

        playerTwo.placeMeepleOnHexagon(coordinateTwo, board);

        assertEquals(1,board.getSettlementSize(coordinateOne));
    }
    @Test
    public void settlementOfSizeTwo() {
        Player player = new Player(Color.WHITE);
        Player playerTwo = new Player(Color.BLACK);
        Board board = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASSLAND), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        board.placeTile(startingTileMove);
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.RIGHT, new Coordinate(97, 101)));
        Coordinate playerOneMeepleOne = new Coordinate(99,100);
        Coordinate playerOneMeepleTwo = new Coordinate (99,101);
        Coordinate playerTwoMeepleOne = new Coordinate(98,100);
        Coordinate playerTwoMeepleTwo = new Coordinate (98,101);

        player.placeMeepleOnHexagon(playerOneMeepleOne, board);

        player.placeMeepleOnHexagon(playerOneMeepleTwo, board);

        playerTwo.placeMeepleOnHexagon(playerTwoMeepleOne, board);

        playerTwo.placeMeepleOnHexagon(playerTwoMeepleTwo, board);

        assertEquals(2,board.getSettlementSize(playerOneMeepleOne));
    }
}
