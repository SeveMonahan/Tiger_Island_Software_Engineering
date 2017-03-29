package Settlement;

import TigerIsland.*;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AnyOf.anyOf;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;

public class SettlementDefinitions {
    private TileBag testBag = null;
    private Tile testTile = null;
    private Board board = null;
    private Player playerOne = null;
    private Player playerTwo = null;

    @Given("^I have initialized a board$")
    public void newBoard() throws Throwable {
        board = new Board();
        Assert.assertNotNull(board);
    }

    @Given("^I have initialized the players$")
    public void newPlayer() throws Throwable {
        playerOne = new Player(Color.WHITE);
        playerTwo = new Player(Color.BLACK);
        Assert.assertNotNull(playerOne);
        Assert.assertNotNull(playerTwo);
    }


    @Then("^moo$")
    public void moo() {
        System.out.println("moo");
    }

    @When("^I placed a tile without restrictions at (\\d+), (\\d+) with terrain (.*), (.*) and direction (.*)$")
    public void placeTile(int arg1, int arg2, String arg3, String arg4, String arg5) {
        HexagonNeighborDirection dir = HexagonNeighborDirection.valueOf(arg5);
        Terrain terrainOne = Terrain.valueOf(arg3);
        Terrain terrainTwo = Terrain.valueOf(arg4);
        board.placeTileNoRestrictions(
                new TileMove(new Tile(terrainOne, terrainTwo), dir, new Coordinate(arg1, arg2)));
    }

    @When("^Player (\\d+) places a meeple at (\\d+),(\\d+)$")
    public void playerPlacesAMeepleAt(int arg0, int arg1, int arg2) throws Throwable {
        Hexagon hexagon = board.getHexagon(new Coordinate(arg1, arg2));
        Player ChosenOne;
        if (arg0 == 1) {
            ChosenOne = playerOne;
        }
        else {
            ChosenOne = playerTwo;
        }

        ChosenOne.placeMeepleOnHexagon(hexagon);
    }

    @Then("^the settlement at (\\d+),(\\d+) should be (\\d+) for player (\\d+)$")
    public void theSettlementAtShouldBeForPlayer(int arg0, int arg1, int arg2, int arg3) throws Throwable {
        Coordinate coordinateOne = new Coordinate(arg0, arg1);
        Player ChosenOne = null;
        if (arg3 == 1) {
            ChosenOne = playerOne;
        }
        else {
            ChosenOne = playerTwo;
        }
        assertEquals(arg2,board.settlementSize(coordinateOne, ChosenOne.getColor()));
    }


}