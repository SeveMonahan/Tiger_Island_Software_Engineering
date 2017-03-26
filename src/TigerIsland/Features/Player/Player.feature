Feature: Player

#Player scenario test
    Scenario: Score of initialized player
        Given I have initialized a player in player
        When I query that player's score
        Then I receive 0

    Scenario: Score of player after addition
        Given I have initialized a player in player
        And I add 5 to that player's score
        When I query that player's score
        Then I receive 5