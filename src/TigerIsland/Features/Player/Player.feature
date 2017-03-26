Feature: Player

#Player scenario test
    Scenario: Score of initialized player
        Given I have initialized a player
        When I query that player's score
        Then I receive 0

    Scenario: Score of player after addition
        Given I have initialized a player
        And I add 5 to that player's score
        When I query that player's score
        Then I receive 5