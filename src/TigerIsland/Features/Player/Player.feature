Feature: Player

#Player scenario test
Scenario: Score of initialized player
    Given I have initialized player
    When I query that player's score
    Then I receive 0

Scenario: Score of player after addition
    Given I have initialized a player and added 5 to their score
    When I query that player's score
    Then I receive 5




