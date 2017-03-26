package Hexagon;

import TigerIsland.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import static org.junit.Assert.assertEquals;

public class HexagonDefinitions {
    private Hexagon testHex = null;
    private int hexLevel = 0;

    @Given("^I have initialized a Hexagon and added a level to it$")
    public void initHexagon() {
        Board TestBoard = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
        TestBoard.placeTile(new TileMove(new Tile(Terrain.ROCK, Terrain.JUNGLE), HexagonNeighborDirection.RIGHT, new Coordinate(100, 101)));
        testHex = TestBoard.getHexagon(new Coordinate(100,100));
    }

    @When("^I query the Hexagon's Level$")
    public void queryHexLevel() {
        hexLevel = testHex.getLevel();
    }

    @Then("^I receive 1 as my result$")
    public void checkIf1() {
        assertEquals(1, hexLevel);
    }
}