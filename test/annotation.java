import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import cucumber.api.java.en.*;

public class annotation {
    TileBag testBag = null;
    Tile testTile = null;

    @Given("^I have a new tile bag$")
    public void initTileBag() {
        testBag = new TileBag();
    }

    @When("^I draw a tile$")
    public void drawTile() {
        testTile = testBag.drawTile();
    }

    @Then("^I should be told there are \"(.*)\" tiles$")
    public void tilesExistingInBag(String arg1) {
        int tilesRemaining = Integer.parseInt( arg1 );
        assert( testBag.getNumberOfTilesInBag() ==  tilesRemaining );
    }

    @Then("^I should be returned a tile instance$")
    public void tileInstanceChecker() {
        assert( testTile instanceof Tile );
    }
}