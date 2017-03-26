package Totoro;

import TigerIsland.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.TestCase;

import static org.junit.Assert.assertEquals;

public class TotoroDefinitions {
    Player player = null;
    Board board = null;
    int totoros;
    boolean isValidMove;

    @Given("^I have initialized player$")
    public void initPlayer() {
        player = new Player(Color.BLACK);
    }

    @When("^I query that player's Totoros$")
    public void queryPlayerMeeples() {
        totoros = player.getTotoroCount();
    }

    @Then("^that player has 3 Totoros$")
    public void playerHas3Meeples() {
        assertEquals(3, totoros);
    }

    @Given("^I have initialized a board$")
    public void initBoard() {
        Tile newTile = new Tile(Terrain.GRASSLAND, Terrain.JUNGLE);
        board = new Board(newTile);
    }

    @Given("^placed two tiles adjacent to each other with two touching hexagons across tiles$")
    public void placeTwoTiles() {
        assertEquals(true, board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101))));
    }

    @Given("^the tiles have adjacent volcanoes$")
    public void tilesHaveAdjacentVolcano() {
        Tile newTile = new Tile(Terrain.GRASSLAND, Terrain.JUNGLE);
        board = new Board(newTile);
    }

    @Given("^There is a Totoro on a non-volcano hexagon which is adjacent to both volcanos$")
    public void totoroAdjacentToBothVolcanos() {
        Player player = new Player(Color.WHITE);
        Piece newTotoro = new Totoro(Color.WHITE);
        Hexagon hexagon = board.getHexagon(new Coordinate(101,100));
        TestCase.assertEquals(true, player.attemptToPlacePiece(newTotoro, hexagon));
    }

    @When("^I attempt to place a tile on a volcano so it overlaps onto the other tile$")
    public void placeTileOverVolcano() {
        Tile newTile = new Tile(Terrain.GRASSLAND, Terrain.JUNGLE);
        board = new Board(newTile);
    }

    @When("^The tile would overwrite the totoro$")
    public void attempToOverwriteTotoro() {
        isValidMove = board.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.ROCK), HexagonNeighborDirection.LOWERRIGHT, new Coordinate(100, 101)));
    }

    @Then("^The operation should fail$")
    public void overwriteTotoroFails() {
        TestCase.assertEquals(false, isValidMove);
    }
}