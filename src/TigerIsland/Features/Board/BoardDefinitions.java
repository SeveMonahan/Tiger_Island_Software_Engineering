package Board;

import TigerIsland.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import static org.junit.Assert.assertEquals;

public class BoardDefinitions {
    Board board = null;
    Hexagon[] neighbors = null;
    Hexagon TestHexagon = null;

    @Given("^I have initialized a board without a starting tile$")
    public void initBoard_TestBoard() {
        board = new Board();
        TestHexagon = new Hexagon();

        TestHexagon.changeTerrainTypeThoughExplosion(Terrain.ROCK);

        board.setHexagon(new Coordinate(102,101), TestHexagon);
        board.setHexagon(new Coordinate(100,101), TestHexagon);

        board.setHexagon(new Coordinate(101,100), TestHexagon);
        board.setHexagon(new Coordinate(101,102), TestHexagon);

        board.setHexagon(new Coordinate(102,100), TestHexagon);
        board.setHexagon(new Coordinate(102,102), TestHexagon);
    }

    @When("^I query a Hexagon's neighbors$")
    public void getAllNeighborTiles() {
        neighbors = board.getNeighboringHexagons(new Coordinate(101,101));
    }

    @Then("^I receive that Hexagon's neighbors as a result$")
    public void receiveAllNeighbors() {
        for(int i=0; i<6; i++){
            assertEquals(1, neighbors[i].getLevel());
        }
    }

}