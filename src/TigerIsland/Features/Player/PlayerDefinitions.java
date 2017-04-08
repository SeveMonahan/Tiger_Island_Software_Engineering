package Player;

import TigerIsland.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

public class PlayerDefinitions {
    private Player player = null;
    private int score;

    @Given("^I have initialized a player in player$")
    public void initPlayer() { player = new Player(Color.BLACK); }

    @Given("^I add 5 to that player's score$")
    public void addPoints() { player.setScore(player.getScore() + 5); }

    @When("^I query that player's score$")
    public void queryPlayerScore() { score = player.getScore(); }

    @Then("^I receive 0$")
    public void verifyPlayerScoreIsZero() { Assert.assertEquals(score, 0); }

    @Then("^I receive 5$")
    public void verifyPlayerScoreIsFive() { Assert.assertEquals(score, 5); }
}