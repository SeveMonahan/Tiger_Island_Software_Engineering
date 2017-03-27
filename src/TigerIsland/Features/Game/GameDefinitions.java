package Game;

import TigerIsland.*;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java.en.And;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GameDefinitions {
    Game game = null;

    @Given("^I have initialized two players$")
    public void init2Players() {
        game = new Game();
    }
    @When("^I end the game$")
    public void endTheGame() {
        game.endGameConditions();
    }

    @Then("^The game is tied$")
    public void isGameTied() {
        assertNull(game.winner);
    }



    //Given I have initialized two players

    @And("^I have added five to the first player's score$")
    public void add5ToFirstPlayersScore(){
        game.player1.setScore(5);
    }

    //When I end the game

    @Then("^The player with added points wins, and the player who has no additional points loses$")
    public void didPlayer1Win() {
        assertEquals(game.player1, game.winner);
    }



//    Given I have initialized two players

    @And("^I have set the first player's points to -1$")
    public void setFirstPlayersPointsToNeg1(){
        game.player1.setAutoLoseScore();
    }

//    When I end the game

    @Then("^The second player wins, and the first player loses$")
    public void didPlayer1Lose(){
        assertEquals(game.player2, game.winner);
    }
}