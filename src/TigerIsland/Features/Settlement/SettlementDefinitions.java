package TigerIsland.Features.Settlement;

import TigerIsland.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;

public class SettlementDefinitions {
    Player player = null;
    Board board = null;
    int SettlementSize;

    @Given("^I have initialized a player$")
    public void initPlayer() {
        player = new Player(Color.BLACK);
    }

    @Given("^initialized a board and placed a tile on it$")
    public void initBoard() {
        board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
    }

    @Given("^I have placed a meeple on a hexagon on the board$")
    public void placeMeeple() {
        Coordinate coordinateOne = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinateOne);
        player.placeMeepleOnHexagon(hexagon);
    }

    @Then("^I receive 1$")
    public void settlementSizeCheck1() {
        assertEquals(1, SettlementSize);
    }

    @Given("^I have placed a meeple on both non-volcanic level 1 hexagons on the board$")
    public void placeAllMeeplesAllowed() {
        Coordinate coordinate = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT);
        Hexagon hexagon = board.getHexagon(coordinate);
        player.placeMeepleOnHexagon(hexagon);

        Coordinate coordinate2 = new Coordinate(100,100).getHexagonNeighborCoordinate(HexagonNeighborDirection.UPPERLEFT);
        Hexagon hexagon2 = board.getHexagon(coordinate2);
        player.placeMeepleOnHexagon(hexagon2);
    }

    @When("^I ask for the settlement size of a Meeple’s hexagon$")
    public void queryHexagonMeeples() {
        SettlementSize = board.settlementSize( new Coordinate(100, 100).getHexagonNeighborCoordinate(HexagonNeighborDirection.LEFT),player.getColor());
    }

    @Then("^I receive 2$")
    public void setSettlementSizeCheck2() {
        assertEquals(2,SettlementSize);
    }

}

/*
Scenario: Settlement Size of 1 stops tile placement
X    Given I have initialized a board
	And placed two tiles adjacent to each other with two touching hexagons across tiles
	And the tiles have adjacent volcanoes
	And There is a meeple on a non-volcano hexagon which is adjacent to both volcanos
	And There are no other meeples on the board
    When I attempt to place a tile on a volcano so it overlaps onto the other tile
	And The tile would overwrite the meeple
    Then The operation should fail
 */