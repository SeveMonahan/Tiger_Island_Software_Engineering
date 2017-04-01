package Meeple;

import TigerIsland.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class MeeplDefinitions {
    private Player player = null;
    private Board board = null;
    private Hexagon hexagon = null;
    private Tile tile = null;
    private int meeples;

    @Given("^I have initialized player in meeple$")
    public void initPlayerInMeeple() {
        player = new Player(Color.WHITE);
    }
    @When("^I query that player's meeples$")
    public void queryPlayerMeeples() {
        meeples = player.getMeeplesCount();
    }
    @Then("^that player has 20 meeples$")
    public void playerShouldHave20Meeples() {
        Assert.assertEquals(20, meeples);
    }



    @Given("^I have an initialized board$")
    public void newBoard() throws Throwable {
        Board board = new Board(new Tile(Terrain.LAKE, Terrain.GRASSLAND));
    }
    @Given("^I place a tile on it in meeple$")
    public void placedTile(){
        Coordinate coordinate = board.getNeighboringCoordinate(new Coordinate(100,100), HexagonNeighborDirection.LEFT);
        this.hexagon = board.getHexagon(coordinate);
    }
    @When("^I attempt to populate a Level 1 non-volcano hexagon $")
    public void populateLevelOneHex(){
        player.placeMeepleOnHexagon(this.hexagon);
    }
    @Then("^I place 1 meeple on Hexagon hexagon $")
    public void oneMeepleIsPlaced(){
        assertEquals(HexagonOccupationStatus.MEEPLES, hexagon.getOccupationStatus());
    }
    @Then("^the player now has 1 point $")
    public void playerHasAPoing(){
        assertEquals(1,player.getScore());
    }
    @Then("^the player has 19 meeples  $")
    public void playerHas19Meeples(){
        assertEquals(19, player.getMeeplesCount());
    }
}
