package TigerIsland.Tests;

import TigerIsland.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MeeplesTest {

    @Test
    public void shouldInitializeToTwentyMeeples() throws Exception {
        Player player = new Player(Color.WHITE);
        assertEquals(20, player.getMeeplesCount());
    }

    @Test
    public void placeMeepleOnLevelZero() throws Exception {
        Hexagon hexagon = new Hexagon();
        Player player = new Player(Color.WHITE);
        player.placeMeepleOnHexagon(hexagon);

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0 , hexagon.getPopulation());
    }

    @Test
    public void placeMeepleOnLevelOne() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(hexagon);

        assertEquals(1, player.getScore());
        assertEquals(19, player.getMeeplesCount());
        assertEquals(1, hexagon.getPopulation());
    }

    @Test
    public void startMeepleSettlement() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player(Color.WHITE);

        player.placeSettlement(hexagon);

        assertEquals(1, player.getScore());
        assertEquals(19, player.getMeeplesCount());
        assertEquals(1, hexagon.getPopulation());
    }

    @Test
    public void startMeepleSettlementOnVolcano() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        Coordinate coordinate = new Coordinate(101,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        Player player = new Player(Color.WHITE);

        player.placeSettlement(hexagon);

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, hexagon.getPopulation());
    }

    @Test
    public void placeMeepleOnLevelTwo() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));
        Hexagon hexagon = board.getHexagon(new Coordinate(100, 100));
        Player player = new Player(Color.WHITE);

        player.placeSettlement(hexagon);

        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(0, hexagon.getPopulation());
    }

    @Test
    public void cantStartSettlementOverTopExistingSettlementSameColor() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));
        Hexagon hexagon = board.getHexagon(new Coordinate(100, 100));
        Player player = new Player(Color.WHITE);

        player.placeSettlement(hexagon);

        assertEquals(player.placeSettlement(hexagon), false);
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(0, hexagon.getPopulation());
    }

    @Test
    public void cantStartSettlementOverTopExistingSettlementDifferentColor() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));
        Hexagon hexagon = board.getHexagon(new Coordinate(100, 100));
        Player player = new Player(Color.WHITE);
        player.placeSettlement(hexagon);

        Player player2 = new Player(Color.BLACK);

        assertEquals(player2.placeSettlement(hexagon), false);
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(0, hexagon.getPopulation());
    }

    @Test
    public void tryToStartSettlementOnLevelTwo() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));
        Hexagon hexagon = board.getHexagon(new Coordinate(100, 100));
        Player player = new Player(Color.WHITE);
        player.placeSettlement(hexagon);

        assertEquals(20, player.getMeeplesCount());
        assertEquals(0, player.getScore());
        assertEquals(0, hexagon.getPopulation());
    }

    @Test
    public void volcanoShouldPreventMeeplePlacement() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        Hexagon hexagon = board.getHexagon(new Coordinate(100,100));
        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(hexagon);

        assertEquals(0, player.getScore());
        assertEquals(20, player.getMeeplesCount());
        assertEquals(0,  hexagon.getPopulation());
    }

    @Test
    public void meeplesShouldBeEliminatedWhenNuked() throws Exception {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        board.placeTile(new TileMove(new Tile(Terrain.JUNGLE, Terrain.ROCK), HexagonNeighborDirection.LOWERLEFT, new Coordinate(98, 101)));
        Hexagon hexagonOne = board.getHexagon(new Coordinate(99,101));
        Hexagon hexagonTwo = board.getHexagon(new Coordinate(99,100));
        Player player = new Player(Color.WHITE);

        player.placeMeepleOnHexagon(hexagonOne);
        player.placeMeepleOnHexagon(hexagonTwo);

        board.placeTile(new TileMove(new Tile(Terrain.JUNGLE, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(98, 101)));

        assertEquals(0, hexagonOne.getPopulation());
        assertEquals(0, hexagonTwo.getPopulation());
        assertEquals(18, player.getMeeplesCount());
        assertEquals(2, player.getScore());
    }
}