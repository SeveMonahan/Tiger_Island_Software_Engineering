Feature: test

#Tile bag scenario test
  Scenario: Meeples of initialized player 
    Given I have initialized player 
    When I query that player's meeples 
    Then that player has 20 meeples 