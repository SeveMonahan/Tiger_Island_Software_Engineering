Feature: Game

#Game scenario test
Scenario: Both players have zero points
    Given I have initialized two players
    When I end the game
    Then The game is tied

Scenario: One player has more points
    Given I have initialized two players
	And I have added five to the first player's score
    When I end the game
    Then The player with added points wins, and the player who has no additional points loses

Scenario: One player has less points
    Given I have initialized two players
	And I have set the first player's points to -1
    When I end the game
    Then The second player wins, and the first player loses




