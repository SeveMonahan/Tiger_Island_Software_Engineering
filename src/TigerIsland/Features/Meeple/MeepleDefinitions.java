package Meeple;

import TigerIsland.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import static org.junit.Assert.assertEquals;

public class MeepleDefinitions
{
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



    @Given("^I have an initialized board with a tile (in meeple folder)$")
    public void newBoard() throws Throwable {
        Board boardWithTile = new Board();
        TileMove startingTileMove = new TileMove(new Tile(Terrain.LAKE, Terrain.GRASSLAND), HexagonNeighborDirection.LEFT, new Coordinate (100, 100));
        boardWithTile.placeTile(startingTileMove);
        Board board = boardWithTile;
    }

    @When("^I attempt to populate a Level 1 non-volcano hexagon $")
    public void populateLevelOneHex(){
        Coordinate coordinate = new Coordinate(100,100).getNeighboringCoordinate(HexagonNeighborDirection.LEFT);
        player.placeMeepleOnHexagon(coordinate, board);
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
