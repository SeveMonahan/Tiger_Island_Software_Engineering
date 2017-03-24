package TigerIsland.Features.Settlement;

import TigerIsland.Board;
import TigerIsland.Terrain;
import TigerIsland.Tile;
import TigerIsland.TileBag;
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

    @Given("^cow$")
    public void initTileBag() {
        testBag = new TileBag();
    }


    @When("^hungry$")
    public void tileDrawn() throws Throwable {
    }
    @Then("^moo$")
    public void tilesExistingInBag() {
        System.out.println("moo");
    }


}