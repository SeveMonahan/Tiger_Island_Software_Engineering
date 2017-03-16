package Annotation.Steps;

import TigerIsland.Tile;
import TigerIsland.TileBag;

import cucumber.api.java.Before;
import cucumber.api.java.en.*;

public class annotation {
    TileBag testBag = null;
    Tile testTile = null;

    @Given("^I have a new tile bag$")
    public void I_have_a_new_tile_bag() {
        testBag = new TileBag();
    }

    @When("^I draw 1 tiles$")
    public void I_draw_tiles() {
        testTile = testBag.drawTile();
    }

    @Then("^I should be told there are 47 tiles$")
    public void I_should_be_told_there_are_47_tiles() {
        int tilesRemaining = 47;
        assert( testBag.getNumberOfTilesInBag() ==  tilesRemaining );
        System.out.println("Test1 Pass");
    }

    @Then("^I should be returned a tile instance$")
    public void I_should_be_returned_a_tile_instance() {
        assert( testTile != null );
        System.out.println("Test2 Pass");
    }
}